/**
 * Tests for 3 comparators for sorting recipe overview objects
 * that contain the information needed to display a list of
 * search results. White-box test.
 * Kristin Ivarson (kristini@cs)
 */

package uwcse403.recipe_reader;


import java.util.*;

import org.junit.Test;

import uwcse403.recipe_reader.RecipeCategoryComparator;
import uwcse403.recipe_reader.RecipeOverview;
import uwcse403.recipe_reader.RecipeRatingComparator;
import uwcse403.recipe_reader.RecipeTitleComparator;
import junit.framework.TestCase;

public class RecipeOverviewComparatorTest extends TestCase {
	
	/*@Test(timeout = 1000)
	public void testSortByTitle() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		RecipeOverview o1 = new RecipeOverview("C", RecipeOverview.Category.SOUPS, true);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("A", RecipeOverview.Category.SOUPS, true);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Z", RecipeOverview.Category.SOUPS, true);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("R", RecipeOverview.Category.SOUPS, true);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeTitleComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o2, o1, o4, o3);
		assertEquals("Sorted alphabetically", expected.toString(), actual.toString());
	}
	
	@Test(timeout = 1000)
	public void testSortByCategory() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		RecipeOverview o1 = new RecipeOverview("C", RecipeOverview.Category.APPETIZER, true);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("A", RecipeOverview.Category.SALADS, true);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Z", RecipeOverview.Category.DESSERTS, true);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("R", RecipeOverview.Category.SOUPS, true);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeCategoryComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o1, o2, o4, o3);
		assertEquals("Sorted by category", expected.toString(), actual.toString());
	}
	
	@Test(timeout = 1000)
	public void testSortByCategoryAndAlphabeticallyWithinCategory() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		RecipeOverview o1 = new RecipeOverview("C", RecipeOverview.Category.SOUPS, true);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("A", RecipeOverview.Category.DESSERTS, true);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Z", RecipeOverview.Category.DESSERTS, true);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("R", RecipeOverview.Category.SOUPS, true);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeCategoryComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o1, o4, o2, o3);
		assertEquals("Sorted by category, and alphabetically within categories", 
				expected.toString(), actual.toString());
	}
	
	@Test(timeout = 1000)
	public void testSortByRating() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		RecipeOverview o1 = new RecipeOverview("C", RecipeOverview.Category.SOUPS, true, 5.0);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("A", RecipeOverview.Category.SOUPS, true, 4.0);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Z", RecipeOverview.Category.SOUPS, true, 2.0);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("R", RecipeOverview.Category.SOUPS, true, 2.5);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeRatingComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o1, o2, o4, o3);
		assertEquals("Sorted by rating", expected.toString(), actual.toString());
	}
	
	@Test(timeout = 1000)
	public void testSortByRatingAndAlphabeticallyWithinRating() {
		List<RecipeOverview> actual = new ArrayList<RecipeOverview>();
		RecipeOverview o1 = new RecipeOverview("C", RecipeOverview.Category.SOUPS, true, 5.0);
		actual.add(o1);
		RecipeOverview o2 = new RecipeOverview("A", RecipeOverview.Category.SOUPS, true, 5.0);
		actual.add(o2);
		RecipeOverview o3 = new RecipeOverview("Z", RecipeOverview.Category.SOUPS, true, 4.0);
		actual.add(o3);
		RecipeOverview o4 = new RecipeOverview("R", RecipeOverview.Category.SOUPS, true, 4.0);
		actual.add(o4);
		
		Comparator<RecipeOverview> comp = new RecipeRatingComparator();
		Collections.sort(actual, comp);
		List<RecipeOverview> expected = Arrays.asList(o2, o1, o4, o3);
		assertEquals("Sorted by rating, and alphabetically within rating", 
				expected.toString(), actual.toString());
	}*/
}
