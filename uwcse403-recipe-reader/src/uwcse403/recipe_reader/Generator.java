package uwcse403.recipe_reader;

public class Generator {

	public Generator() {
		
	}
	
	public Recipe getRecipe(RecipeOverview overview) {
		return new Recipe(overview);
	}
}
