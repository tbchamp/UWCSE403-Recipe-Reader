/**
 * Fragment for displaying list of recipe search results.
 * @author Kristin Ivarson
 */
package recipe_reader.view;


import java.util.Arrays;
import java.util.List;
import uwcse403.recipe_reader.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultsScreen extends Fragment {
	private List<String> searchKeywords;
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.results_screen, container, false);
		FragmentTransaction ft = 
			((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		Fragment resultsFragment = Fragment.instantiate(getActivity(), 
				SearchResultsList.class.getName());
		((SearchResultsList) resultsFragment).setSearchPhrase(searchKeywords);
		ft.replace(R.id.list_frag, resultsFragment, "Search Results");
		ft.commit();
		return v;
		
		
	}
	
	/**
	 * Set phrase to search and display results for.
	 * @param phrase String to search for.
	 */
	public void setSearchPhrase(String phrase) {
		searchKeywords = Arrays.asList(phrase.split(" "));
	}

}
