package com.gourmetoven.gourmetapp.service;

import com.gourmetoven.gourmetapp.Dto.Request.RecipeCreateRequestDto;
import com.gourmetoven.gourmetapp.Dto.Request.SearchRequestDto;
import com.gourmetoven.gourmetapp.Dto.Response.IngredientResponseDto;
import com.gourmetoven.gourmetapp.Dto.Response.RecipeResponseDto;
import com.gourmetoven.gourmetapp.entity.Ingredients;
import com.gourmetoven.gourmetapp.entity.Recipe;
import com.gourmetoven.gourmetapp.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *  Unit tests for @link GourmetOvenService
 */
@RunWith(MockitoJUnitRunner.class)
public class GourmetOvenServiceTest {

    @InjectMocks
    GourmetOvenService gourmetOvenService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

//    @Mock
//    private RecipeInstructionMappingRepository recipeInstructionMappingRepository;
//
//    @Mock
//    RecipeIngredientMappingRepository recipeIngredientMappingRepository;
//
//    @Test
//    public void givenGourmetOvenServiceWhenGetAllIngredientsIsCalledThenFetchAllIngredients()
//    {
//        Ingredients ingredients = new Ingredients(101,"Cheese",1);
//        List<Ingredients> ingredientsList = new ArrayList<>( Arrays.asList(ingredients));
//        Mockito.when(ingredientRepository.findAll()).thenReturn(ingredientsList);
//
//        List<IngredientResponseDto> ingredientResponseDtos = gourmetOvenService.getAllIngredients();
//        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto(101, "Cheese", "vegetarian");
//        Assert.assertNotNull(ingredientResponseDtos);
//        Assert.assertEquals(ingredientResponseDto, ingredientResponseDtos.get(0));
//    }

//    @Test
//    public void GivenGourmetOvenServiceWhenCreateArecipeIsCalledThenRecipeCreatesSuccessfully() throws Exception {
//        Mockito.when(userRepository.getUserIdForUserName(Mockito.anyString())).thenReturn(101);
//        RecipeCreateRequestDto recipeCreateRequestDto = RecipeCreateRequestDto.builder().dishType("vegetarian")
//                .ingredients(List.of(101,102)).instructions(List.of("More spicy")).
//                servings(2).build();
//        RecipeResponseDto recipeResponseDtoMock = new RecipeResponseDto();
//        recipeResponseDtoMock.setRecipeId(101);
//        recipeResponseDtoMock.setRecipeName("Sambar");
//        Mockito.when(recipeRepository.createRecipe(Mockito.any(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(recipeResponseDtoMock);
//        RecipeResponseDto recipeResponseDtoOut = gourmetOvenService.createRecipe(recipeCreateRequestDto,"101");
//
//        Assert.assertEquals(recipeResponseDtoMock, recipeResponseDtoOut);
//    }

//    @Test(expected = DataNotAvailableException.class )
//    public void GivenGourmetOvenServiceWhenCreateArecipeIsCalledWithNullUseridThenExceptionThrown() throws Exception {
//        RecipeCreateRequestDto recipeCreateRequestDto = RecipeCreateRequestDto.builder().dishType("vegetarian")
//                .ingredients(List.of(101,102)).instructions(List.of("More spicy")).
//                servings(2).build();
//
//        RecipeResponseDto recipeResponseDtoOut = gourmetOvenService.createRecipe(recipeCreateRequestDto,"101");
//    }

//    @Test
//    public void GivenGourmetOvenServiceWhenGetAllRecipeIsCalledThenRecipeListFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "omlette", 2, 2, 1, 6 );
//        recipes.add(recipe);
//        Mockito.when(recipeRepository.findAllRecipes()).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.getAllRecipes();
//
//        Assert.assertEquals(recipe.getRecipeId(), recipeResponseDtos.get(0).getRecipeId());
//    }
//
//    @Test
//    public void GivenGourmetOvenServiceWhenSpecificRecipeIsCalledThenRecipesFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "omlette", 2, 2, 1, 6 );
//        recipes.add(recipe);
//        Mockito.when(recipeRepository.findAllRecipes()).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.findRecipesWithType("all");
//
//        Assert.assertEquals(recipe.getRecipeId(), recipeResponseDtos.get(0).getRecipeId());
//    }
//
//    @Test
//    public void GivenGourmetOvenServiceWhenVegRecipeIsCalledThenVegRecipesFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "Sambar", 2, 1, 1, 6 );
//        recipes.add(recipe);
//        Mockito.when(recipeRepository.findRecipesWithType(Mockito.anyInt())).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.findRecipesWithType("vegetarian");
//
//        Assert.assertEquals(recipe.getRecipeName(), recipeResponseDtos.get(0).getRecipeName());
//    }
//
//    @Test
//    public void GivenGourmetOvenServiceWhenNonvegRecipeIsCalledThenVegRecipesFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "omlette", 2, 2, 1, 6 );
//        recipes.add(recipe);
//        Mockito.when(recipeRepository.findRecipesWithType(Mockito.anyInt())).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.findRecipesWithType("non-vegetarian");
//
//        Assert.assertEquals(recipe.getRecipeName(), recipeResponseDtos.get(0).getRecipeName());
//    }
//
//    @Test
//    public void GivenGourmetOvenServiceWhenSpecificSearchWithDefaultValuesIsCalledThenAllRecipesFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "omlette", 2, 2, 1, 6 );
//        recipes.add(recipe);
//        SearchRequestDto searchRequestDto = new SearchRequestDto();
//        searchRequestDto.setServings(null);
//        Mockito.when(recipeRepository.findAllRecipes()).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.getspecificRecipes(searchRequestDto);
//
//        Assert.assertEquals(recipe.getRecipeName(), recipeResponseDtos.get(0).getRecipeName());
//    }
//
//    @Test
//    public void GivenGourmetOvenServiceWhenSpecificRecipeWithValuesIsCalledThenSelectedRecipesFetched() {
//        List<Recipe> recipes = new ArrayList<>();
//        Recipe recipe = new Recipe(108, "omlette", 2, 2, 1, 6 );
//        recipes.add(recipe);
//        SearchRequestDto searchRequestDto = new SearchRequestDto();
//        searchRequestDto.setServings(2);
//        Mockito.when(recipeRepository.findRecipesWithType(Mockito.anyString(), Mockito.anyInt(), Mockito.anyList(),
//                Mockito.anyList(), Mockito.anyList())).thenReturn(recipes);
//        List<RecipeResponseDto> recipeResponseDtos = gourmetOvenService.getspecificRecipes(searchRequestDto);
//
//        Assert.assertEquals(recipe.getRecipeName(), recipeResponseDtos.get(0).getRecipeName());
//    }

//    @Test
//    public void GivenGourmetOvenServiceWhenUpdateRecipeIsCalledThenRecipeUpdatesSuccessfully() throws Exception {
//        Mockito.when(userRepository.getUserIdForUserName(Mockito.anyString())).thenReturn(101);
//        RecipeCreateRequestDto recipeCreateRequestDto = RecipeCreateRequestDto.builder().dishType("vegetarian").recipeId(101).
//                ingredients(List.of(101,102)).instructions(List.of("More spicy")).
//                servings(2).build();
//        Mockito.when(recipeRepository.isRecipeExists(Mockito.anyInt())).thenReturn(true);
//        Recipe recipe = new Recipe(101,"sambar", 2, 1, 5,101);
//        Mockito.when(recipeRepository.findArecipe(Mockito.anyInt())).thenReturn(recipe);
//
//        RecipeResponseDto recipeResponseDtoMock = new RecipeResponseDto();
//        recipeResponseDtoMock.setRecipeId(101);
//        recipeResponseDtoMock.setRecipeName("Sambar");
//        Mockito.when(recipeRepository.updateRecipe(Mockito.any(), Mockito.anyInt())).thenReturn(recipeResponseDtoMock);
//        RecipeResponseDto recipeResponseDtoOut = gourmetOvenService.updateRecipe(recipeCreateRequestDto,"101");
//
//        Assert.assertEquals(recipeResponseDtoMock, recipeResponseDtoOut);
//    }

//    @Test
//    public void GivenGourmetOvenServiceWhenDeleteRecipeIsCalledThenRecipeDeletesSuccessfully() throws Exception {
//        Mockito.when(userRepository.getUserIdForUserName(Mockito.anyString())).thenReturn(101);
//        Mockito.when(recipeRepository.isRecipeExists(Mockito.anyInt())).thenReturn(true);
//        Recipe recipe = new Recipe(101,"sambar", 2, 1, 5,101);
//        Mockito.when(recipeRepository.findArecipe(Mockito.anyInt())).thenReturn(recipe);
//
//        gourmetOvenService.deleteRecipe(108,"101");
//
//         Mockito.verify(recipeRepository, Mockito.times(1)).deleteRecipe(Mockito.anyInt(),
//                 Mockito.anyInt());
//    }

}
