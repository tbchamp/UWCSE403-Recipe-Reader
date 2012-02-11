package uwcse403.recipe_reader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LandingFragment extends Fragment {

	
	@Override
	/** When view is created, inflate layout and add listener to buttons. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.landing, container, false);
		return v;
    }
}
