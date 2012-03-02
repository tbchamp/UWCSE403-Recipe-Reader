/**
 * Fragment for displaying an image and name for a 
 * recipe when it's first viewed.
 * @author Kristin Ivarson
 */
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

public class LandingFragment extends Fragment {
	
	@Override
	/** When view is created, inflate layout and add listener to buttons. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.landing, container, false);
		TextView tv = (TextView) v.findViewById(R.id.recipe_title);
		//String test = savedInstanceState.getString("name");
		RecipeViewActivity rva = (RecipeViewActivity) this.getActivity();
        Recipe r = rva.getRecipe();
		String test = r.getName();
		tv.setText(test);
		try {
			ImageView iv = ((ImageView) v.findViewById(R.id.recipe_image));
			InputStream is = (InputStream) new URL(r.getImgLoc()).getContent();
			Drawable d = Drawable.createFromStream(is, "src");
			iv.setImageDrawable(d);
			
		} catch (IOException e) {}
		((TextView) v.findViewById(R.id.yield)).setText(r.getYield());
		((TextView) v.findViewById(R.id.cook_time)).setText(r.getCook());
		((TextView) v.findViewById(R.id.total_time)).setText(r.getReadytime());
		((TextView) v.findViewById(R.id.calories)).setText(""+r.getCalories());
		return v;
    }
}
