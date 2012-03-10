package uwcse403.recipe_reader.integration_test;

/*
 * The following tests exercise the RecipeOverview class by calling its constructor,
 *  get, and set methods. Tests that look for invalid input were purposefully left out
 *  because invalid input is checked by the database.
 *  
 *  NOTE: This is a black box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */


import recipe_reader.model.Category;
import recipe_reader.model.RecipeOverview;
import android.test.AndroidTestCase;


public class RecipeOverviewTest extends AndroidTestCase {
	
	public class CategoryStub extends Category
	{
	    public int numCallsToGetName;
		public CategoryStub(String name){
			super(name);
			numCallsToGetName = 0;
		}
	    @Override
	    public String getName() {
	    	numCallsToGetName++; 
	    	return super.getName();
	    }
	}
	
	private RecipeOverview testROWithRating;
	private RecipeOverview testROWithoutRating;
	private RecipeOverview specialRO;
	
	private Category cat1;
	private Category cat2;
	private CategoryStub cat3;
	
	@Override
	public void setUp() {
		
		cat1 = new Category("Vegan");
		testROWithRating = new RecipeOverview("Super Delicious Surprise", cat1, false, 75,
				"It's not really all that delicious... Or vegan...", 747953);
		
		cat2 = new Category("Beverage");
		testROWithoutRating = new RecipeOverview("Water", cat2, true,
				"It's water. There is no recipe.", 81237);
		
	    cat3 = new CategoryStub("Side Dish");
	    specialRO = new RecipeOverview("Dee's Nuts", cat3, false, 65,
				"Eat some of Dee's nuts! They're delicious!", 38738);
	}
	
	
	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/

	// Tests that the constructor created a new RecipeOverview (with rating)
	public void testsThatTheConstructorSuccessfullyCreatedANewRecipeWithRating(){
		assertNotNull("The RecipeOverview created with a rating shouldn't be null after " +
				"calling the constructor.", testROWithRating);
	}
	
	// Tests that the constructor created a new RecipeOverview (without rating)
	public void testsThatTheConstructorSuccessfullyCreatedANewRecipeWithoutRating(){
		assertNotNull("The RecipeOverview created without a rating shouldn't be null after " +
				"calling the constructor.", testROWithoutRating);
	}
	
	
	/***************************************************	getName TESTS	***************************************************/
	
	// Tests the getName method called on the RecipeOverview created with a rating
	public void testsThatTheGetNameMethodReturnsTheCorrectNameForARecipeOverviewWithRating(){
		assertEquals("Tests the getName method on a RecipeOverview created with a rating.", "Super Delicious Surprise", testROWithRating.getName());
	}
	
	// Tests the getName method called on the RecipeOverview created without a rating
	public void testsThatTheGetNameMethodReturnsTheCorrectNameForARecipeOverviewWithoutRating(){
		assertEquals("Tests the getName method on a RecipeOverview created without a rating.", "Water", testROWithoutRating.getName());
	}
	
	
	
	/***************************************************	getCategory TESTS	***************************************************/
	
	// Tests the getCategory method called on the RecipeOverview created with a rating
	public void testsThatTheGetCategoryMethodReturnsTheCorrectCategoryForARecipeOverviewWithRating(){
		assertEquals("Tests the getCategory method on a RecipeOverview created with a rating.", cat1, testROWithRating.getCategory());
	}
	
	// Tests that the getCategory method called on the RecipeOverview created without a rating
	//	doesn't return the category of the other RecipeOverview
	public void testsThatTheGetCategoryMethodDoesntReturnTheWrongCategoryForARecipeOverviewWithoutRating(){
		assertFalse("Tests the getCategory method doesn't return the wrong Category when called on a RecipeOverview created " +
				"without a rating.", testROWithoutRating.getCategory() == cat1);
	}
	
	// Tests the getCategory method called on the RecipeOverview created without a rating
	public void testsThatTheGetCategoryMethodReturnsTheCorrectCategoryForARecipeOverviewWithoutRating(){
		assertEquals("Tests the getCategory method on a RecipeOverview created without a rating.", cat2, testROWithoutRating.getCategory());
	}
	
	
	
	/***************************************************	getCategoryName TESTS	***************************************************/
	
	// Tests the getCategoryName method returns a valid category name
	public void testsThatTheGetCategoryNameMethodWorks(){
        specialRO.getCategoryName();
        assertEquals(1, cat3.numCallsToGetName);
	}
	
	
	
	/***************************************************	getFavorite TESTS	***************************************************/
	
	// Tests the getFavorite method called on the RecipeOverview created with a rating
	public void testsThatTheGetFavoriteMethodReturnsTheCorrectFavoriteIndicationForARecipeOverviewWithRating(){
		assertFalse("Tests the getFavorite method on a RecipeOverview created with a rating.", testROWithRating.getFavorite());
	}
	
	// Tests the getFavorite method called on the RecipeOverview created without a rating
	public void testsThatTheGetFavoriteMethodReturnsTheCorrectFavoriteIndicationForARecipeOverviewWithoutRating(){
		assertTrue("Tests the getFavorite method on a RecipeOverview created without a rating.", testROWithoutRating.getFavorite());
	}
	
	
	
	/***************************************************	getRating TESTS	***************************************************/
	
	// Tests the getRating method called on the RecipeOverview created with a rating
	public void testsThatTheGetRatingMethodReturnsTheCorrectRatingForARecipeOverviewWithRating(){
		assertEquals("Tests the getRating method on a RecipeOverview created with a rating.", 75, testROWithRating.getRating());
	}
	
	// Tests the getRating method called on the RecipeOverview created without a rating
	//	returns the default value of 50.
	public void testsThatTheGetRatingMethodReturnsTheCorrectRatingForARecipeOverviewWithoutRating(){
		assertEquals("Tests the getRating method on a RecipeOverview created without a rating returns 50.", 50, testROWithoutRating.getRating());
	}
	
	
	
	/***************************************************	getDescription TESTS	***************************************************/
	
	// Tests the getDescription method called on the RecipeOverview created with a rating
	public void testsThatTheGetDescriptionMethodReturnsTheCorrectDescriptionForARecipeOverviewWithRating(){
		assertEquals("Tests the getDescription method on a RecipeOverview created with a rating.", "It's not really all that delicious... Or vegan...", testROWithRating.getDescription());
	}
	
	// Tests the getDescription method called on the RecipeOverview created without a rating
	public void testsThatTheGetDescriptionMethodReturnsTheCorrectDescriptionForARecipeOverviewWithoutRating(){
		assertEquals("Tests the getDescription method on a RecipeOverview created without a rating.", "It's water. There is no recipe.", testROWithoutRating.getDescription());
	}
	
	
	
	/***************************************************	getId TESTS	***************************************************/
	
	// Tests the getId method called on the RecipeOverview created with a rating
	public void testsThatTheGetIdMethodReturnsTheCorrectIdForARecipeOverviewWithRating(){
		assertEquals("Tests the getId method on a RecipeOverview created with a rating.", 747953, testROWithRating.getId());
	}
	
	// Tests the getName method called on the RecipeOverview created without a rating
	public void testsThatTheGetIdMethodReturnsTheCorrectIdForARecipeOverviewWithoutRating(){
		assertEquals("Tests the getId method on a RecipeOverview created without a rating.", 81237, testROWithoutRating.getId());
	}
	
}