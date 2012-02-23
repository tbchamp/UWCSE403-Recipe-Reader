package uwcse403.recipe_reader;

import java.util.Observable;
import java.util.Observer;

import uwcse403.recipe_reader.VoiceRecognition.Command;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecipeViewActivity extends FragmentActivity {

	private Recipe recipe;
	
	private VoiceRecognition vr;
	
	// the recipe step that were currently on (is highlighted)
	// initialized to 1 and updated by vr as the user talks to the app
	private int currentStep;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);
        ActionBar bar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        String recipeName = extras.getString("recipeName");
        bar.setTitle(recipeName);
        
        // field initialization
        recipe = new Recipe(recipeName);
        vr = new VoiceRecognition(this);
        currentStep = 1;
        
        // attach a new observer to the VoiceRecognition object
        VoiceRecObserver obs = new VoiceRecObserver(this);
        vr.addObserver(obs);
        
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
    
    /**
     * Updates the current step based on the Command recieved
     * @author aosobov
     * @param c The Command that the VoiceRecognition object got from the user
     */
    private void updateStep(Command c) {
    	if (c == Command.NEXT) {
    		currentStep++;
    	} else if (c == Command.PREVIOUS) {
    		if (currentStep > 1) {
    			currentStep--;
    		}
    	} else if (c == Command.REPEAT) {
    		// repeat
    	} else {
    		// unknown command so do nothing
    	}
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
			
			// Checks if the instruction button was clicked, if so start voice recognition
			// Otherwise, stop voice recognition because user left instructions fragment
			if(v.getId() == R.id.instructions) {
				vr.start();
			} else {
				vr.stop();
			}
		}	
    }
	
    /**
     * @author aosobov
     * Observer used to get info from the VoiceRecognition object when there is some new piece of
     * user voice interaction. Updates the currentStep.
     * For now, displays a dialog showing the command recieved and the current step
     */
    private class VoiceRecObserver implements Observer {
    	private Activity parent;
    	
    	public VoiceRecObserver(Activity a) {
    		parent = a;
    	}
    	
    	public void update(Observable obj, Object arg) {
    		Command result = (Command) arg;
    		
    		// Update app status based on result
    		updateStep(result);
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(parent);
    		builder.setMessage("Command recieved: " + result + " Current step: " + currentStep)
    		       .setCancelable(false)
    		       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	   dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
    	}
    }
    
    /**
     * @author aosobov
     * This catches the result of the VoiceRecognitionActivity and passes it on to the VoiceRecognition
     *  object for processing
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	vr.onActivityResult(requestCode, resultCode, data);
    }

}
