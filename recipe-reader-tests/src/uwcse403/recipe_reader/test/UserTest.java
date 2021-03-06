package uwcse403.recipe_reader.test;

/*
 * The following tests exercise the User class by calling its constructor,
 *  get, and set methods. Tests that look for invalid input were purposefully left out
 *  because invalid input is checked by the database.
 *  
 *  NOTE: This is a black box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */

import android.test.AndroidTestCase;
import recipe_reader.model.User;

public class UserTest extends AndroidTestCase {
	
	private User testUser;
	
	@Override
	public void setUp() throws Exception{
		try {
			testUser = new User("TestUser", 777777, "TestUserId");
		} catch (IllegalArgumentException expected){
			fail("Setup was not performed successfully.");
		}
	}
	
	
	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests that a User, given valid parameters, is created
	public void testsThatUserCreatedWithValidParamtersIsCreated() {
		assertNotNull(testUser);
	}
	
	// Tests that a User, given a negative id, is not created. An exception should probably be thrown.
	public void testsThatAUserGivenANegativeIdIsNotCreatedAndExceptionThrown() throws Exception {
		try{
			User negativeId = new User("NegativeUser", -1, "NegativeUserId");
			
			fail("Given a negative ID, a User should not be created and an Exception should be thrown.");
		} catch (IllegalArgumentException expected){
		}
	}

	
	
	/***************************************************	getter METHOD TESTS		***************************************************/
	
	// Tests that getUsername returns the correct username
	public void testsTheGetUsernameMethod(){
		assertEquals("The getUsername method should return the user's correct username.", "TestUser", testUser.getUsername());
	}
	
	// Tests that getUniqueId returns the correct unique ID
	public void testsTheGetUniqueIdMethod(){
		assertEquals("The getUniqueId method should return the user's correct unique ID.", "TestUserId", testUser.getUniqueId());
	}
	
	// Tests that getId returns the correct integer ID
	public void testsTheGetIdMethod(){
		assertEquals("The getID method should return the user's correct integer ID.", 777777, testUser.getId());
	}
	
}