package recipes.services;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID")
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name="description")
    @NotBlank
    private String description;

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    private List<String> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    private List<String> directions = new ArrayList<>();

    public Recipe(String name, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Recipe() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
