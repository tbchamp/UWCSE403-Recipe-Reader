/**
 * Adapter for populating a list of recipes with images and names
 * into the XML.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<RecipeOverview> {
	private ArrayList<RecipeOverview> recipes;
	private Context context;
	
	/** Create adapter with context and recipe data. */
	public SearchResultAdapter(Context context, int textViewResourceId,
				ArrayList<RecipeOverview> recipes) {
		super(context, textViewResourceId, recipes);	
		this.context = context;
		this.recipes = recipes;
	}
	
	@Override
	/** When view is needed, populate view with appropriate data and return. */
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.list_item, null);
            }
            RecipeOverview recipe = recipes.get(position);
            if (recipe != null) {
                    TextView title = (TextView) v.findViewById(R.id.name);
                    ImageView image = (ImageView) v.findViewById(R.id.imgIcon);
                    ImageView favoriteStar = (ImageView) v.findViewById(R.id.favoriteStar);
                    if (title != null) {
                          title.setText(recipe.getName());    
                    }
//                    if (image != null) {
//                        image.setImageResource(recipe.getImage());    
//                    }
                    if (favoriteStar != null && recipe.getFavorite()) {
                        favoriteStar.setImageResource(R.drawable.star);    
                    }
            }
            return v;
    }
}
