package uwcse403.recipe_reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class test2 {

	//this is a test file to see if this is working!!
	//comment added by zaphans

	public static void main(String [] args) throws Exception {
		Settings settings = new Settings();
		settings.signIn("john", "test");
		Set<Ingredient> ing = new HashSet<Ingredient>();
		ing.add(new Ingredient("Cheese", 1, "Pound"));
		ing.add(new Ingredient("Beef", 10, "Pounds"));
		List<String> dir = new ArrayList<String>();
		dir.add("Massage the Beef");
		dir.add("Mix in the Cheese tenderly");
		Directions d = new Directions(dir);
		List<String> key = new ArrayList<String>();
		key.add("new");
		key.add("raw");
		List<String> notes = new ArrayList<String>();
		notes.add("I made this once and it was a huge mistake...");
		Category cat = new Category(6, "Entree");
		Recipe r = new Recipe("testdddsdss", 30, 0, 1, ing, d, notes, key, 10000, 10000, 10000, cat, true, "pretty bad", "Dessert");
		if (!r.addToDatabase(settings.getUser())){
			System.out.println("fail");
		}
		List<RecipeOverview> temp = Searcher.getRecipeOverviewsByKeywords(key, settings.getUser());
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
