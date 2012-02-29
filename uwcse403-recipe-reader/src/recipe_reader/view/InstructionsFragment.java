/**
 * Fragment for displaying list of recipe instructions.
 * @author Kristin Ivarson
 */
package recipe_reader.view;

import recipe_reader.model.Directions;
import recipe_reader.sound.TextToSpeecher;
import uwcse403.recipe_reader.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.TextView;

public class InstructionsFragment extends ListFragment {
	
	private TextToSpeecher tts;
	
	int voo = -1;
	
	@Override
	/** @inheritDoc */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeecher(this.getActivity());
        
        Directions d = ((RecipeViewActivity) this.getActivity()).getRecipe().getDirections();
        setListAdapter(new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
                R.layout.instruction_item, d.getDirectionList()));
    }
	
	
	
	/**
	 * Sends the ListView to the activity to allow highlighting
	 */
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState){
		((RecipeViewActivity) this.getActivity()).setInstructionsListView(this.getListView());
	}
}
