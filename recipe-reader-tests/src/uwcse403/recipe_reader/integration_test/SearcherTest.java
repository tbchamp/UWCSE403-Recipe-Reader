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

import recipe_reader.model.Searcher;
import recipe_reader.model.Settings;

import android.test.AndroidTestCase;

public class SearcherTest extends AndroidTestCase {
	
	private Settings testSettings;
	
	@Override
	public void setUp(){
		testSettings = new Settings();
		testSettings.signIn("Alisa", "Yamanaka");
	}
	
	
	
	/**********************************************	getRecipeOverviewsByKeywords TESTS	*********************************************/
	
	// Tests that the getRecipeOverviewsByKeywords method returns RecipeOverviews
	//	when passed valid keywords and a valid User object
	public void testsThatTheGetRecipeOverviewsByKeywordsDoesntFailWhenPassedValidParameters(){
		List<String> keywords = new ArrayList<String>();
		keywords.add("apple");
		keywords.add("cheesecake");
		assertNotNull("Tests that the Searcher returns a non-null list of RecipeOveview when getRecipeOverviewByKeywords is " +
				"called with valid parameters.", Searcher.getRecipeOverviewsByKeywords(keywords, testSettings.getUser()));
	}
	
	// Tests that the getRecipeOverviewsByKeywords returns an empty list when no keywords are specified
	public void testsThatTheGetRecipeOverviewsByKeywordsReturnsAnEmptyListWhenPassedAnEmptyKeywordsList(){
		List<String> keywords = new ArrayList<String>();
		assertEquals("Tests that the Searcher returns an empty list of RecipeOveview when getRecipeOverviewByKeywords is " +
				"passed an empty list of keywords.", 0, Searcher.getRecipeOverviewsByKeywords(keywords, testSettings.getUser()).size());
	}
	
	
	
	/**********************************************	getFavoritesByUser TESTS	*********************************************/
	
	// Tests that the getFavoritesByUser method doesn't fail when passed a valid user.
	public void testsThatGetFavoritesByUserMethodReturnsANonNullListOfROsWhenCalledOnValidUser(){
		assertNotNull("Tests that the getFavoritesByUser method returns a non-nulln list of RecipeOverview objects when " +
				"called on a valid User.", Searcher.getFavoritesByUser(testSettings.getUser()));
	}
	
	
	
	
	
	
	
}
