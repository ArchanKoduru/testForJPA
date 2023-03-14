package com.gourmetoven.gourmetapp.service;

import com.gourmetoven.gourmetapp.Dto.Request.RecipeCreateRequestDto;
import com.gourmetoven.gourmetapp.Dto.Request.SearchRequestDto;
import com.gourmetoven.gourmetapp.Dto.Response.IngredientResponseDto;
import com.gourmetoven.gourmetapp.Dto.Response.RecipeResponseDto;
import com.gourmetoven.gourmetapp.Exception.AccessErrorException;
import com.gourmetoven.gourmetapp.Exception.DataFormatException;
import com.gourmetoven.gourmetapp.Exception.DataNotAvailableException;
import com.gourmetoven.gourmetapp.entity.Ingredients;
import com.gourmetoven.gourmetapp.entity.Recipe;
import com.gourmetoven.gourmetapp.entity.User;
import com.gourmetoven.gourmetapp.repository.IngredientRepository;
import com.gourmetoven.gourmetapp.repository.RecipeRepository;
import com.gourmetoven.gourmetapp.repository.UserRepository;
import com.gourmetoven.gourmetapp.util.TypeMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.gourmetoven.gourmetapp.util.Constants.*;

@Service
public class GourmetOvenService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeSpecification recipeSpecification;

    @Autowired
    private ModelMapper modelMapper;

    public List<Ingredients> getAllIngredients() {
        List<Ingredients> ingredients = ingredientRepository.findAll();
//        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();
//        if (ingredients != null && !ingredients.isEmpty()) {
//            ingredients.forEach(p -> extractIngredients(ingredientResponseDtoList, p));
//        }
        return ingredients;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findRecipesWithType(String type) {
        Optional<Recipe> recipes = recipeRepository.findByType(type);
        if (recipes.isEmpty() || !recipes.get().equals(VEG) || !recipes.get().equals(NONVEG))
            return recipeRepository.findAll();
        return recipes.stream().toList();
    }

    public List<Recipe> getspecificRecipes(SearchRequestDto searchRequestDto) {
        //ALL default is as good as returning all recipes
        if (isAllDefault(searchRequestDto)) {
            return getAllRecipes();
        }
        return processSearchRequest(searchRequestDto);
    }

    public Recipe createRecipe(RecipeCreateRequestDto recipeCreateRequestDto, String name) throws Exception {
        //check user exists
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(name));
        if (!user.isPresent() || user.get().getId() <= 0) {
            throw new DataNotAvailableException("User doesn't exists:" + name);
        }

        if (recipeCreateRequestDto.getIngredients().isEmpty()) {
            throw new DataFormatException("Ingredients can't be empty");
        }

        if (recipeCreateRequestDto.getName() == null || recipeCreateRequestDto.getName().isEmpty()) {
            throw new DataFormatException("Recipe name can't be empty");
        }

        if (!recipeCreateRequestDto.getDishType().equals(VEG) && !recipeCreateRequestDto.getDishType().equals(NONVEG)) {
            throw new DataFormatException("Recipe must be either vegetarian or non-vegetarian");
        }

        Optional<Ingredients> ingredients = recipeCreateRequestDto.getIngredients().stream().map(ingredient -> ingredientRepository.findByName(ingredient)).findAny();
        if (ingredients.isEmpty() || ingredients.stream().toList().size() != recipeCreateRequestDto.getIngredients().size()) {
            throw new DataFormatException("Invalid ingredients mentioned");
        }
        Recipe recipe = modelMapper.map(recipeCreateRequestDto,Recipe.class);

        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(RecipeCreateRequestDto recipeCreateRequestDto, String name) throws Exception {
        //check user exists
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(name));
        if (!user.isPresent() || user.get().getId() <= 0) {
            throw new DataNotAvailableException("User doesn't exists:" + name);
        }
        //check recipe exists
        if (recipeCreateRequestDto.getRecipeId() == null || recipeCreateRequestDto.getRecipeId() <= 0) {
            throw new DataNotAvailableException("Recipe ID cannot be empty");
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeCreateRequestDto.getRecipeId());
        if (recipeOptional.isEmpty()) {
            throw new DataNotAvailableException("Recipe not found");
        }

        //Now we should update only if the owning user is same as the logged in user
        Recipe recipe = recipeOptional.get();
        int userId = recipe.getUser().getId();

        if (user.get().getId() != userId) {
            throw new AccessErrorException("You cannot update someone's recipe");
        }
        //Now we simply delete all entries from recipe, recipe-ingredientmapping, recipe-instruction mapping
        recipeRepository.deleteById(userId);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Integer recipeId, String name) throws Exception {
        //check user exists
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(name));
        if (!user.isPresent() || user.get().getId() <= 0) {
            throw new DataNotAvailableException("User doesn't exists:" + name);
        }

        //check recipe exists
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (!recipe.isPresent() || recipe.isEmpty()) {
            throw new DataFormatException("Recipe ID cannot be empty");
        }
