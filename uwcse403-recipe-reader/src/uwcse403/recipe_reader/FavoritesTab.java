/**
 * Fragment for displaying list of user's favorites.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

import java.util.ArrayList;

import uwcse403.recipe_reader.RecipeOverview.Category;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FavoritesTab extends ListFragment {
	
	@Override
	/** When view is created, */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		// Temporary hard-coded data: to be replaced by List of RecipeOverviews from Searcher.java(?) 
		ArrayList<RecipeOverview> recipeList = new ArrayList<RecipeOverview>();
		recipeList.add(new RecipeOverview("Very Delicious Hamburgers", Category.MAIN_COURSE, true, 5.0));
		recipeList.add(new RecipeOverview("Mediocre Hamburgers", Category.MAIN_COURSE, true, 5.0));
		//
		
		
		setListAdapter(new SearchResultAdapter(
				this.getActivity().getApplicationContext(), R.layout.list_item, recipeList));
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this.getActivity().getApplicationContext(), RecipeViewActivity.class);
		Bundle b = new Bundle();
		TextView tv = (TextView) v.findViewById(R.id.name);
		String rn = (String) tv.getText();
		b.putString("recipeName", rn);
		i.putExtras(b);
		startActivity(i);
	}

}
