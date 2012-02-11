/**
 * Adapter for populating a list of recipes with images and names
 * into the XML.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IngredientListAdapter extends ArrayAdapter<String> {
	private ArrayList<String> ingredients;
	private Context context;
	
	/** Create adapter with context and recipe data. */
	public IngredientListAdapter(Context context, int textViewResourceId,
				ArrayList<String> ingredients) {
		super(context, textViewResourceId, ingredients);	
		this.context = context;
		this.ingredients = ingredients;
	}
	
	@Override
	/** When view is needed, populate view with appropriate data and return. */
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.ingredient_item, null);
            }
            String ingredient = ingredients.get(position);
            Log.i("MYNOTE", "ingredient is:" + ingredient);
            Log.i("MYNOTE", "view is " + v);
            if (ingredient != null) {
            		String[] values = ingredient.split(" ");
                    TextView amount = (TextView) v.findViewById(R.id.amount);
                    TextView measure = (TextView) v.findViewById(R.id.measure);
                    TextView type = (TextView) v.findViewById(R.id.type);
                    if (amount != null) {
                    	amount.setText(values[0]); 
                    	Log.i("MYNOTE", "set amount");
                    }
                    if (measure != null) {
                        measure.setText(values[1]);   
                        Log.i("MYNOTE", "set measure");
                    }
                    if (type != null) {
                        type.setText(values[2]);
                        Log.i("MYNOTE", "set type");
                    }
            }
            return v;
    }
}
