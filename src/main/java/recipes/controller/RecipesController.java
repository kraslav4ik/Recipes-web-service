package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.services.Recipe;
import recipes.services.RecipesService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Validated
@RequestMapping("/api")
public class RecipesController {

    private final RecipesService service;

    @Autowired
    public RecipesController(RecipesService service) {
        this.service = service;
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return this.service.findById(id);
    }

    @PostMapping("/recipe/new")
    public Map<String, Long> addRecipe(@RequestBody @Valid Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        long recipeId = this.service.getLastId() + 1;
        this.service.save(new Recipe(recipe.getName(), recipe.getDescription(),
                LocalDateTime.now(), recipe.getCategory(), recipe.getIngredients(), recipe.getDirections()), userDetails);

        Map<String, Long> response = new HashMap<>();
        response.put("id", recipeId);
        return response;
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        this.service.deleteById(id, userDetails);

    }

    @PutMapping("/recipe/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id, @RequestBody @Valid Recipe newRecipe,
                             @AuthenticationPrincipal UserDetails userDetails) {
        this.service.updateRecipe(id, newRecipe, userDetails);
    }

    @GetMapping("/recipe/search")
    public List<Recipe> searchRecipe(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "category", required = false) String category) {
        if ((name == null && category == null) || (name != null && category != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (name != null) {
            return this.service.findByNameContaining(name);
        }
        return this.service.findByCategory(category);
    }

}

