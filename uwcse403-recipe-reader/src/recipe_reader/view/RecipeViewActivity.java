/**
 * Activity for viewing a single recipe, and for executing
 * the recipe via voice commands.
 * @author Kristin Ivarson
 */

package recipe_reader.view;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import recipe_reader.model.Category;
import recipe_reader.model.Generator;
import recipe_reader.model.Recipe;
import recipe_reader.model.RecipeOverview;
import recipe_reader.sound.VoiceRecognition;
import recipe_reader.sound.TextToSpeecher;
import recipe_reader.sound.VoiceRecognition.Command;

import uwcse403.recipe_reader.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class RecipeViewActivity extends FragmentActivity {

	private static Recipe recipe;
	
	private VoiceRecognition vr;
	
	private TextToSpeecher tts;
	
	// the recipe step that were currently on (is highlighted)
	// initialized to 0 and updated by vr as the user talks to the app
	// Note: List of instructions is zero-based array
	private int currentStep;
	
    /** @inheritDoc */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);
        ActionBar bar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        String recipeName = extras.getString("recipeName");
        
        // field initialization
        RecipeOverview ro = new RecipeOverview(recipeName, new Category(6, "Breakfast"), true, "good", 1);
        Generator g = new Generator();
		try {
			recipe = g.getRecipe(ro);
		} catch (Exception e) {
			recipe = new Recipe("failed");
		}
		bar.setTitle(recipe.getName());
		List<String> notes = Arrays.asList("Testing this feature", "Hope it works");
		recipe.setNotes(notes);
        vr = new VoiceRecognition(this);
        tts = new TextToSpeecher(this);
        currentStep = 0;

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
    
    //Attach listeners to buttons for displaying 3 screens.
    private void attachButtonListeners() {
    	Button start = (Button) findViewById(R.id.start);
    	start.setOnClickListener(new ButtonListener(this, ExecuteRecipeScreen.class));
    	
    	Button ingredients = (Button) findViewById(R.id.ingredients);
    	ingredients.setOnClickListener(new ButtonListener(this, IngredientsFragment.class));
    	
    	Button instructions = (Button) findViewById(R.id.instructions);
    	instructions.setOnClickListener(new ButtonListener(this, InstructionsFragment.class));
    }
    
    /**
     * @return Recipe this activity is for
     */
    public Recipe getRecipe() {
    	return recipe;
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
    		if (currentStep > 0) {
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
    	
		/** @inheritDoc */
		public void onClick(View v) {
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			if (fragment == null) {
				fragment = Fragment.instantiate(activity, classFrag.getName());
			}
			ft.replace(R.id.frag, fragment);
			
			ft.commit();
			
			// Checks if the instruction button was clicked. If so, start voice recognition
			// Otherwise, stop voice recognition because user left instructions fragment
			//
			// Also, store instance of InstructionsFragment. For use in reading TextToSpeech stuff.
			if(v.getId() == R.id.start) {
				vr.start();
				tts.setInstructionsList(recipe.getDirections().getDirectionList());
				tts.speakInstruction(0); // reads first step
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
