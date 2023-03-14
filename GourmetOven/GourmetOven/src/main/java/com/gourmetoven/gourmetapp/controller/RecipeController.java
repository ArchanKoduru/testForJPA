package com.gourmetoven.gourmetapp.controller;

import com.gourmetoven.gourmetapp.Dto.Request.RecipeCreateRequestDto;
import com.gourmetoven.gourmetapp.Dto.Request.SearchRequestDto;
import com.gourmetoven.gourmetapp.Dto.Response.IngredientResponseDto;
import com.gourmetoven.gourmetapp.Dto.Response.RecipeResponseDto;
import com.gourmetoven.gourmetapp.entity.Ingredients;
import com.gourmetoven.gourmetapp.entity.Recipe;
import com.gourmetoven.gourmetapp.service.GourmetOvenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gourmetoven.gourmetapp.util.Constants.ALL;

@RestController()
@RequestMapping(value = "/recipe")
public class RecipeController {

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private GourmetOvenService gourmetOvenService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok().body(appVersion);
    }

    @GetMapping("/all/ingredients")
    public ResponseEntity<List<Ingredients>> getAllIngredient() {
        return ResponseEntity.ok().body(gourmetOvenService.getAllIngredients());
    }

    @Deprecated(since = "The search endpoint can be used for same purpose just by passing empty json input" +
            "keeping this endpoint for initial tests")
    @GetMapping("/all/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok().body(gourmetOvenService.getAllRecipes());
    }

    @Deprecated(since = "The search endpoint can be used for same purpose just by passing \"type\" parameter in input" +
            "keeping this endpoint for initial tests")
    @GetMapping("/recipes/type")
    public ResponseEntity<List<Recipe>> getspecificRecipeType(@RequestParam("type") String type) {
        return ResponseEntity.ok().body(gourmetOvenService.findRecipesWithType(type));
    }


    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchForRecipesNew(@RequestParam(required = false, defaultValue = ALL) String type,
                                                                       @RequestParam(required = false, defaultValue = "999999") Integer servings,
                                                                       @RequestParam(required = false) String includeIngredients,
                                                                       @RequestParam(required = false) String excludeIngredients,
                                                                       @RequestParam(required = false) String instructions) {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setType(type);
        searchRequestDto.setServings(servings);
        searchRequestDto.setIncludeIngredients(includeIngredients == null ? Collections.singletonList("") : Arrays.stream(includeIngredients.split(",")).toList());
        searchRequestDto.setExcludeIngredients(excludeIngredients == null ? Collections.singletonList("") : Arrays.stream(excludeIngredients.split(",")).toList());
        searchRequestDto.setInstructions(instructions == null ? null : Arrays.stream(instructions.split(",")).toList());
        List<RecipeResponseDto> recipeResponseDtoList;
        return ResponseEntity.ok().body(gourmetOvenService.getspecificRecipes(searchRequestDto));
    }

    @PostMapping("/create")
    public ResponseEntity<Recipe> createArecipe(@RequestBody RecipeCreateRequestDto recipeCreateRequestDto,
                                                Authentication authentication) throws Exception {
        Recipe recipe = gourmetOvenService.
                createRecipe(recipeCreateRequestDto, authentication.getName());
        return ResponseEntity.ok().body(recipe);
    }

    @PatchMapping("/update")
    public ResponseEntity<Recipe> updateArecipe(@RequestBody RecipeCreateRequestDto recipeCreateRequestDto,
                                                Authentication authentication) throws Exception {
        Recipe recipe = gourmetOvenService.
                updateRecipe(recipeCreateRequestDto, authentication.getName());
        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<String> updateArecipe(@PathVariable("recipeId") Integer recipeId,
                                                Authentication authentication) throws Exception {
        gourmetOvenService.deleteRecipe(recipeId, authentication.getName());
        return ResponseEntity.ok().body("Recipe deleted succesfully");
    }
}
