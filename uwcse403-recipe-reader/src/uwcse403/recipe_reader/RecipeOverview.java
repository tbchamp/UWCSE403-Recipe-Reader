/**
 * Recipe Overview object contains data necessary to 
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

public class RecipeOverview {
	private String name;
	private int image;
	private Boolean favorite;
	
	public RecipeOverview(String name, int image, Boolean favorite) {
		this.name = name;
		this.image = image;
		this.favorite = favorite;
	}

	public String getName() {
		return name;
	}
	
	public int getImage() {
		return image;
	}
	
	public Boolean getFavorite() {
		return favorite;
	}
	
}
