package uwcse403.recipe_reader.UI_test;

import java.util.List;
import com.jayway.android.robotium.solo.Solo;
import recipe_reader.view.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import recipe_reader.view.RecipeReaderActivity;

public class RobotiumTest extends ActivityInstrumentationTestCase2<RecipeReaderActivity> {
	
	private static final String USER = "Jeremy Lin";
	private static final String PASS = "Linsanity";
	
	private Solo solo;
	
	public RobotiumTest() {
		super("uwcse403.recipe_reader.view", RecipeReaderActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// Tests navigating to Settings tab and impersonating
	// 	everyone's favorite Asian-American basketball player
	public void testLogin() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		selectActionItem(actionBarItems, ActionItem.SETTINGS);
		solo.enterText(0, RobotiumTest.USER);
		solo.enterText(1, RobotiumTest.PASS);
		solo.clickOnButton("Log In");
		assertTrue(solo.searchText("Logged in as"));
	}
	
	// Test that if a favorite is removed in the Favorites tab
	// and then you switch to another tab, the recipe removed
	// is not shown in the list of favorites.
	public void testRemoveFavoriteAndNotShownOnRefresh() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		logIn(actionBarItems);
		selectActionItem(actionBarItems, ActionItem.FAVORITES);
		solo.clickOnImage(6);
		selectActionItem(actionBarItems, ActionItem.HOME);
		selectActionItem(actionBarItems, ActionItem.FAVORITES);
		assertFalse(solo.searchText("Ople"));
		
		
		// restore changed favorites for subsequent test
		selectActionItem(actionBarItems, ActionItem.HOME);
		solo.enterText(0, "Ople");
		solo.clickOnButton("Search");
		solo.clickOnImage(6);
	}
	
	// Tests that clicking on a recipe in search results
	// launches the recipe viewer activity
	public void testStartRecipeViewActivity() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		logIn(actionBarItems);
		solo.enterText(0, "Cookies");
		solo.clickOnButton("Search");
		solo.clickOnImage(5);
		solo.assertCurrentActivity("Should start Recipe View Activity",
					RecipeViewActivity.class);
	}
	
	// Test that if the user is logged out, no favorites are shown.
	public void testLogOutAndNoFavoritesShown() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		logIn(actionBarItems);
		selectActionItem(actionBarItems, ActionItem.SETTINGS);
		solo.clickOnButton("Log Out");
		selectActionItem(actionBarItems, ActionItem.HOME);
		assertEquals(0, solo.getCurrentImageButtons().size());
	}
	
	// Test that pressing the back button in the recipe
	// view activity returns to the previous list of search results
	public void testBackButtonInRecipeReturnsToSearchResultsList() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		logIn(actionBarItems);
		solo.enterText(0, "Cookies");
		solo.clickOnButton("Search");
		solo.clickOnImage(5);
		solo.goBack();
		assert(solo.searchText("Cookies"));
	}
	
	// Test that selecting a category updates the list to show
	// recipes in that category.
	public void testBrowseRecipesByCategory() {
		List<View> actionBarItems = solo.getView(
				com.actionbarsherlock.internal.widget.ActionBarView.class, 0)
					.getFocusables(0);
		logIn(actionBarItems);
		selectActionItem(actionBarItems, ActionItem.BROWSE);
		solo.clickOnText("Side Dish");
		assert(solo.searchText("Cinnabon"));
	}
	
	// Helper for selecting an action bar item
	private void selectActionItem(List<View> v, ActionItem a) {
		solo.clickOnView(v.get(a.index()));
	}
	
	// Values needed for navigating between action bar items 
	private enum ActionItem {
		HOME(1),
		BROWSE(2),
		FAVORITES(3),
		SETTINGS(4);
		
		private int index;
		private ActionItem(int i) {
			this.index = i;
		}
		
		private int index() {
			return index;
		}
	}
	
	// Many actions to be tested require the user to be logged in, 
	// so this opens settings tab, logs in, and returns to the home
	// screen.
	private void logIn(List<View> views) {
		selectActionItem(views, ActionItem.SETTINGS);
		solo.enterText(0, RobotiumTest.USER);
		solo.enterText(1, RobotiumTest.PASS);
		solo.clickOnButton("Log In");
		solo.clickOnView(views.get(1));
	}

}