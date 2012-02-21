package uwcse403.recipe_reader;

import java.util.Comparator;

public class RecipeRatingComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		if (o1.getRating() != o2.getRating()) {
			return (int) Math.signum(o2.getRating() - o1.getRating());
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}

}
