/**
* Fragment for displaying list of recipe instructions.
* @author Kristin Ivarson
*/
package recipe_reader.view;

import recipe_reader.model.Directions;
import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;

public class InstructionsFragment extends ListFragment {


	@Override
	/** @inheritDoc */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
		Directions d = ((RecipeViewActivity) this.getActivity()).getRecipe().getDirections();
		setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
				R.layout.instruction_item, d.getDirectionList()));
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