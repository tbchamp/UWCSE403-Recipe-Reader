package recipe_reader.view;

import recipe_reader.model.Category;
import recipe_reader.model.Searcher;
import uwcse403.recipe_reader.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * PowerSearchTab is a fragment used to search for recipes by category
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class PowerSearchTab extends ListFragment{
	
	private Fragment resultsFragment;

	@Override
	/** When view is created, set adapter for displaying categories. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		try {
			setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
			        R.layout.power_search_item, Searcher.getCategoryStrings()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FragmentTransaction ft = 
			((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		if (resultsFragment == null) {
			resultsFragment = Fragment.instantiate(getActivity(), SearchResultsScreen.class.getName());
		}
		String cat = (String) l.getItemAtPosition(position);
		Category c = new Category(cat);
		int catID = c.getId();
		((SearchResultsScreen) resultsFragment).setCatNumber(catID);
		ft.replace(android.R.id.content, resultsFragment, "Search Results");
		ft.addToBackStack("Search Results");
		ft.commit();
	}

}
