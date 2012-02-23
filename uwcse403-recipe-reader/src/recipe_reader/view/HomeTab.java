/**
 * Fragment shown on application load, with search box.
 * Kristin Ivarson (kristini@cs)
 */
package recipe_reader.view;

import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.id;
import uwcse403.recipe_reader.R.layout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeTab extends Fragment {
	
	@Override
	/** When view is created, inflate layout and add listener to button. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home_screen, container, false);
		Button button = (Button) v.findViewById(R.id.searchButton);
		button.setOnClickListener(new ClickListener());
		return v;
    }
	
	/**
	 * Listener for initiating search when search button clicked.
	 */
	private class ClickListener implements View.OnClickListener {
		private Fragment resultsFragment;

		public void onClick(View v) {
			FragmentTransaction ft = 
				((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			if (resultsFragment == null) {
				resultsFragment = Fragment.instantiate(getActivity(), SearchResultsScreen.class.getName());
			}
			ft.replace(android.R.id.content, resultsFragment, "Search Results");
			ft.commit();
		}
	}

}