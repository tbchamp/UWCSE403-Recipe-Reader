/**
 * Fragment for displaying list of user's favorites.
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import java.util.ArrayList;

import recipe_reader.model.Category;
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

public class FavoritesTab extends ListFragment {
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		// Temporary hard-coded data: to be replaced by List of RecipeOverviews from Searcher.java(?) 
		User u = ((RecipeReaderActivity) this.getActivity()).getSettings().getUser();
		ArrayList<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		try {
			recipeList = (ArrayList<RecipeOverview>) Searcher.getFavoritesByUser(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		RecipeOverview ro = new RecipeOverview("cereal", new Category(6, "Breakfast"), true, "good", 1);
		recipeList.add(ro);
		
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
		String rn = ro.getName();
		b.putString("recipeName", rn);
		int idNum = ro.getId();
		b.putInt("recipeID", idNum);
		i.putExtras(b);
		startActivity(i);
	}

}
