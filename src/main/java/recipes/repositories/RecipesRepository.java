package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.services.Recipe;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
}
