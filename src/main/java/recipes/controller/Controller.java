package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.services.Recipe;
import recipes.services.RecipesService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@Validated
@RequestMapping("/api/recipe")
public class Controller {

    private final RecipesService service;

    @Autowired
    public Controller(RecipesService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return this.service.findById(id);
    }

    @PostMapping("/new")
    public Map<String, Long> addRecipe(@RequestBody @Valid Recipe recipe) {
        long recipeId = this.service.getLastId() + 1;
        this.service.save(new Recipe(recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections()));
        Map<String, Long> response = new HashMap<>();
        response.put("id", recipeId);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {
        this.service.deleteById(id);
    }
}

