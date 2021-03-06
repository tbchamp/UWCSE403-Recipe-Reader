package recipe_reader.view;

import java.util.ArrayList;

import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.User;
import uwcse403.recipe_reader.R;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * FavoritesTab is a fragment for displaying a list of the user's favorite recipes
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class FavoritesTab extends ListFragment {
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		User u = ((RecipeReaderActivity) this.getActivity()).getSettings().getUser();
		ArrayList<RecipeOverview> recipeList = null;
		try {
			recipeList = (ArrayList<RecipeOverview>) Searcher.getFavoritesByUser(u);
			if (recipeList.isEmpty()) {
				recipeList = new ArrayList<RecipeOverview>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setListAdapter(new SearchResultAdapter(
				this.getActivity(), R.layout.list_item, recipeList));
		
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
		i.putExtras(b); //Sends the id number of the recipe clicked to the view activity
		startActivity(i);
	}

}
