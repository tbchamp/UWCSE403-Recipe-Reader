/**
 * Fragment for displaying list of ingredients for recipe.
 * @author Kristin Ivarson
 */

package recipe_reader.view;

import java.util.ArrayList;

import recipe_reader.model.Ingredient;
import recipe_reader.model.Recipe;
import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class IngredientsFragment extends ListFragment {
	
	@Override
	/** @inheritDoc */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecipeViewActivity rva = (RecipeViewActivity) this.getActivity();
        Recipe r = rva.getRecipe();
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) r.getIngredients();
        setListAdapter(new IngredientListAdapter(this.getActivity().getApplicationContext(),
                R.layout.ingredient_item, ingredients));
    }
}
