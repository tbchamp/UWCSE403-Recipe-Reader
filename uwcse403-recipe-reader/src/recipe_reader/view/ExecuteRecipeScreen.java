/**
 * Fragment for displaying instructions and buttons
 * to navigate instructions while a recipe is being made.
 * @author Kristin Ivarson
 */

package recipe_reader.view;

import recipe_reader.sound.VoiceRecognition.Command;
import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ExecuteRecipeScreen extends Fragment {
	
	private RecipeViewActivity parent;
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.execute_recipe, container, false);
		FragmentTransaction ft = 
			((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		Fragment fragment = Fragment.instantiate(getActivity(), 
				InstructionsFragment.class.getName());
		ft.replace(R.id.instruction_list_frag, fragment, "Instructions");
		ft.commit();
		
		parent = (RecipeViewActivity) getActivity();
		attachNavButtonListeners(v);
		
		
		return v;
	}
	
	/**
	 * @author aosobov
	 * Attaches listeners to the four buttons at the top of this fragment
	 */
	public void attachNavButtonListeners(View v) {

		ImageButton stepBack = (ImageButton) v.findViewById(R.id.step_back);
    	stepBack.setOnClickListener(new NavButtonListener());
    	
    	ImageButton repeatStep = (ImageButton) v.findViewById(R.id.repeat_step);
    	repeatStep.setOnClickListener(new NavButtonListener());
    	
    	ImageButton stepForward = (ImageButton)  v.findViewById(R.id.step_forward);
    	stepForward.setOnClickListener(new NavButtonListener());
	}

	/**
	 * 
	 * @author aosobov
	 *	The listener that reacts to click on the four buttons at the top of this fragment
	 */
    private class NavButtonListener implements OnClickListener {
		public NavButtonListener() {
    	}
    	
		public void onClick(View v) {
			if(v.getId() == R.id.step_back) {
				parent.updateStep(Command.PREVIOUS);
			} else if(v.getId() == R.id.repeat_step) {
				parent.updateStep(Command.REPEAT);
			} else if(v.getId() == R.id.step_forward) {
				parent.updateStep(Command.NEXT);
			}
			
		}	
    }
	
}
