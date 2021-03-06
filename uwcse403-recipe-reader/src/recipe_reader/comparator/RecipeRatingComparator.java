package recipe_reader.comparator;

import java.util.Comparator;

import recipe_reader.model.RecipeOverview;

/**
 * RecipeRatingComparator allows a list of recipes to be sorted by rating
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class RecipeRatingComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		if (o1.getRating() != o2.getRating()) {
			return (int) Math.signum(o2.getRating() - o1.getRating());
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}

}
