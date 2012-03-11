package uwcse403.recipe_reader.integration_test;

/*
 * The following tests exercise the Generator class by calling its getMethods.
 *  
 *  NOTE: This is a white box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */

import recipe_reader.model.Generator;
import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.Settings;
import recipe_reader.model.User;
import android.test.AndroidTestCase;

public class GeneratorTest extends AndroidTestCase {

	private User testUser;
	private RecipeOverview testRO;
	
	@Override
	public void setUp(){
		Settings testSettings = new Settings();
		testSettings.signIn("Alisa", "Yamanaka");
		testUser = testSettings.getUser();
		testRO = Searcher.getFavoritesByUser(testUser).get(0);
	}
	
	
	
	/**********************************************		getRecipe TESTS		*********************************************/
	
	// Tests that the getRecipe method returns a non-null Recipe when passed valid recipe id and user parameters
	public void testsThatGetRecipeMethodReturnsANonNullRecipeWhenPassedValidRecipeIdAndUserParamters(){
		assertNotNull("Tests that getRecipe returns non-null Recipe when passed valid recipe ID and user parameters.",
				Generator.getRecipe(testRO.getId(), testUser));
	}
	
	// Tests that the getRecipe method returns a non-null Recipe when passed valid RecipeOverview
	public void testsThatGetRecipeMethodReturnsANonNullRecipeWhenPassedAValidRecipeOverview(){
		assertNotNull("Tests that getRecipe returns non-null Recipe when passed valid recipe ID and user parameters.",
				Generator.getRecipe(testRO));
	}

	
}
