/**
 * Recipe Overview object contains data necessary to 
 * populate an item in a list of recipes.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

public class RecipeOverview {
	private String name;
	private Category category;
	private boolean favorite;
	private double rating;
	
	/**
	 * 
	 * @param name Title of recipe
	 * @param category Category of recipe, Enum of type RecipeOverview.Category
	 * @param favorite Whether this recipe is one of user's favorites
	 * @param rating Average recipe rating for all users, 5.0 scale.
	 */
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

	/**
	 * @return Title of recipe
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Category of recipe
	 */
	public Category getCategory() {
		return category;
	}
	
	/**
	 * @return True if recipe has been favorited by user
	 */
	public boolean getFavorite() {
		return favorite;
	}
	
	/**
	 * @return Rating of recipe
	 */
	public double getRating() {
		return rating;
	}
	
	/**
	 * Enum representing recipe category, linked
	 * to an icon to show in search results list.
	 */
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
		
		/**
		 * @return Android R.id value for Category's icon
		 */
		public int getIcon() {
			return this.icon;
		}
	}
	
}
