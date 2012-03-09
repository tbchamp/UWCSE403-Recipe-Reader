/**
 * Fragment for displaying list of recipe search results.
 * @author Kristin Ivarson
 */
package recipe_reader.view;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import recipe_reader.comparator.RecipeCategoryComparator;
import recipe_reader.comparator.RecipeRatingComparator;
import recipe_reader.comparator.RecipeTitleComparator;
import recipe_reader.model.RecipeOverview;
import uwcse403.recipe_reader.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SearchResultsScreen extends Fragment {
	private List<String> searchKeywords;
	private int catNumber;
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.results_screen, container, false);
		attachButtonListeners(v);
		FragmentTransaction ft = 
			((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		SearchResultsList resultsFragment = (SearchResultsList) Fragment.instantiate(getActivity(), 
				SearchResultsList.class.getName());
		if(searchKeywords != null) {
			resultsFragment.setSearchPhrase(searchKeywords);
		} else {
			resultsFragment.setCatNumber(catNumber);
		}
		resultsFragment.setComparator(new RecipeTitleComparator());
		ft.replace(R.id.list_frag, resultsFragment, "Search Results");
		ft.commit();
		v.findViewById(R.id.sort_title).setEnabled(false);
		return v;
	}
	
	private void attachButtonListeners(View v) {
		v.findViewById(R.id.sort_category)
			.setOnClickListener(new ClickListener(new RecipeCategoryComparator()));
		v.findViewById(R.id.sort_title)
			.setOnClickListener(new ClickListener(new RecipeTitleComparator()));
		v.findViewById(R.id.sort_rating)
			.setOnClickListener(new ClickListener(new RecipeRatingComparator()));
	}
	
	/**
	 * Set phrase to search and display results for.
	 * @param phrase String to search for.
	 */
	public void setSearchPhrase(String phrase) {
		searchKeywords = Arrays.asList(phrase.split(" "));
	}
	
	/**
	 * Sets the category number to display results from
	 * @param c Category ID number
	 */
	public void setCatNumber(int c) {
		catNumber = c;
	}
	
	private class ClickListener implements View.OnClickListener {
		private SearchResultsList frag;
		private Comparator<RecipeOverview> comp;
		private ClickListener(Comparator<RecipeOverview> comp) {
			this.comp = comp;
		}
		public void onClick(View v) {
			FragmentTransaction ft = 
				((FragmentActivity) getActivity())
				.getSupportFragmentManager().beginTransaction();
			if (frag == null) {
				frag = (SearchResultsList) Fragment.instantiate(getActivity(), 
						SearchResultsList.class.getName());
				frag.setSearchPhrase(searchKeywords);
				frag.setCatNumber(catNumber);
				frag.setComparator(comp);
			}
			ft.replace(R.id.list_frag, frag, "Search Results");
			ft.commit();
			((View)v.getParent()).findViewById(R.id.sort_category)
				.setEnabled(true);
			((View)v.getParent()).findViewById(R.id.sort_title)
				.setEnabled(true);
			((View)v.getParent()).findViewById(R.id.sort_rating)
				.setEnabled(true);
			v.setEnabled(false);
		}
	}

}
