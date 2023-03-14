# GourmetOven
Simple Java based recipe search app
Open GourmetAppApplication class and click on run icon to start Application. 


> curl -i --user user1:pass1 http://localhost:8088/recipe/all/ingredients <br/>
> curl -i --user user1:pass1 http://localhost:8088/recipe/all/default/recipes

**_POST http://localhost:8088/recipe/create_**
```json
{
"name":"sambar",
"servings":2,
"dishType":"vegetarian",
"ingredients":[101,102],
"instructions":["More spicy"]
}
```

**_PATCH http://localhost:8088/recipe/update_**
```json
{
"recipeId":108,
"name":"Sambar",
"servings":4,
"dishType":"vegetarian",
"ingredients":[101,102,103],
"instructions":["More spicy","Less tomato","Lots of lentils"]
}
```
**_http://localhost:8088/recipe/search_**
```json
{
"type" : "all",
"servings" : 4,
"includeIngredients": ["Garlic","Mustard"],
"excludeIngredients": ["Salt","Egg"],
"instructions":["No Paprika"]
}
```
```sql
select * from ingredients;
select * from recipe;
select * from recipe_ingredient_mapping;
select * from recipe_instruction_mapping;
select * from users;
```

```json
{
"type" : "all",
"servings" : 4,
"includeIngredients": ["Garlic","Mustard"],
"excludeIngredients": ["Salt","Egg"],
"instructions":["No Paprika"]
}
```

## All vegetarian recipes

```sql
select recipe.recipe_id, recipe.recipe_name, recipe.servings, recipe.dish_type, recipe.creation_type, recipe.owning_user
from recipe where recipe.dish_type = 1;
```
__Recipes that can serve 2 persons and have “Salami” as an ingredient__

```json
{
"type" : "all",
"servings" : 2,
"includeIngredients": ["Salami"]
}
```

```sql
select recipe.recipe_id, recipe.recipe_name, recipe.servings, recipe.dish_type, recipe.creation_type, recipe.owning_user
from recipe INNER JOIN recipe_ingredient_mapping ON recipe.recipe_id = recipe_ingredient_mapping.recipe_id
    AND  recipe.servings = 2 INNER JOIN ingredients ON ingredients.ingredient_id=recipe_ingredient_mapping.ingredient_id
    AND ingredients.ingredient_name='Salami';
```
__Recipes without “Salami” as an ingredient that has “oven” in the instructions__
```json
{
"type" : "all",
"excludeIngredients": ["Salami"],
"instructions":["No Paprika"]
}
```
```sql
select recipe.recipe_id, recipe.recipe_name, recipe.servings, recipe.dish_type, recipe.creation_type, recipe.owning_user
from recipe INNER JOIN recipe_ingredient_mapping ON recipe.recipe_id != recipe_ingredient_mapping.recipe_id INNER JOIN
ingredients ON ingredients.ingredient_id=recipe_ingredient_mapping.ingredient_id AND ingredients.ingredient_name='Salami'
INNER JOIN recipe_instruction_mapping ON recipe.recipe_id=recipe_instruction_mapping.recipe_id AND recipe_instruction_mapping.instruction='No Paprika';
```

# Status
&check; Architecture <br/>
&check; Design <br/>
&check; Implementation <br/>
&check; Integration Tests <br/>
&check; Unit Tests <br/>