package recipes.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.repositories.RecipesRepository;

@Service
public class RecipesService {

    private final RecipesRepository repository;

    @Autowired
    public RecipesService(RecipesRepository repository) {
        this.repository =  repository;
    }

    public Recipe findById(long id) {
        String errorMessage = "Recipe with specified id doesn't exist";
        return this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage));
    }

    public Recipe save(Recipe recipe) {
        return this.repository.save(recipe);
    }

    public void deleteById(long id) {
        if (!this.repository.existsById(id)) {
            String errorMessage = "Recipe with specified id doesn't exist";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        this.repository.deleteById(id);
    }

    public long getLastId() {
        return this.repository.count();
    }
}

