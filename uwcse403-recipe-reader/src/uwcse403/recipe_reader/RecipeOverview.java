/**
 * Recipe Overview object contains data necessary to 
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

public class RecipeOverview {
	private String name;
	private String image;
	private Boolean favorite;
	
	public RecipeOverview(String name, String image, Boolean favorite) {
		this.name = name;
		this.image = image;
		this.favorite = favorite;
	}

	public String getName() {
		return name;
	}
	
	public String getImage() {
		return image;
	}
	
	public Boolean getFavorite() {
		return favorite;
	}
	
}
