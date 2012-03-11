package recipe_reader.model;

import java.util.Random;

/**
 * RecipeOverview contains information about a recipe to be display in a list of other recipes
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class RecipeOverview {
	private final int id;
	private final String name;
	private final Category category;
	private final Boolean favorite;
	private final int rating;
	private final String description;

	/**
	 * Class constructor
	 * 	Doesn't do error checking, as errors are handled by the database.
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
	 * Class constructor if no rating is supplied. Sets rating to random.
	 * @param name
	 * @param category
	 * @param favorite
	 * @param description
	 * @param id
	 */
	public RecipeOverview(String name, Category category, Boolean favorite, String description, int id) {
		Random rand = new Random();
		int rating = rand.nextInt(100);
		this.name = name;
		this.category = category;
		this.favorite = favorite;
		this.rating = rating;
		this.description = description;
		this.id = id;
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
	 * returns the name of the Category
	 * @returns Category's name
	 */
	public String getCategoryName(){
		return category.getName();
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
