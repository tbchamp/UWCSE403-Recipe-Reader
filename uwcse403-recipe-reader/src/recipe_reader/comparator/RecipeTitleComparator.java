package recipe_reader.comparator;

import java.util.Comparator;

import recipe_reader.model.RecipeOverview;

public class RecipeTitleComparator implements Comparator<RecipeOverview> {

	public int compare(RecipeOverview o1, RecipeOverview o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
