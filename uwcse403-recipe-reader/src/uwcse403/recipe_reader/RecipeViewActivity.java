package uwcse403.recipe_reader;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecipeViewActivity extends FragmentActivity {

	private Recipe recipe;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Name of Recipe Here");
        Bundle extras = getIntent().getExtras();
        if (extras!= null) {
        	String test = extras.getString("name");
        	if (test != null) {
        		Button b = (Button) findViewById(R.id.recipe_image);
        		b.setText(test);
        	}
        }
        attachButtonListeners();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment resultsFragment = Fragment.instantiate(this, 
				LandingFragment.class.getName(), getIntent().getExtras());
		ft.replace(R.id.frag, resultsFragment);
		ft.commit();
    }
    
    private void attachButtonListeners() {
    	Button start = (Button) findViewById(R.id.recipe_image);
    	start.setOnClickListener(new ButtonListener(this, LandingFragment.class));
    	Button ingredients = (Button) findViewById(R.id.ingredients);
    	ingredients.setOnClickListener(new ButtonListener(this, IngredientsFragment.class));
    	Button instructions = (Button) findViewById(R.id.instructions);
    	instructions.setOnClickListener(new ButtonListener(this, InstructionsFragment.class));
    }
    
    private class ButtonListener implements OnClickListener {
    	private FragmentActivity activity;
		private Fragment fragment;
		private Class classFrag;

		private ButtonListener(FragmentActivity activity, Class classFrag) {
    		this.activity = activity;
    		this.classFrag = classFrag;
    	}
    	
		public void onClick(View v) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			if (fragment == null) {
				fragment = Fragment.instantiate(activity, classFrag.getName());
			}
			ft.replace(R.id.frag, fragment);
			ft.commit();
		}
    }
}
