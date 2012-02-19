/**
 * Recipe Overview object contains data necessary to 
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

public class RecipeOverview {
	private String name;
	private Category category;
	private Boolean favorite;
	private Double rating;
	
	public RecipeOverview(String name, Category category, Boolean favorite,
			Double rating) {
		this.name = name;
		this.category = category;
		this.favorite = favorite;
		this.rating = rating;
	}
	
	public RecipeOverview(String name, Category category, Boolean favorite) {
		this(name, category, favorite, 5.0);
	}

	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Boolean getFavorite() {
		return favorite;
	}
	
	public Double getRating() {
		return rating;
	}
	
	public enum Category {
		APPETIZER(R.drawable.icon),
		SALADS(R.drawable.icon),
		SOUPS(R.drawable.icon),
		MAIN_COURSE(R.drawable.icon),
		DESSERTS(R.drawable.icon);
		
		private final int icon;
		Category(int icon) {
			this.icon = icon;
		}
		
		public int getIcon() {
			return this.icon;
		}
	}
	
}
