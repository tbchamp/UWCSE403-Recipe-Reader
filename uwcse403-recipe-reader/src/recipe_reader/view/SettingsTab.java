/**
 * Fragment for settings/preferences tab.
 * @author Kristin Ivarson
 */
package recipe_reader.view;


import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SettingsTab extends Fragment {
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.preferences, container, false);
		TextView tv = (TextView) v.findViewById(R.id.userName);
		if (tv != null) {
			tv.setText("Logged in as:\n\t" + 
					((RecipeReaderActivity)this.getActivity())
						.getSettings().getUser().getUsername());
		}
		return v;
    }

}

