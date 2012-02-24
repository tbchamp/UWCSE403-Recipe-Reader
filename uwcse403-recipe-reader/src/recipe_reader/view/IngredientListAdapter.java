/**
 * Adapter for populating a list of ingredients with amounts and names
 * into the XML.
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import java.util.ArrayList;

import uwcse403.recipe_reader.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            if (ingredient != null) {
            		String[] values = ingredient.split(" ");
                    TextView amount = (TextView) v.findViewById(R.id.amount);
                    TextView measure = (TextView) v.findViewById(R.id.measure);
                    TextView type = (TextView) v.findViewById(R.id.type);
                    if (amount != null) {
                    	amount.setText(values[0]); 
                    }
                    if (measure != null) {
                        measure.setText(values[1]);   
                    }
                    if (type != null) {
                        type.setText(values[2]);
                    }
            }
            return v;
    }
}
