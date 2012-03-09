package recipe_reader.model;

import java.util.HashMap;
import java.util.Map;

import uwcse403.recipe_reader.R;

/**
 * Category is the container class for a recipe category
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class Category {
	private final int icon;
	private final int id;
	private final String name;

	/**
	 * Class constructor
	 * @param name
	 */
	public Category(String name) {
		if(name == null || !ids.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.id = ids.get(name);
		this.icon = icons.get(name);
	}

	private static final Map<String, Integer> icons = 
			new HashMap<String, Integer>(){
		private static final long serialVersionUID = 1L;
		{
			put("Vegetarian", R.drawable.vegetarian);
			put("Dairy", R.drawable.dairy);
			put("Entree",R.drawable.entree);
			put("Side Dish",R.drawable.sidedish);
			put("Gluten Free", R.drawable.gluten_free);
			put("Vegan", R.drawable.vegan);
			put("Beverage", R.drawable.beverage);
			put("Other", R.drawable.other);
		}
	};

	private static final Map<String, Integer> ids =
			new HashMap<String, Integer>(){
		private static final long serialVersionUID = 1L;
		{
			put("Vegetarian", 1);
			put("Dairy", 2);
			put("Gluten Free", 3);
			put("Vegan", 4);
			put("Beverage", 5);
			put("Entree", 6);
			put("Side Dish", 7);
			put("Other", 8);
		}
	};

	/**
	 * Method allows you to get category id for category string
	 * @param String category
	 * @return int corresponding to id for category string passed
	 * @throws IllegalArgumentException if null or invalid string is input
	 */
	public int getIdByName(String category) {
		if(category == null || !ids.containsKey(category)) {
			throw new IllegalArgumentException();
		}
		return ids.get(category);
	}

	/**
	 * @return this categories icon
	 */
	public int getIcon() {
		return this.icon;
	}

	/**
	 * @return this categories name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return this categories id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return a string summarizing this category
	 */
	public String toString() {
		return name;
	}
}
