package uwcse403.recipe_reader;

import java.util.Comparator;

public class RecipeTitleComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
