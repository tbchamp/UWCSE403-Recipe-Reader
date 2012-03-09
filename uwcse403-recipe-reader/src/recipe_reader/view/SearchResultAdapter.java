/**
 * Adapter for populating a list of recipes with images and names
 * into the XML.
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import java.util.List;

import recipe_reader.model.RecipeOverview;
import recipe_reader.model.Searcher;
import recipe_reader.model.User;
import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.drawable;
import uwcse403.recipe_reader.R.id;
import uwcse403.recipe_reader.R.layout;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<RecipeOverview> {
	private List<RecipeOverview> recipes;
	private User user;
	private Context context;
	
	/** @inheritDoc
	 *  Create adapter with context and recipe data. */
	public SearchResultAdapter(Activity activity, int textViewResourceId,
				List<RecipeOverview> recipes) {
		super(activity.getApplicationContext(), textViewResourceId, recipes);	
		this.user = ((RecipeReaderActivity) activity).getSettings().getUser();
		this.context = activity.getApplicationContext();
		this.recipes = recipes;
	}
	
	@Override
	/** @inheritDoc 
	 *  When view is needed, populate view with appropriate data and return. */
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.list_item, null);
            }
            RecipeOverview recipe = recipes.get(position);
            if (recipe != null) {
            		ImageView image = (ImageView) v.findViewById(R.id.categoryIcon);
            		ImageButton favoriteStar = (ImageButton) v.findViewById(R.id.favoriteStar);
                    TextView title = (TextView) v.findViewById(R.id.name);
                    TextView rating = (TextView) v.findViewById(R.id.rating);
                    if (title != null) {
                          title.setText(recipe.getName());    
                    }
                    if (image != null) {
                        image.setImageResource(recipe.getCategory().getIcon());    
                    }
                    if (favoriteStar != null) {
                    	if (recipe.getFavorite()) {
                    		favoriteStar.setImageResource(R.drawable.star);  
                    	} else {
                    		favoriteStar.setImageResource(R.drawable.star_outline);
                    	}
                    	favoriteStar.setOnClickListener(
                    			new FavoriteStarListener(user, recipe));
                    }
                    if (rating != null) {
                        rating.setText("" + recipe.getRating());    
                    }
            }
            return v;
    }
	
	private class FavoriteStarListener implements View.OnClickListener {
		private User user;
		private RecipeOverview recipe; 
		
		private FavoriteStarListener(User user, RecipeOverview recipe) {
			this.user = user;
			this.recipe = recipe;
		}
		
		public void onClick(View v) {
			if(!user.getUsername().equals("guest")) {
				ImageButton b = ((ImageButton) v);
				if (recipe.getFavorite()) {
					try {
						Searcher.removeRecipeFromFavoriteById(user, recipe.getId());
					} catch (Exception e) {}
					b.setImageResource(R.drawable.star_outline);
				} else {
					try {
						Searcher.addRecipeToFavoriteById(user, recipe.getId());
					} catch (Exception e) {}
					b.setImageResource(R.drawable.star);
				}
			}
		}
		
	}
}
