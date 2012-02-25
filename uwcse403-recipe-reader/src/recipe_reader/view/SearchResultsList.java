/**
 * Fragment for displaying list of recipes with 
 * information like name, rating, and whether it
 * is one of the user's favorites.
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import java.util.ArrayList;
import java.util.List;

import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.User;
import uwcse403.recipe_reader.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchResultsList extends ListFragment {
	private List<String> searchKeywords;

	@Override
	/** @inheritDoc */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		List<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		
		try {
			User user = ((RecipeReaderActivity) this.getActivity()).getSettings().getUser();
			recipeList = 
				Searcher.getRecipeOverviewsByKeywords(searchKeywords, user);
			if (recipeList == null) {
				recipeList = new ArrayList<RecipeOverview>();
			}
		} catch (Exception e) {
			Log.i("MYNOTE", "error: " + e);
		}
		setListAdapter(new SearchResultAdapter(
				this.getActivity().getApplicationContext(), R.layout.list_item, recipeList));
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	/** @inheritDoc */
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this.getActivity().getApplicationContext(), RecipeViewActivity.class);
		Bundle b = new Bundle();
		RecipeOverview ro = (RecipeOverview) l.getItemAtPosition(position);
		int idNum = ro.getId();
		b.putInt("recipeID", idNum);
		i.putExtras(b);
		startActivity(i);
	}
	
	/**
	 * @param phrase List of keywords to search for 
	 * and display matches.
	 */
	public void setSearchPhrase(List<String> phrase) {
		searchKeywords = phrase;
	}

}
