/**
 * Fragment for displaying list of ingredients for recipe.
 * @author Kristin Ivarson
 */

package recipe_reader.view;

import java.util.List;

import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class IngredientsFragment extends ListFragment {
	
	@Override
	/** @inheritDoc */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        List<String> ingredients = ((RecipeViewActivity) this.getActivity()).getRecipe().getIngredients();
        setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
                R.layout.ingredient_item, ingredients));
    }
}
