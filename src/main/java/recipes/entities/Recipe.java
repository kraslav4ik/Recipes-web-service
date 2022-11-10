package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID")
    private long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name="description")
    @NotBlank
    private String description;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="category")
    @NotBlank
    private String category;

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    private List<String> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    private List<String> directions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Recipe(String name, String description,
                  LocalDateTime date, String category,
                  List<String> ingredients, List<String> directions, User user) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.user = user;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "id:" + id + "\tname:" + name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
