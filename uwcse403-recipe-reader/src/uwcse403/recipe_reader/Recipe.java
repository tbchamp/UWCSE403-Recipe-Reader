package uwcse403.recipe_reader;

/**
 * Contains all the information about a specific recipe
 * Jonathan Ramaswamy
 */
import java.util.Arrays;
import java.util.List;

import uwcse403.recipe_reader.RecipeOverview.Category;

public class Recipe {

	private RecipeOverview overview;
	private String name;
	private String prep;
	private String cook;
	private String yield;
	private List<String> ingredients;
	private List<String> directions;
	private List<String> notes;
	private int calories;
	private int fat;
	private int cholesterol;

	public Recipe(RecipeOverview overview) {
		this.overview = overview;
		name = overview.getName();
	}
	
	public Recipe(String name) {
		this.name = name;
		overview = new RecipeOverview(name, Category.MAIN_COURSE, true);
		prep = "20 min";
		cook = "10 min";
		yield = "1 serving";
		String[] i = {"Beef", "Bun", "Ketchup", "Lettuce"};
		ingredients = Arrays.asList(i);
		String[] d = {"Put it all together", "Eat"};
		directions = Arrays.asList(d);
		String[] n = {"Absolutely delicious"};
		notes = Arrays.asList(n);
		calories = 1000;
		fat = 10;
		cholesterol = 100;
	}
}