package recipe_reader.test;

import java.util.ArrayList;
import java.util.List;

import recipe_reader.model.Category;
import recipe_reader.model.Generator;
import recipe_reader.model.Meal; 
import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.Settings;

public class test2 {

	//this is a test file to see if this is working!!
	//comment added by zaphans

	public static void main(String [] args) throws Exception {
		Settings settings = new Settings();
		settings.signIn("john", "test");
//		Set<Ingredient> ing = new HashSet<Ingredient>();
//		ing.add(new Ingredient("Cheese", 1, "Pound"));
//		ing.add(new Ingredient("Beef", 10, "Pounds"));
//		List<String> dir = new ArrayList<String>();
//		dir.add("Massage the Beef");
//		dir.add("Mix in the Cheese tenderly");
//		Directions d = new Directions(dir);
		List<String> key = new ArrayList<String>();
		key.add("allrecipes");
//		List<String> notes = new ArrayList<String>();
//		notes.add("I made this once and it was a huge mistake...");
//		Category cat = new Category(6, "Entree");
//		Recipe r = new Recipe("test", "30 minutes", "5 minutes", "1 serving", ing, d,
//				notes, key, 10000, 10000, 10000, cat, true, "pretty bad", "Dessert", "10 minutes", "this.url.com");
//		if (!r.addToDatabase(settings.getUser())){
//			System.out.println("fail");
//		}
//		List<RecipeOverview> temp = Searcher.getRecipeOverviewsByKeywords(key, settings.getUser());
//		if (temp == null){
//			System.out.println("Search failed");
//		} else {
//			for (RecipeOverview rec : temp){
//				System.out.println("Is it a favorite: " + rec.getFavorite());
//				System.out.println("It is named: " + rec.getName());
//				System.out.println();
//			}
//		}
//		List<Category> t = Searcher.getCategories();
//		List<Meal> s = Searcher.getMeals();
//		for (Category c : t){
//			System.out.println(c.getName());
//		}
//		for (Meal m : s){
//			System.out.println(m.getName());
//		}
//		temp = Searcher.getOverviewByMealCategory(6, 6, settings.getUser());
//		if (temp == null){
//			System.out.println("Search failed");
//		} else {
//			for (RecipeOverview rec : temp){
//				System.out.println("Is it a favorite: " + rec.getFavorite());
//				System.out.println("It is named: " + rec.getName());
//				System.out.println();
//			}
//		}
		Generator.getRecipe(195, settings.getUser());
		List<RecipeOverview> temp = Searcher.getFavoritesByUser(settings.getUser());
		if (temp == null){
			System.out.println("Search failed");
		} else {
			for (RecipeOverview rec : temp){
				System.out.println("Is it a favorite: " + rec.getFavorite());
				System.out.println("It is named: " + rec.getName());
				System.out.println();
			}
		}
	}

	// hey look it's a comment! -kristini

}
