package recipe_reader.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import recipe_reader.model.*;

public class RecipeTest extends AndroidTestCase {
	
	List<String> directions;
	List<String> ingredients;
	List<String> notes;
	List<String> keywords;
	
	Recipe testRecipeOverviewRecipe;
	Recipe testFullRecipe;
	Category testCategory;
	Directions testDirections;
	RecipeOverview testRecipeOverview;
	
	User user;
	
	@Override
	public void setUp(){
		directions = new ArrayList<String>();
		directions.add("1. Preheat oven to 1 billion degree.");
		directions.add("2. Roll around on ground.");
		directions.add("3. Delete recipe so I can add it again next time I run this test.");
		
		ingredients = new ArrayList<String>();
		ingredients.add("Destruction");
		ingredients.add("Chaos");
		ingredients.add("Cinnamon");
		
		notes = new ArrayList<String>();
		notes.add("Not sure what should be here...");
		
		keywords = new ArrayList<String>();
		keywords.add("Delete");
		keywords.add("Baleted!");
		keywords.add("Test");
		
		testCategory = new Category("Entree");
		testRecipeOverview = new RecipeOverview("Test Recipe1! DELETE ME LATER", testCategory, true, 92, "Delicious warm test recipe. Deletion is yummy.", 12345);
		testDirections = new Directions(directions);
		
		testRecipeOverviewRecipe = new Recipe(testRecipeOverview);
		
		testFullRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5240, 5000, 240, testCategory, true, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		user = new User("TestUser", 777777, "TestUserID");
	}
	

	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests construction of a Recipe made with just RecipeOverview
	public void testsThatARecipeIsSuccessfullyMadeWithJustARecipeOverview(){
		assertNotNull(testRecipeOverviewRecipe);
	}
	
	// Tests construction of a Recipe made with a full and valid paramters passed in
	public void testsThatARecipeIsSuccessfullyMadeWithFullAndValidParamtersPassed(){
		assertNotNull(testFullRecipe);
	}
	
	
	
	/***************************************************	getter METHOD TESTS	***************************************************/
	
	
	
	
	
	
	/***************************************************	setter METHOD TESTS	***************************************************/
	
	
	
	
	
	
	/***************************************************	addToDatabase TESTS	***************************************************/
	
	// Tests that a recipe with an invalid meal name isn't added to the database
	public void testsThatARecipeWithInvalidMealNameIsNotAddedToTheDatabase(){
		Recipe tempRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5240, 5000, 240, testCategory, true, "Delete this recipe if it makes it to the database at some point.",
				"INVALID MEAL NAME", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(tempRecipe.addToDatabase(user));
		} catch (Exception unexpected) {
		}
	}
	
	
	// Tests that a recipe that is just a RecipeOverview is not added to the Database
	public void testsThatARecipeMadeUpOfJustARecipeOverviewIsNotAddedToTheDatabase(){
		Recipe testRecipeOverviewRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5240, 5000, 240, testCategory, true, "Delete this recipe if it makes it to the database at some point.",
				"INVALID MEAL NAME", "Never", "http://i.imgur.com/dUWmF.jpg");
		try{
			assertFalse(testRecipeOverviewRecipe.addToDatabase(user));
		} catch (Exception unexpected) {
		}
	}
	
}
