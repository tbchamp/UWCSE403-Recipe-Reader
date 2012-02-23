/**
 * Fragment for searching/browsing recipes by category
 * Kristin Ivarson (kristini@cs)
 */
package recipe_reader.view;

import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.array;
import uwcse403.recipe_reader.R.layout;
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
		setListAdapter(ArrayAdapter.createFromResource(this.getActivity().getApplicationContext(),
                R.array.categories, R.layout.power_search_item));
		
		return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO: repopulate list with subcategories when item is clicked.
	}

}
