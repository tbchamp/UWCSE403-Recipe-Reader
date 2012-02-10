package uwcse403.recipe_reader;

import java.util.List;

public class Recipe {

	private RecipeOverview overview;
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
	}
}
