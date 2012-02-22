/**
 * Fragment for displaying list of recipe search results.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsScreen extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.results_screen, container, false);
		FragmentTransaction ft =
			((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		Fragment resultsFragment = Fragment.instantiate(getActivity(),
				SearchResultsList.class.getName());
		ft.replace(R.id.list_frag, resultsFragment, "Search Results");
		ft.commit();
		return v;
	}
}