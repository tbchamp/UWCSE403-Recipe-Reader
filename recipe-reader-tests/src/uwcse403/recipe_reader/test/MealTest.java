package uwcse403.recipe_reader.test;

/*
 * The following tests exercise the Meal class by calling its constructor and
 *  get methods.
 *  
 *  NOTE: This is a black box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */


import android.test.AndroidTestCase;
import recipe_reader.model.Meal;

public class MealTest extends AndroidTestCase {
	
	private Meal testMeal1;
	private Meal testMeal2;
	private Meal testMeal3;
	
	@Override
	public void setUp() {
		testMeal1 = new Meal("Breakfast");
		testMeal2 = new Meal("Snack");
		testMeal3 = new Meal("Dessert");
	}
	
	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests that the constructor creates a new Meal object
	public void testsThatConstructorCreatesANewMealObject(){
		assertNotNull("Tests that the constructor creates a non-null meal object.", testMeal1);
	}
	
	// Tests that the constructor throws an IllegalArgumentException when passed null.
	public void testsThatConstructorThrowsExceptionWhenPassedNullString() throws Exception {
		try{
			Meal testMeal = new Meal(null);
			fail("A meal object should not be able to be made when passed null string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	// Tests that the constructor throws and IllegalArgumentException when passed an invalid string.
	public void testsThatConstructorThrowsExceptionWhenPassedInvalidString() throws Exception {
		try{
			Meal testMeal = new Meal("Kitten Mittens");
			fail("A meal object should not be able to be made when passed an invalid string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	// Tests that the constructor throws and IllegalArgumentException when passed an empty string.
	public void testsThatConstructorThrowsExceptionWhenPassedEmptyString() throws Exception {
		try{
			Meal testMeal = new Meal("");
			fail("A meal object should not be able to be made when passed an empty string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	
	
	/***************************************************	getIdByName TESTS	***************************************************/
	
	// Tests that getIdByName returns correct ID when passed valid input
	public void testsThatGetIdByNameMethodReturnsCorrectIdForValidInput(){
		assertEquals("Tests that getIdByName returns correct ID for a valid meal.", 2, testMeal1.getIdByName("Lunch"));
	}
	
	// Tests that getIdByName throws an IllegalArgumentException when passed a null string.
	public void testsThatGetIdByNameMethodThrowsExceptionWhenPassedANullString() throws Exception {
		try{
			testMeal2.getIdByName(null);
			fail("An exception should be thrown when getIdByName is passed a null string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	// Tests that getIdByName throws an IllegalArgumentException when passed a empty string.
	public void testsThatGetIdByNameMethodThrowsExceptionWhenPassedAnEmptyString() throws Exception {
		try{
			testMeal2.getIdByName("");
			fail("An exception should be thrown when getIdByName is passed an empty string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	// Tests that getIdByName throws an IllegalArgumentException when passed a invalid string.
	public void testsThatGetIdByNameMethodThrowsExceptionWhenPassedAnInvalidString() throws Exception {
		try{
			testMeal2.getIdByName("Fourth Meal");
			fail("An exception should be thrown when getIdByName is passed an invalid string.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	
	
	/***************************************************	getId TESTS	***************************************************/
	
	// Tests that the getId method returns the correct Id
	public void testsThatGetIdReturnsCorrectIdForAMeal(){
		assertEquals("Tests that the getId method returns the correct ID for a Meal.", 6, testMeal3.getId());
	}
	
	
	
	/***************************************************	getName TESTS	***************************************************/
	
	// Tests that the getName method returns the correct name
	public void testsThatGetNameReturnsCorrectNameForAMeal(){
		assertEquals("Tests that the getName method returns the correct name for a Meal.", "Snack", testMeal2.getName());
	}
	
}