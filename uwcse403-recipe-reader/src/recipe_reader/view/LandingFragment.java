package recipe_reader.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import recipe_reader.model.Recipe;
import uwcse403.recipe_reader.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * LandingFragment is the home screen for a recipe view
 * It displays an image and other information for a recipe
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class LandingFragment extends Fragment {
	
	@Override
	/** When view is created, inflate layout and add listener to buttons. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.landing, container, false);
		TextView tv = (TextView) v.findViewById(R.id.recipe_title);
		RecipeViewActivity rva = (RecipeViewActivity) this.getActivity();
        Recipe r = rva.getRecipe();
		String test = r.getName();
		tv.setText(test);
		try {
			ImageView iv = ((ImageView) v.findViewById(R.id.recipe_image));
			InputStream is = (InputStream) new URL(r.getImgLoc()).getContent();
			Drawable d = Drawable.createFromStream(is, "src");
			iv.setBackgroundDrawable(d);
		} catch (IOException e) {}
		((TextView) v.findViewById(R.id.yield)).setText(r.getYield());
		if (r.getCook().equals("[]")) {
			((TextView) v.findViewById(R.id.cook_time)).setText("N/A");
		} else {
			((TextView) v.findViewById(R.id.cook_time)).setText(r.getCook());
		}
		if (r.getReadytime().equals("[]")) {
			((TextView) v.findViewById(R.id.total_time)).setText("N/A");
		} else {
			((TextView) v.findViewById(R.id.total_time)).setText(r.getReadytime());
		}
		((TextView) v.findViewById(R.id.calories)).setText(""+r.getCalories());
		return v;
    }
}
