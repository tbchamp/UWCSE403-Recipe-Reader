package recipe_reader.view;

import recipe_reader.model.Directions;
import uwcse403.recipe_reader.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * InstructionsFragment is a fragment that displays the list of recipe instructions
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class InstructionsFragment extends ListFragment {

	private static final int HIGHLIGHTING_COLOR = Color.rgb(87, 174, 74);

	@Override
	/** @inheritDoc */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
		Directions d = ((RecipeViewActivity) this.getActivity()).getRecipe().getDirections();
		
		/**
		 * @author yamana
		 * 
		 * Defines a new adapter to work specifically with this fragment.
		 * Has been modified to highlight first instruction.
		 */
		ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.instruction_item, d.getDirectionList()) {
			@Override
	        public View getView(int position, View v, ViewGroup parent) {
	            if (v == null) {
	                   LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                   v = inflater.inflate(R.layout.instruction_item, null);
	               }
	            View view = super.getView(position, v, parent);
	            if(position == 0){
		            view.setBackgroundColor(HIGHLIGHTING_COLOR);
		            ((TextView) view).setTextColor(Color.BLACK);
	            } else {
	            	((TextView) view).setTextColor(Color.LTGRAY);
	            }
	            return view;
			}
		};
		
		// Sets adapter to custom adapter
		setListAdapter(customAdapter);
		
	}
	
	
	/**
	 * @author yamana
	 * 
	 * Sends the ListView to the activity to allow highlighting
	 */
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		((RecipeViewActivity) this.getActivity()).setInstructionsListView(this.getListView());
	}

}