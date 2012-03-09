package recipe_reader.view;

import recipe_reader.model.Settings;
import uwcse403.recipe_reader.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * SettingsTab is the fragment showing the user log in information
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class SettingsTab extends Fragment {
	
	private Settings settings;
	
	@Override
	/** @inheritDoc */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		settings = ((RecipeReaderActivity)this.getActivity()).getSettings();
		View v;
		if (settings.getUser().getUsername().equals("guest")) {
			v = inflater.inflate(R.layout.log_in, container, false);
			Button logInButton = (Button) v.findViewById(R.id.logInButton);
			logInButton.setOnClickListener(new ClickListener());
		} else {
			v = inflater.inflate(R.layout.logged_in, container, false);
			TextView tv = (TextView) v.findViewById(R.id.userName);
			if (tv != null) {
				tv.setText("Logged in as:\n\t" + settings.getUser().getUsername());
			}
			Button logOutButton = (Button) v.findViewById(R.id.logOutButton);
			logOutButton.setOnClickListener(new ClickListener());
		}
		return v;
    }
	
	private class ClickListener implements View.OnClickListener {
		private Fragment resultsFragment;

		/** @inheritDoc */
		public void onClick(View v) {
			FragmentTransaction ft = 
				((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			if (resultsFragment == null) {
				resultsFragment = Fragment.instantiate(getActivity(), SettingsTab.class.getName());
			}
			if (settings.getUser().getUsername().equals("guest")) {
				View u = ((View) v.getParent()).findViewById(R.id.userEntry);
				String username = ((TextView) u).getText().toString();
				View p = ((View) v.getParent()).findViewById(R.id.passwordEntry);
				String password = ((TextView) p).getText().toString();
				try {
					settings.signIn(username, password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				settings.signOut();
			}
			ft.replace(android.R.id.content, resultsFragment, "Settings");
			ft.commit();
		}
	}

}

