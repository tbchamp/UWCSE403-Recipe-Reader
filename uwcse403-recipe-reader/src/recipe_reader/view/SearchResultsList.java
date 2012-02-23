package recipe_reader.view;

import java.util.ArrayList;

import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.layout;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsList extends ListFragment {


	@Override
	/** When view is created, create adapter with data to populate list. */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		ArrayList<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		Searcher s = new Searcher();
		try {
			//recipeList.add(s.transaction_getRecipeOverviewById(1));
		} catch (Exception e) {
			Log.i("MYNOTE", "error: " + e);
		}
		setListAdapter(new SearchResultAdapter(
				this.getActivity().getApplicationContext(), R.layout.list_item, recipeList));
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
