package uwcse403.recipe_reader.test;

/*
 * The following tests exercise the Directions class by calling its constructor, and
 *  get methods. Tests that look for invalid input were purposefully left out
 *  because invalid input is checked by the database.
 *  
 *  NOTE: This is a white box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import recipe_reader.model.Directions;

public class DirectionsTest extends AndroidTestCase {
	
	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests that the Directions constructor creates a non-null Directions object
	public void testsTheConstructionOfANewDirectionsObjectAndMakesSureItIsntNull() {
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		directionsList.add("WATCH IT BURN!");
		directionsList.add("Naptime.");
		
		Directions testDirections = new Directions(directionsList);
		
		assertNotNull("Calling constructor for new Directions shouldn't leave it being null.", testDirections);
	}
	
	// Tests that a Directions object isn't made when passed null
	public void testsThatDirectionsConstructorCantBePassedNullValue(){
		try{
			Directions d = new Directions(null);
			
			fail("You shouldn't be able to pass null to the Directions constructor. An exception should be thrown.");
		} catch (IllegalArgumentException expected){
		}
	}
	
	// tests that a Directions object passed an empty list is still created.
	public void testsThatDirectionsObjectIsMadeWhenPassedEmptyList(){
		List<String> directionsList  = new ArrayList<String>();

		Directions d = new Directions(directionsList);
		
		assertNotNull("Tests that Directions object is made with empty list.", d);
	}
	
	
	
	/***************************************************	getNextDirection TESTS		***************************************************/
	
	// Tests that the getNextDirection method returns the next direction in the recipe.
	public void testsThatTheGetNextDirectionMethodReturnsTheNextDirectionInTheRecipe(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		
		Directions testDirections = new Directions(directionsList);
		
		assertEquals("Tests that getNextDirection works when called once.", "2. Preheat oven to 1 million " +
				"degrees F.", testDirections.getNextDirection());
	}
	
	// Tests that the getNextDirection method returns the 3rd direction after being called once already.
	public void testsThatTheGetNextDirectionMethodReturnsTheRightDirectionAfterBeingCalledTwice(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		
		Directions testDirections = new Directions(directionsList);
		testDirections.getNextDirection();
		
		assertEquals("Tests that getNextDirection works when called once.", "3. Place chicken in oven.", testDirections.getNextDirection());
	}
	
	// Tests that the getNextDirection method throws an IllegalStateException when called on the last direction.
	public void testsThatGetNextDirectionThrowsExceptionWhenCalledOnLastDirection(){
		try{
			List<String> directionsList  = new ArrayList<String>();
			directionsList.add("Gather ingredients.");
			directionsList.add("Preheat oven to 1 million degrees F.");
			directionsList.add("Place chicken in oven.");
			
			Directions testDirections = new Directions(directionsList);
			testDirections.getNextDirection();
			testDirections.getNextDirection();
			testDirections.getNextDirection();
			
			fail("Calling getNextDirection method on last direction should throw an exception.");
		} catch (IllegalStateException expected){
		}
	}
	
	
	
	/***************************************************	getPreviousDirection TESTS		***************************************************/
	
	// Tests that the getPreviousDirection method returns the 1st direction after getNextDirection is called once.
	public void testsThatTheGetPreviousDirectionMethodReturnsTheFirstDirectionAfterBeingCalledAfterGetNextDirectionMethod(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		
		Directions testDirections = new Directions(directionsList);
		testDirections.getNextDirection();
		
		assertEquals("Tests that getPreviousDirection works when called once after getNextDirection.",
				"1. Gather ingredients.", testDirections.getPreviousDirection());
	}
	
	// Tests that the getPreviousDirection method returns the 1st direction after getNextDirection is called twice and
	//	getPreviousDirection has been called once.
	public void testsThatTheGetPreviousDirectionMethodReturnsCorrectDirectionAfterBeingCalledTwiceAfterGetNextDirectionMethodWasCalledTwice(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		
		Directions testDirections = new Directions(directionsList);
		testDirections.getNextDirection();
		testDirections.getNextDirection();
		testDirections.getPreviousDirection();
		
		assertEquals("Tests that getPreviousDirection works when called getNextDirection is called once, and " +
				"getPreviousDirection was called once.", "1. Gather ingredients.", testDirections.getPreviousDirection());
		
	}
	
	// Tests that the getPreviousDirection method throws an IllegalStateException when called on the first direction.
	public void testsThatGetPreviousDirectionThrowsExceptionWhenCalledOnFirstDirection(){
		try{
			List<String> directionsList  = new ArrayList<String>();
			directionsList.add("Gather ingredients.");
			directionsList.add("Preheat oven to 1 million degrees F.");
			directionsList.add("Place chicken in oven.");
			
			Directions testDirections = new Directions(directionsList);
			testDirections.getPreviousDirection();
			
			fail("Calling getPreviousDirection method on first direction should throw an exception.");
		} catch (IllegalStateException expected){
		}
	}
	
	
	/***************************************************	repeatDirection TESTS		***************************************************/
	
	// Tests that repeatDirection repeats the first direction.
	public void testsThatRepeatDirectionMethodReturnsFirstDirectionWhenCalledAfterDirectionsObjectIsMade(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		directionsList.add("WATCH IT BURN!");
		directionsList.add("Naptime.");
		
		Directions testDirections = new Directions(directionsList);
		assertEquals("Tests that first direction is returned when repeatDirection called on first step.",
				"1. Gather ingredients.", testDirections.repeatDirection());
	}
	
	// Tests that the repeatDirection returns the same direction after getNextDirection has been called once.
	public void testsThatRepeatDirectionMethodReturnsTheCorrectDirectionAfterGetNextDirectionMethodCalledOnce(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		directionsList.add("WATCH IT BURN!");
		directionsList.add("Naptime.");
		
		Directions testDirections = new Directions(directionsList);
		testDirections.getNextDirection();
		assertEquals("Tests that second direction is returned when repeatDirection called after getNextDirection called once.",
				"2. Preheat oven to 1 million degrees F.", testDirections.repeatDirection());
	}
	
	
	
	/***************************************************	getDirectionList TESTS		***************************************************/
	
	// Tests that the getDirectionList method returns the list of directions
	public void testsThatGetDirectionListMethodReturnsListOfDirections(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		directionsList.add("WATCH IT BURN!");
		directionsList.add("Naptime.");
		
		Directions testDirections = new Directions(directionsList);
		assertEquals("Tests that getDirectionList returns the list of the Direction's steps.", directionsList, testDirections.getDirectionList());
	}
	
	
	/***************************************************	getNumSteps TESTS		***************************************************/
	
	// Tests that the getNumSteps method returns 0 for a Direction object with an empty list of directions.
	public void testsThatGetNumStepsReturns0ForDirectionObjectWithEmptyListOfDirections(){
		List<String> directionsList  = new ArrayList<String>();
		
		Directions testDirections = new Directions(directionsList);
		
		assertEquals("A Directions object made up of no directions should return 0 when getNumSteps is called.", 0, testDirections.getNumSteps());
	}
	
	// Tests that the getNumSteps returns correct number of steps for a Direction object.
	public void testsThatGetNumStepsReturnsTheCorrectNumberOfStepsForADirectionObject(){
		List<String> directionsList  = new ArrayList<String>();
		directionsList.add("Gather ingredients.");
		directionsList.add("Preheat oven to 1 million degrees F.");
		directionsList.add("Place chicken in oven.");
		directionsList.add("WATCH IT BURN!");
		directionsList.add("Naptime.");
		
		Directions testDirections = new Directions(directionsList);
		
		assertEquals("Tests that getNumSteps method returns correct number of steps for a Direction objecy.", 5, testDirections.getNumSteps());
	}
	
	
	
	
	
	
	
}