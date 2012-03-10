package uwcse403.recipe_reader.test;

// White box test

// NO NEGATIVE TESTS FOR SET METHODS, WAITING ON SOLUTION FOR INVALID INPUTS

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
	public void setUp() throws Exception {
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
		testRecipeOverview = new RecipeOverview("Test Recipe1! DELETE ME LATER", testCategory, false, 92, "Delicious warm test recipe. Deletion is yummy.", 12345);
		testDirections = new Directions(directions);
		
		testRecipeOverviewRecipe = new Recipe(testRecipeOverview);
		
		testFullRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			user = new User("TestUser", 777777, "TestUserID");
		} catch (IllegalArgumentException unexpected){
			fail("Setup was not performed successfully.");
		}

	}
	

	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests construction of a Recipe made with just RecipeOverview
	public void testsThatARecipeIsSuccessfullyMadeWithJustARecipeOverview() {
		assertNotNull(testRecipeOverviewRecipe);
	}
	
	// Tests construction of a Recipe made with a full and valid paramters passed in
	public void testsThatARecipeIsSuccessfullyMadeWithFullAndValidParamtersPassed() {
		assertNotNull(testFullRecipe);
	}
	
	
	
	/***************************************************	getter METHOD TESTS		***************************************************/
	
	
	// Tests that the getOverview method returns the correct RecipeOverview for a Recipe
	//	created from just a RecipeOverview
	public void testsGetOverViewMethodReturnsCorrectRecipeOverview() {
		assertEquals("getOverview method for a Recipe created from just a RecipeOverview should return " +
				"the RecipeOverview the Recipe was created with.", testRecipeOverview, testRecipeOverviewRecipe.getOverview());
	}
	
	// Tests that a RecipeOverview is created when a new Recipe is created without using
	//	a RecipeOverview
	public void testsARecipeOverviewIsCreatedForANewRecipeThatWasCreatedWithoutARecipeOverview() {
		assertNotNull("A RecipeOverview should be created for any new Recipe that was created without" +
				" a RecipeOverview.", testFullRecipe.getOverview());
	}
	
	// Tests the getName method
	public void testsTheGetNameMethodReturnsTheCorrectName() {
		assertEquals("The getName method should return the name of the Recipe.", "Test Recipe2! DELETE ME LATER", testFullRecipe.getName());
	}
	
	// Tests the getPrep method
	public void testsTheGetPrepMethodReturnsTheCorrectPrepTimeForTheRecipe() {
		assertEquals("The getPrep method should return the prep time for a Recipe.", "10 minutes", testFullRecipe.getPrep());
	}
	
	// Tests the getCook method
	public void testsTheGetCookMethodReturnsTheCorrectCookTimeForTheRecipe() {
		assertEquals("The getCook method should return the cook time for a Recipe.", "45 minutes", testFullRecipe.getCook());
	}
	
	// Tests the getYield method
	public void testsTheGetYieldMethodReturnsTheCorrectPrepTimeForTheRecipe() {
		assertEquals("The getYield method should return the amount a Recipe yields.", "8 servings", testFullRecipe.getYield());
	}
	
	// Tests the getIngredients method
	public void testsTheGetIngredientsMethodReturnsTheCorrectIngredientsForTheRecipe() {
		assertEquals("The getIngredients method should return the list of ingredients for a Recipe.", ingredients, testFullRecipe.getIngredients());
	}
	
	// Tests the getDirection method
	public void testsTheGetDirectionsMethodReturnsTheCorrectDirectionsForTheRecipe() {
		assertEquals("The getPrep method should return the list of directions for a Recipe.", testDirections, testFullRecipe.getDirections());
	}
	
	// Tests the getNotes method
	public void testsTheGetNotesMethodReturnsTheCorrectNotesForTheRecipe() {
		assertEquals("The getNotes method should return the list of notes for a Recipe.", notes, testFullRecipe.getNotes());
	}
	
	// Tests the getFat method
	public void testsTheGetFatMethodReturnsTheCorrectFatAmountForTheRecipe() {
		assertEquals("The getFat method should return the amount of fat in a Recipe.", 5000, testFullRecipe.getFat());
	}
	
	// Tests the getCalories method
	public void testsTheGetCaloriesMethodReturnsTheCorrectAmountOfCaloriesForTheRecipe() {
		assertEquals("The getCalories method should return the amount of calories for a Recipe.", 5280, testFullRecipe.getCalories());
	}
	
	// Tests the getCholesterol method
	public void testsTheGetCholesterolMethodReturnsTheCorrectAmountOfCholesterolForTheRecipe() {
		assertEquals("The getCholesterol method should return the amount of cholesterol in a Recipe.", 280, testFullRecipe.getCholesterol());
	}
	
	// Tests the getKeywords method
	public void testsTheGetKeywordsMethodReturnsTheCorrectListOfKeywordsForTheRecipe() {
		assertEquals("The getKeywords method should return the list of keywords for a Recipe.", keywords, testFullRecipe.getKeywords());
	}
	
	// Tests the getMeal method
	public void testsTheGetMealMethodReturnsTheCorrectMealTheRecipeIsClassifiedAs() {
		assertEquals("The getMeal method should return the meal that the Recipe is considered as.", "Lunch", testFullRecipe.getMeal());
	}
	
	// Tests the getReadytime method
	public void testsTheGetReadyTimeMethodReturnsTheCorrectReadyTimeForTheRecipe() {
		assertEquals("The getReadytime method should return the ready time for a Recipe.", "Never", testFullRecipe.getReadytime());
	}
	
	// Tests the getImgLoc method
	public void testsTheGetKeywordsMethodReturnsTheCorrectImageLocationForTheRecipe() {
		assertEquals("The getImgLoc method should return the URL of the location of the image for a Recipe.", "http://i.imgur.com/dUWmF.jpg",
				testFullRecipe.getImgLoc());
	}
	
	
	
	/***************************************************	setter METHOD TESTS		***************************************************/
	
	// Tests the setOverview method
	public void testsTheSetOverviewMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		bareRecipe.setOverview(testRecipeOverview);
		assertEquals("Calling setOverview on a new bare Recipe should set the Recipe to the RecipeOverview passed.",
				testRecipeOverview, bareRecipe.getOverview());
	}
	
	// Tests the setPrep method
	public void testsTheSetPrepMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getPrep();
		bareRecipe.setPrep("15 minutes");
		assertTrue("The bare Recipe's prep time should initially be null, and changed after calling the setPrep method.",
				inital == null && bareRecipe.getPrep().equals("15 minutes"));
	}
	
	// Tests the setCook method
	public void testsTheSetCookMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getCook();
		bareRecipe.setPrep("200 minutes");
		assertTrue("The bare Recipe's cook time should initially be null, and changed after calling the setCook method.",
				inital == null && bareRecipe.getPrep().equals("200 minutes"));
	}
	
	// Tests the setYield method
	public void testsTheSetYieldMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getYield();
		bareRecipe.setYield("52 servings");
		assertTrue("The bare Recipe's yield amount should initially be null, and changed after calling the setYield method.",
				inital == null && bareRecipe.getYield().equals("52 servings"));
	}
	
	// Tests the setIngredients method
	public void testsTheSetIngredientsMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		List<String> inital = bareRecipe.getIngredients();
		bareRecipe.setIngredients(ingredients);
		assertTrue("The bare Recipe's ingredients should initially be null, and changed after calling the setIngredients method.",
				inital == null && bareRecipe.getIngredients() == ingredients);
	}
	
	// Tests the setDirection method
	public void testsTheSetDirectionsMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		Directions inital = bareRecipe.getDirections();
		bareRecipe.setDirections(testDirections);
		assertTrue("The bare Recipe's directions should initially be null, and changed after calling the setDirections method.",
				inital == null && bareRecipe.getDirections() == testDirections);
	}
	
	// Tests the setNotes method
	public void testsTheSetNotesMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		List<String> inital = bareRecipe.getNotes();
		bareRecipe.setNotes(notes);
		assertTrue("The bare Recipe's notes should initially be null, and changed after calling the setNotes method.",
				inital == null && bareRecipe.getNotes() == notes);
	}
	
	// Tests the setFat method
	public void testsTheSetFatMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		bareRecipe.setFat(300);
		assertEquals("The bare Recipe's fat amount should be set after calling the setFat method.", 300, bareRecipe.getFat());
	}
	
	// Tests the setCalories method
	public void testsTheSetCaloriesMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		bareRecipe.setCalories(64453);
		assertEquals("The bare Recipe's calorie amount should be set after calling the setCalories method.", 64453, bareRecipe.getCalories());
	}
	
	// Tests the setCholesterol method
	public void testsTheSetCholesterolMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		bareRecipe.setCholesterol(300);
		assertEquals("The bare Recipe's cholesterol amount should be set after calling the setCholesterol method.", 300, bareRecipe.getCholesterol());
	}
	
	// Tests the setKeywords method
	public void testsTheSetKeywordsMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		List<String> inital = bareRecipe.getKeywords();
		bareRecipe.setKeywords(keywords);
		assertTrue("The bare Recipe's list of keywords should initially be null, and changed after calling the setKeywords method.",
				inital == null && bareRecipe.getKeywords() == keywords);
	}
	
	// Tests the setMeal method
	public void testsTheSetMealMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getMeal();
		bareRecipe.setMeal("Supper");
		assertTrue("The bare Recipe's meal type should initially be null, and changed after calling the setMeal method.",
				inital == null && bareRecipe.getMeal().equals("Supper"));
	}
	
	// Tests the setReadytime method
	public void testsTheSetReadytimeMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getReadytime();
		bareRecipe.setReadytime("80 minutes");
		assertTrue("The bare Recipe's ready time should initially be null, and changed after calling the setReadytime method.",
				inital == null && bareRecipe.getReadytime().equals("80 minutes"));
	}
	
	// Tests the setImgLoc method
	public void testsTheSetImgLocMethodOnANewEmptyRecipe() {
		Recipe bareRecipe = new Recipe();
		String inital = bareRecipe.getImgLoc();
		bareRecipe.setImgLoc("http://i.imgur.com/5KiBc.jpg");
		assertTrue("The bare Recipe's image location should initially be null, and changed after calling the setImgLoc method.",
				inital == null && bareRecipe.getImgLoc().equals("http://i.imgur.com/5KiBc.jpg"));
	}

	
	
	
	/***************************************************	addToDatabase TESTS		***************************************************/
	
	// Tests that a Recipe with an invalid meal name isn't added to the database
	public void testsThatARecipeWithInvalidMealNameIsNotAddedToTheDatabase() {
		Recipe tempRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5240, 5000, 240, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"INVALID MEAL NAME", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(tempRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Adding a Recipe with an invalid meal name to the database should have just returned false.");
		}
	}
	
	
	// Tests that a Recipe that is just a RecipeOverview is not added to the Database
	public void testsThatARecipeMadeUpOfJustARecipeOverviewIsNotAddedToTheDatabase() {
		try{
			assertFalse(testRecipeOverviewRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Adding a Recipe made of just a RecipeOverview to the database should have just returned false.");
		}
	}
	
	// Tests that a completely empty Recipe can't be added to the database.
	public void testsThatAnEmptyRecipeCannotBeAddedToTheDatabase() {
		Recipe testRecipe = new Recipe();
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Adding a Recipe made of just a name to the database should have just returned false.");
		}
	}
	
	// Tests that a Recipe with null keywords is not added to the database
	public void testsThatARecipeWithNullKeywordsIsNotAddedToDatabase() {
		Recipe testRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, null, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a Recipe to the database with null keywords should have resulted in false.");
		}
	}
	
	// Tests that a Recipe with null ingredients is not added to the database
	public void testsThatARecipeWithNullIngredientsIsNotAddedToDatabase() {
		Recipe testRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", null,
				testDirections, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a Recipe to the database with null ingredients should have resulted in false.");
		}
	}
	
	// Tests that a Recipe with null directions is not added to the database
	public void testsThatARecipeWithNullDirectionsIsNotAddedToDatabase() {
		Recipe testRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				null, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a Recipe to the database with null directions should have resulted in false.");
		}
	}
	
	// Tests that a Recipe with null notes is not added to the database
	public void testsThatARecipeWithNullNotesIsNotAddedToDatabase() {
		Recipe testRecipe = new Recipe("Test Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, null, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a Recipe to the database with null notes should have resulted in false.");
		}
	}
	
	/* *******  THE TESTS ENCLOSED SHOULD SUCCESSFULLY ADD RECIPES TO THE DATABASE. NOT RUNNING THESE
	   				UNTIL I KNOW THEY WON'T F UP THE DATABASE!
	   				I NEED MORE DOCUMENTATION!!!!!
	
	// Tests that a Recipe is successfully added to the database
	public void testsThatAValidRecipeIsAddedToTheDatabase() {
		Recipe testRecipe = new Recipe("Tester Recipe1! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			assertTrue(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a valid Recipe to the database should return true, not throw and exception.");
		}
	}
	
	// Tests that a Recipe cannot be added to the Database twice
	public void testsThatAValidRecipeCannotBeAddedToTheDatabaseTwice() {
		Recipe testRecipe = new Recipe("Tester Recipe2! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			testRecipe.addToDatabase(user);
		} catch (Exception expected) {
			fail("Adding the first valid Recipe to the database failed.");
		}
		
		try{
			assertFalse(testRecipe.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a valid Recipe twice to the database should return false.");
		}
	}
	
	// Tests that a two Recipes with the same name cannot both be added to the database
	public void testsThatTwoValidRecipesWithTheSameNameCannotBothBeAddedToTheDatabase() {
		Recipe testRecipe1 = new Recipe("Tester Recipe3! DELETE ME LATER", "10 minutes", "45 minutes", "8 servings", ingredients,
				testDirections, notes, keywords, 5280, 5000, 280, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		Recipe testRecipe2 = new Recipe("Tester Recipe3! DELETE ME LATER", "15 minutes", "1 hour", "1000 servings", ingredients,
				testDirections, notes, keywords, 257, 54, 34, testCategory, false, "Delete this recipe if it makes it to the database at some point.",
				"Lunch", "Never", "http://i.imgur.com/dUWmF.jpg");
		
		try{
			testRecipe1.addToDatabase(user);
		} catch (Exception expected) {
			fail("Adding the first valid Recipe to the database failed.");
		}
		
		try{
			assertFalse(testRecipe2.addToDatabase(user));
		} catch (Exception expected) {
			fail("Attempting to add a valid Recipe with the same name as a Recipe already in the database should result in false.");
		}
	}
	
	*********************** END OF ENCLOSURE */
	
}