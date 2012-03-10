package uwcse403.recipe_reader.integration_test;

/*
 * The following tests exercise the Settings class by calling its constructor, and
 *  signIn methods.
 *  
 *  NOTE: This is a white box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */

import recipe_reader.model.Settings;
import recipe_reader.model.User;
import android.test.AndroidTestCase;

public class SettingsTest extends AndroidTestCase {

	/* IMPORTANT
	 * 	These variables are needed for the createUser method. Increment
	 * 	the user number before every run of tests.
	 */
	private final String TEST_USER = "TESTUSER3";
	private final String TEST_PASSWORD = "TEST PASSWORD";
	
	public class UserStub {
		public String getUsername() {
			return "Alisa";
		}
		
		public String getPassword(){
			return "Yamanaka";
		}
	}                                 

	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/

	// Tests that the Settings object is not null when first created
	public void testsThatSettingsTabNotNullWhenFirstCreated() {
		assertNotNull("Tests that Settings object is not null upon creation.", new Settings());
	}
	
	
	
	/***************************************************	createUser TESTS	***************************************************/
	
	/* NOTICE:
	 * 	This method is commented out during testing not performed by people
	 * 	who may be unaware of it's magical properties.
	 * 	Actually, that's only partially true. People who don't know to change
	 * 	the user number at the top of the file shouldn't run this test.
	 */
	
	// Tests that a new user is created when new credentials are added
	/*public void testsThatNewUserIsCreatedWhenNewCredentialsArePassedToCreateUserMethod(){
		Settings testSettings = new Settings();
		UserStub user = new UserStub();
		assertTrue("Tests that the createUser function adds a new user successfully.", testSettings.createUser(TEST_USER, TEST_PASSWORD));
	}*/
	
	
	
	/***************************************************	signIn TESTS	***************************************************/
	
	// Tests that a registered user is signed in successfully using their username and password
	public void testsTheSignInMethodSucceedsForAUserAlreadyInTheDatabaseUsingTheirUsernameAndPassword(){
		Settings testSettings = new Settings();
		UserStub user = new UserStub();
		
		assertTrue("Tests that a user already in the system is signed in successfully.", testSettings.signIn(user.getUsername(), user.getPassword()));
	}
	
	// Tests that a registered user is signed in successfully using their uniqueId
	public void testsTheSignInMethodSucceedsForAUserAlreadyInTheDatabaseUsingTheirUniqueId(){
		Settings testSettings = new Settings();
		UserStub user = new UserStub();
		
		testSettings.createUser(user.getUsername(), user.getPassword());
		User testUser = testSettings.getUser();
		testSettings.signOut();
		assertTrue("Tests that a user is successfully signed in using their unique ID.", testSettings.signIn(testUser.getUniqueId()));
	}
	
	// Tests that a user cannot sign in when supplying the wrong password
	public void testsThatUserCannotSignInWhenTheySupplyAnIncorrectPassword(){
		Settings testSettings = new Settings();
		UserStub user = new UserStub();
		
		assertFalse("Tests that a user cannot sign in when they supply the wrong password.", 
				testSettings.signIn(user.getUsername(), "WrongPassword!"));
	}
	
	// Tests that a user cannot sign in when supplying a password that is their password, but in dfferent case
	public void testsThatUserCannotSignInWithCorrectPasswordButWrongCaseLetters(){
		Settings testSettings = new Settings();
		UserStub user = new UserStub();
		
		assertFalse("Tests that a user cannot sign in when they supply their password in a different case", 
				testSettings.signIn(user.getUsername(), user.getPassword().toUpperCase()));
	}
	
	
}
