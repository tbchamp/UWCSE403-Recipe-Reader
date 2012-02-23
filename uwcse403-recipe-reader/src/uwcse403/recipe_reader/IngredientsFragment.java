package uwcse403.recipe_reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class IngredientsFragment extends ListFragment {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
       /* List<String> list = Arrays.asList(	"1 cup something or other", 
        									"4 ounces something else",
        									"¼ tsp this or that");*/
        RecipeViewActivity rva = (RecipeViewActivity) this.getActivity();
        Recipe r = rva.getRecipe();
        ArrayList<String> ingredients = new ArrayList<String>(r.getNotes());
        setListAdapter(new IngredientListAdapter(this.getActivity().getApplicationContext(),
                R.layout.ingredient_item, ingredients));
    }
}