//        if (!recipeRepository.isRecipeExists(recipeId)) {
//            throw new DataNotAvailableException("Recipe not found");
//        }
//        if (userId == null) {
//            throw new DataNotAvailableException("User doesn't exists");
//        }
        //Now we should update only if the owning user is same as the logged in user
        if (user.get().getId() != recipe.get().getUser().getId()) {
            throw new AccessErrorException("You cannot update someone's recipe");
        }
        //Now we simply delete all entries from recipe, recipe-ingredientmapping, recipe-instruction mapping
        recipeRepository.deleteById(recipeId);
    }

    private List<Recipe> processSearchRequest(SearchRequestDto searchRequestDto) {
        String type = searchRequestDto.getType();
        Integer servings = searchRequestDto.getServings();
        List<String> excludeIngredients = searchRequestDto.getExcludeIngredients();
        List<String> includeIngredients = searchRequestDto.getIncludeIngredients();
        List<String> instructions = searchRequestDto.getInstructions();

        //If type is something other than vegetarian/non-vegetarian/all then default it to all
        if (!type.equals(ALL) && !type.equals(VEG) && !type.equals(NONVEG)) {
            searchRequestDto.setType(ALL);
        }

        List<Recipe> recipes = recipeRepository.findAll(recipeSpecification.getRecipes(searchRequestDto));

        return recipes;
    }

    private boolean isAllDefault(SearchRequestDto searchRequestDto) {
        String type = searchRequestDto.getType();
        Integer servings = searchRequestDto.getServings();
        List<String> excludeIngredients = searchRequestDto.getExcludeIngredients();
        List<String> includeIngredients = searchRequestDto.getIncludeIngredients();
        List<String> instructions = searchRequestDto.getInstructions();
        return type.equals(ALL) && servings == null && excludeIngredients.isEmpty() && includeIngredients.isEmpty() &&
                instructions.isEmpty();
    }

//    private void getAllIngredientsForARecipe(Map<Recipe, List<Integer>> ingredients, Recipe recipeId) {
//        List<Integer> ingredientRet = recipeIngredientMappingRepository.getAllIngredientForArecipe(recipeId.getRecipeId());
//        ingredients.put(recipeId, ingredientRet);
//    }

//    private List<RecipeResponseDto> generatePayload(List<Recipe> recipes) {
//        Map<Recipe, List<Integer>> ingredients = new HashMap<>();
//        if (recipes == null || recipes.isEmpty()) {
//            return Collections.emptyList();
//        }
//        recipes.forEach(p -> getAllIngredientsForARecipe(ingredients, p));
//
//        List<RecipeResponseDto> recipeResponseDtos = new ArrayList<>();
//        ingredients.forEach((key, value) -> populateRecipeResponseDtos(key, value, recipeResponseDtos));
//        return recipeResponseDtos;
//    }

//    private void populateRecipeResponseDtos(Recipe recipe, List<Integer> ingredints, List<RecipeResponseDto> recipeResponseDtos) {
//        List<Ingredients> ingredientsList = ingredientRepository.findAll(ingredints);
//        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();
//        if (ingredientsList != null && !ingredientsList.isEmpty()) {
//            ingredientsList.forEach(p -> extractIngredients(ingredientResponseDtoList, p));
//        }
//
//        List<String> instructions = recipeInstructionMappingRepository.getAllInstructionsForArecipe(recipe.getRecipeId());
//        RecipeResponseDto recipeResponseDto = RecipeResponseDto.builder().recipeId(recipe.getRecipeId()).recipeName(recipe.getRecipeName())
//                .ingredients(ingredientResponseDtoList).servings(recipe.getServings())
//                .owningUser(userRepository.getUsernameForUserId(recipe.getOwningUser()))
//                .dishType(TypeMapper.getType(recipe.getDishType())).instructions(instructions).
//                creationType(TypeMapper.getType(recipe.getCreationType())).build();
//        recipeResponseDtos.add(recipeResponseDto);
//    }

//    private void extractIngredients(List<IngredientResponseDto> ingredientResponseDtoList, Ingredients ingredients) {
//        ingredientResponseDtoList.add(IngredientResponseDto.builder().ingredientId(ingredients.
//                        getIngredientId()).ingredientName(ingredients.getIngredientName()).
//                type(TypeMapper.getType(ingredients.getTypeId())).build());
//    }
}
