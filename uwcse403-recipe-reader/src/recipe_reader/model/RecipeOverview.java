/**
 * Recipe Overview object contains data necessary to 
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package recipe_reader.model;

public class RecipeOverview {
	private final int id;
	private final String name;
	private final Category category;
	private final Boolean favorite;
	private final int rating;
	private final String description;

	/**
	 * class constructor
	 * @param name
	 * @param category
	 * @param favorite
	 * @param rating
	 * @param description
	 * @param id
	 */
	public RecipeOverview(String name, Category category, Boolean favorite,
			int rating, String description, int id) {
		this.name = name;
		this.category = category;
		this.favorite = favorite;
		this.rating = rating;
		this.description = description;
		this.id = id;
	}

	/**
	 * class constructor if no rating is supplied
	 * @param name
	 * @param category
	 * @param favorite
	 * @param description
	 * @param id
	 */
	public RecipeOverview(String name, Category category, Boolean favorite, String description, int id) {
		this(name, category, favorite, 50, description, id);
	}

	/**
	 * fetcher for name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * fetcher for category
	 * @return category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * fetcher for favorite
	 * @return favorite
	 */
	public Boolean getFavorite() {
		return favorite;
	}

	/**
	 * fetcher for rating
	 * @return rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * fetcher for description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * fetcher for id
	 * @return id
	 */
	public int getId() {
		return id;
	}

}
