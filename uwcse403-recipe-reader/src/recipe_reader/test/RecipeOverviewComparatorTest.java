/**
 * Tests for 3 comparators for sorting recipe overview objects
 * that contain the information needed to display a list of
 * search results. White-box test.
 * Kristin Ivarson (kristini@cs)
 */

package recipe_reader.test;

import java.util.*;
import android.test.AndroidTestCase;
import recipe_reader.comparator.RecipeCategoryComparator;
import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Category;
import recipe_reader.comparator.RecipeRatingComparator;
import recipe_reader.comparator.RecipeTitleComparator;

public class RecipeOverviewComparatorTest extends AndroidTestCase {
	
	public void testSortByTitle() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		Category entree = new Category("Entree");
		RecipeOverview o1 = new RecipeOverview("Chicken", entree, true, 50, "Delicious Chicken", 12345);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("Apples", entree, true, 50, "Poisonous Apples", 67890);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Zebra", entree, true, 50, "Fresh Zebra", 24680);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("Robots", entree, true, 50, "Self-aware Robots", 13579);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeTitleComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o2, o1, o4, o3);
		assertEquals("Sorted alphabetically", expected.toString(), actual.toString());
	}

	public void testSortByCategory() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		Category entree = new Category("Entree");
		Category beverage = new Category("Beverage");
		Category side = new Category("Side Dish");
		Category veggie = new Category("Vegetarian");
		RecipeOverview o1 = new RecipeOverview("Carrots", veggie, true, 50, "Steamed carrots", 12345);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("Apples", side, true, 50, "Crisp apples with cinnamon!", 67890);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Zebra", entree, true, 50, "Fresh zebra, killed by a lion.", 24680);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("Root Beer", beverage, true, 50, "Rooty root root beer!", 13579);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeCategoryComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o4, o3, o2, o1);
		assertEquals("Sorted by category", expected.toString(), actual.toString());
	}

	public void testSortByCategoryAndAlphabeticallyWithinCategory() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		Category entree = new Category("Entree");
		Category side = new Category("Side Dish");
		Category veggie = new Category("Vegetarian");
		RecipeOverview o1 = new RecipeOverview("Carrots", veggie, true, 50, "Steamed carrots", 12345);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("Apples", side, true, 50, "Crisp apples with cinnamon!", 67890);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Zebra", entree, true, 50, "Fresh zebra, killed by a lion.", 24680);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("Root Beer", side, true, 50, "Rooty root root beer!", 13579);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeCategoryComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o3, o2, o4, o1);
		assertEquals("Sorted by category, and alphabetically within categories", 
				expected.toString(), actual.toString());
	}
	
	public void testSortByRating() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		Category entree = new Category("Entree");
		Category beverage = new Category("Beverage");
		Category side = new Category("Side Dish");
		Category veggie = new Category("Vegetarian");
		RecipeOverview o1 = new RecipeOverview("Carrots", veggie, true, 48, "Steamed carrots", 12345);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("Apples", side, true, 53, "Crisp apples with cinnamon!", 67890);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Zebra", entree, true, 78, "Fresh zebra, killed by a lion.", 24680);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("Root Beer", beverage, true, 2, "Rooty root root beer!", 13579);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeRatingComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o3, o2, o1, o4);
		assertEquals("Sorted by rating", expected.toString(), actual.toString());
	}

	public void testSortByRatingAndAlphabeticallyWithinRating() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		Category entree = new Category("Entree");
		Category beverage = new Category("Beverage");
		Category side = new Category("Side Dish");
		Category veggie = new Category("Vegetarian");
		RecipeOverview o1 = new RecipeOverview("Carrots", veggie, true, 50, "Steamed carrots", 12345);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("Apples", side, true, 50, "Crisp apples with cinnamon!", 67890);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Zebra", entree, true, 50, "Fresh zebra, killed by a lion.", 24680);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("Root Beer", beverage, true, 2, "Rooty root root beer!", 13579);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeRatingComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o2, o1, o3, o4);
		assertEquals("Sorted by rating, and alphabetically within rating", 
				expected.toString(), actual.toString());
	}
}
