/**
 * Adapter for populating a list of recipes with images and names
 * into the XML.
 * Kristin Ivarson (kristini@cs)
 */
package recipe_reader.view;

import java.util.ArrayList;

import recipe_reader.model.RecipeOverview;
import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.drawable;
import uwcse403.recipe_reader.R.id;
import uwcse403.recipe_reader.R.layout;
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
            		ImageView image = (ImageView) v.findViewById(R.id.categoryIcon);
            		ImageView favoriteStar = (ImageView) v.findViewById(R.id.favoriteStar);
                    TextView title = (TextView) v.findViewById(R.id.name);
                    TextView rating = (TextView) v.findViewById(R.id.rating);
                    if (title != null) {
                          title.setText(recipe.getName());    
                    }
                    if (image != null) {
                        image.setImageResource(recipe.getCategory().getIcon());    
                    }
                    if (favoriteStar != null && recipe.getFavorite()) {
                        favoriteStar.setImageResource(R.drawable.star);    
                    }
                    if (rating != null) {
                        rating.setText("" + recipe.getRating());    
                    }
            }
            return v;
    }
}
