/**
 * Fragment for displaying list of recipe search results.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsScreen extends ListFragment {
	
	
	@Override
	/** When view is created, create adapter with data to populate list. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		// Temporary hard-coded data: to be replaced by List of RecipeOverviews from Searcher.java(?) 
		ArrayList<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		recipeList.add(new RecipeOverview("Very Delicious Hamburgers", R.drawable.hamburger, true));
		recipeList.add(new RecipeOverview("Mediocre Hamburgers", R.drawable.hamburger, true));
		//
		
		setListAdapter(new SearchResultAdapter(
				this.getActivity().getApplicationContext(), R.layout.list_item, recipeList));
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }

}
