package recipe_reader.comparator;

import java.util.Comparator;

import recipe_reader.model.RecipeOverview;

/**
 * RecipeTitleComparator allows a list of recipes to be sorted in alphabetical order
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class RecipeTitleComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
