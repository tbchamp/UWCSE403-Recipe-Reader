package recipe_reader.comparator;

import java.util.Comparator;

import recipe_reader.model.RecipeOverview;

/**
 * RecipeCategoryComparator allows a list of recipes to be sorted by category
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class RecipeCategoryComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		if (o1.getCategory() != o2.getCategory()) {
			return o1.getCategory().toString()
				.compareTo(o2.getCategory().toString());
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
