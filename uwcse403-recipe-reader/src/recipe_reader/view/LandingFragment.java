package recipe_reader.view;

import uwcse403.recipe_reader.R;
import uwcse403.recipe_reader.R.id;
import uwcse403.recipe_reader.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LandingFragment extends Fragment {
	
	@Override
	/** When view is created, inflate layout and add listener to buttons. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.landing, container, false);
		TextView tv = (TextView) v.findViewById(R.id.recipe_title);
		//String test = savedInstanceState.getString("name");
		String test = "The Hamburger";
		tv.setText(test);
		return v;
    }
}
