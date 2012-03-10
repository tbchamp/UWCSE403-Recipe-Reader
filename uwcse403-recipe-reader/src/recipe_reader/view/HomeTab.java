package recipe_reader.view;

import uwcse403.recipe_reader.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * HomeTab is the fragment shown on application load, and contains a search box
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class HomeTab extends Fragment {
	
	@Override
	/** @inheritDoc */
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

		/** @inheritDoc */
		public void onClick(View v) {
			FragmentTransaction ft = 
				((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			if (resultsFragment == null) {
				resultsFragment = Fragment.instantiate(getActivity(), SearchResultsScreen.class.getName());
			}
			View box = ((View) v.getParent()).findViewById(R.id.searchBox);
			String search = ((TextView) box).getText().toString();
			((SearchResultsScreen) resultsFragment).setSearchPhrase(search);
			ft.replace(android.R.id.content, resultsFragment, "Search Results");
			ft.addToBackStack("Search Results");
			ft.commit();
		}
	}

}
