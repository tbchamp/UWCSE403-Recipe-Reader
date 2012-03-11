package uwcse403.recipe_reader.integration_test;

/*
 * The following tests exercise the Searcher class by calling its getMethods.
 *  
 *  NOTE: This is a white box test.
 * 
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 */

import java.util.ArrayList;
import java.util.List;

import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.Settings;
import recipe_reader.model.User;

import android.test.AndroidTestCase;

public class SearcherTest extends AndroidTestCase {
	
	private Settings testSettings;
	private User testUser;
	private int recipeId;
	
	@Override
	public void setUp(){
		testSettings = new Settings();
		testSettings.signIn("Alisa", "Yamanaka");
		
		testUser = testSettings.getUser();
		
		recipeId = Searcher.getFavoritesByUser(testUser).get(0).getId();
	}
	
	
	
	/**********************************************	getRecipeOverviewsByKeywords TESTS	*********************************************/
	
	// Tests that the getRecipeOverviewsByKeywords method returns RecipeOverviews
	//	when passed valid keywords and a valid User object
	public void testsThatTheGetRecipeOverviewsByKeywordsDoesntFailWhenPassedValidParameters(){
		List<String> keywords = new ArrayList<String>();
		keywords.add("apple");
		keywords.add("cheesecake");
		assertNotNull("Tests that the Searcher returns a non-null list of RecipeOveview when getRecipeOverviewByKeywords is " +
				"called with valid parameters.", Searcher.getRecipeOverviewsByKeywords(keywords, testUser));
	}
	
	// Tests that the getRecipeOverviewsByKeywords returns an empty list when no keywords are specified
	public void testsThatTheGetRecipeOverviewsByKeywordsReturnsAnEmptyListWhenPassedAnEmptyKeywordsList(){
		List<String> keywords = new ArrayList<String>();
		assertEquals("Tests that the Searcher returns an empty list of RecipeOveview when getRecipeOverviewByKeywords is " +
				"passed an empty list of keywords.", 0, Searcher.getRecipeOverviewsByKeywords(keywords, testUser).size());
	}
	
	
	
	/**********************************************		getFavoritesByUser TESTS	*********************************************/
	
	// Tests that the getFavoritesByUser method doesn't fail when passed a valid user.
	public void testsThatGetFavoritesByUserMethodReturnsANonNullListOfROsWhenCalledOnValidUser(){
		assertNotNull("Tests that the getFavoritesByUser method returns a non-nulln list of RecipeOverview objects when " +
				"called on a valid User.", Searcher.getFavoritesByUser(testUser));
	}
	
	
	
	/**********************************************		getOverviewFromId TESTS		*********************************************/
	
	// Tests that the getOverviewFromId test returns the correct RecipeOverview
	public void testsThatTheGetOverviewFromIdMethodReturnsTheCorrectRecipeOverview(){
		RecipeOverview r = Searcher.getFavoritesByUser(testUser).get(1);
		RecipeOverview r2 = Searcher.getOverviewFromId(r.getId(), testUser);
		
		boolean sameName = r.getName().equals(r2.getName());
		boolean sameCategory = r.getCategory().getId() == r2.getCategory().getId();
		boolean sameRating = r.getRating() == r2.getRating();
		boolean sameDescription = r.getDescription().equals(r2.getDescription());
		boolean sameId = r.getId() == r2.getId();
				
		assertTrue("Tests that the getOverviewFromId method returns the RecipeOverview that corresponds to the Id.",
				sameName && sameCategory && sameRating && sameDescription && sameId);
	}
	
	// Tests that the getOverviewFromId test returns null when passed a negative value id
	public void testsThatTheGetOverviewFromIdMethodReturnsNullWhenPassedNegativeId(){
		assertNull("Tests that the getOverviewFromId method returns null when passed a negative ID.",
				Searcher.getOverviewFromId(-1, testUser));
	}
	
	
	
	/**********************************************		getOverviewFromId TESTS		*********************************************/

	// Tests that the isFavorite method returns true for a favorited recipe
	public void testsThatTrueIsReturnedWhenIsFavoriteMethodPassedValidUserAndIdOfAFavoritedRecipe(){
		assertTrue("Tests that true is returned by isFavorite when passed the ID of a favorited recipe for the User.",
				Searcher.isFavorite(testUser, recipeId));
	}
	
	
	
	/**********************************************		getMeals TESTS		*********************************************/
	
	// Tests that the getMeals method returns a non-null list of meals
	public void testsThatTheGetMealsMethodReturnsANonNullListOfMeals(){
		assertNotNull("Tests that the getMeals method returns a non-null list.", Searcher.getMeals());
	}
	
	
	
	/**********************************************		getCategories TESTS		*********************************************/
	
	// Tests that the getCategories method returns a non-null list of categories
	public void testsThatTheGetCategoriesMethodReturnsANonNullListOfCategories(){
		assertNotNull("Tests that the getCategories method returns a non-null list.", Searcher.getCategories());
	}
	
	
	
	/**********************************		getOverviewByCategory TESTS		**********************************/
	
	// Tests that the getOverviewByCategory method returns a non-null list of RecipeOverviews
	public void testsThatTheGetOverviewByCategoryMethodReturnsANonNullListOfRecipeOverviews(){
		assertNotNull("Tests that the getOverviewByCategory method returns a non-null list.",
				Searcher.getOverviewByCategory(2, testUser));
	}
	
	
	
	/**********************************		getOverviewByMealCategory TESTS		**********************************/
	
	// Tests that the getOverviewByMealCategory method returns a non-null list of RecipeOverviews
	public void testsThatTheGetOverviewByMealCategoryMethodReturnsANonNullListOfRecipeOverviews(){
		assertNotNull("Tests that the getOverviewByMealCategory method returns a non-null list.",
				Searcher.getOverviewByMealCategory(3, 6, testUser));
	}
	
	
	
	
	
	
}
