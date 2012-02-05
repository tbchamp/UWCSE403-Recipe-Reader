/**
 * Fragment for displaying list of recipe search results.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

import java.util.ArrayList;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsScreen extends ListFragment {
	
	
	@Override
	/** When view is created, create adapter with data to populate list. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		
		ArrayList<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		Searcher s = new Searcher();
		try {
			s.openConnection();
			s.prepareStatements();
			recipeList.add(s.transaction_getRecipeOverviewById(1));
			s.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("MYNOTE", "error: " + e);
		}
		//
		Log.i("MYNOTE", recipeList.toString());
		setListAdapter(new SearchResultAdapter(
				this.getActivity().getApplicationContext(), R.layout.list_item, recipeList));
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }

}
