package recipes.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.entities.User;
import recipes.repositories.RecipesRepository;
import recipes.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipesService {

    private final RecipesRepository recipesRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipesService(RecipesRepository recipesRepository, UserRepository userRepository) {
        this.recipesRepository = recipesRepository;
        this.userRepository = userRepository;
    }

    public Recipe findById(long id) {
        String errorMessage = "Recipe with specified id doesn't exist";
        return this.recipesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage));
    }

    public void save(Recipe recipe, UserDetails userDetails) {
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        Recipe recipeToSave = new Recipe(recipe.getName(), recipe.getDescription(),
                LocalDateTime.now(), recipe.getCategory(), recipe.getIngredients(), recipe.getDirections(), user);
        this.recipesRepository.save(recipeToSave);
        this.userRepository.save(user);
    }

    public void deleteById(long id, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User currentUser = this.userRepository.findByEmail(email);
        Recipe recipe = this.findById(id);

        if (recipe.getUser() != currentUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have an access");

        }
        this.recipesRepository.deleteById(id);
        this.userRepository.save(currentUser);
    }

    public void updateRecipe(long id, Recipe newRecipe, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User currentUser = this.userRepository.findByEmail(email);
        Recipe oldRecipe = this.findById(id);
        if (oldRecipe.getUser() != currentUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have an access");
        }

        oldRecipe.setName(newRecipe.getName());
        oldRecipe.setDescription(newRecipe.getDescription());
        oldRecipe.setDate(LocalDateTime.now());
        oldRecipe.setCategory(newRecipe.getCategory());
        oldRecipe.setDirections(newRecipe.getDirections());
        oldRecipe.setIngredients(newRecipe.getIngredients());
        this.userRepository.save(currentUser);
        this.recipesRepository.save(oldRecipe);

    }

    public List<Recipe> findByCategory(String category) {
        return this.recipesRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByNameContaining(String name) {
        return this.recipesRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public long getLastId() {
        return this.recipesRepository.count();
    }
}

