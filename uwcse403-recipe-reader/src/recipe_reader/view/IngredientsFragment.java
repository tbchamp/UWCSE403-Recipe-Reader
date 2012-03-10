package recipe_reader.view;

import java.util.List;


import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

/**
 * IngredientsFragment is the fragment for displaying a list of ingredients
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
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
