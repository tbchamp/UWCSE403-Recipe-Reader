/**
 * Recipe Overview object contains data necessary to
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

public class RecipeOverview {
	private int id;
	private String name;
	private Category category;
	private Boolean favorite;
	private int rating;
	private String description;

	public RecipeOverview(String name, Category category, Boolean favorite,
			int rating, String description, int id) {
		this.name = name;
		this.category = category;
		this.favorite = favorite;
		this.rating = rating;
		this.setDescription(description);
		this.id = id;
	}

	public RecipeOverview(String name, Category category, Boolean favorite, String description, int id) {
		this(name, category, favorite, 50, description, id);
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public Boolean getFavorite() {
		return favorite;
	}

	public int getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
