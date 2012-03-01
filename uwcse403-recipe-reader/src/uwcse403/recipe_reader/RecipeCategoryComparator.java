package uwcse403.recipe_reader;

import java.util.Comparator;

import recipe_reader.model.RecipeOverview;

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
