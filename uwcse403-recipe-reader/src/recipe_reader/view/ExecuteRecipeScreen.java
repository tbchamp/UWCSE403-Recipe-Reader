/**
 * Fragment for displaying instructions and buttons
 * to navigate instructions while a recipe is being made.
 * @author Kristin Ivarson
 */

package recipe_reader.view;

import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExecuteRecipeScreen extends Fragment {
	
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
		return v;
	}
}
