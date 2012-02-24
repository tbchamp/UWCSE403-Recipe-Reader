/**
 * Fragment for searching/browsing recipes by category
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import recipe_reader.model.Category;
import recipe_reader.model.Searcher;
import uwcse403.recipe_reader.R;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PowerSearchTab extends ListFragment{
	
	@Override
	/** When view is created, set adapter for displaying categories. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		try {
			setListAdapter(new ArrayAdapter<Category>(this.getActivity().getApplicationContext(),
			        R.layout.power_search_item, Searcher.getCategories()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO: repopulate list with subcategories when item is clicked.
	}

}
