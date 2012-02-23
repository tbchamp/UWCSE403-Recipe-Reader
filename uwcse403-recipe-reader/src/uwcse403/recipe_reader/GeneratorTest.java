/**
 * Tests generator to make sure the recipes are being generated correctly given
 * a recipe overview
 * White Box Test
 * Jonathan Ramaswamy (ramasj@cs.washington.edu)
 */
package uwcse403.recipe_reader;

import junit.framework.TestCase;

public class GeneratorTest extends TestCase {

	/*public void testHamburger() {
		RecipeOverview ro = new RecipeOverview("Hamburger", Category.MAIN_COURSE, true);
		Generator g = new Generator();
		Recipe r = g.getRecipe(ro);
		assertEquals("Put the beef on the bun", r.getCurrentStep());
		assertEquals("Bun", r.getIngredients().get(1));
	}
	
	public void testSmoothie() {
		RecipeOverview ro = new RecipeOverview("Smoothie", Category.DESSERTS, true);
		Generator g = new Generator();
		Recipe r = g.getRecipe(ro);
		assertEquals("Mix the banana and the strawberry", r.getCurrentStep());
		assertEquals("Sugar", r.getIngredients().get(1));
	}
	
	public void testNonExistantRecipe() {
		RecipeOverview ro = new RecipeOverview("Fake", Category.DESSERTS, true);
		Generator g = new Generator();
		Recipe r = g.getRecipe(ro);
		assertEquals(null, r);
	}*/
}