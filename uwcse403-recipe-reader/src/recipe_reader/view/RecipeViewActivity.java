/**
 * Activity for viewing a single recipe, and for executing
 * the recipe via voice commands.
 * @author Kristin Ivarson
 */

package recipe_reader.view;


import java.util.Observable;
import java.util.Observer;


import recipe_reader.model.Generator;
import recipe_reader.model.Recipe;
import recipe_reader.model.Settings;
import recipe_reader.sound.VoiceRecognition;
import recipe_reader.sound.TextToSpeecher;
import recipe_reader.sound.VoiceRecognition.Command;

import uwcse403.recipe_reader.R;

import android.app.Activity;
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
	
	private Settings settings;
	
	private VoiceRecognition vr;
	
	private TextToSpeecher tts;
	
	// the recipe step that were currently on (is highlighted)
	// initialized to 0 and updated by vr as the user talks to the app
	// Note: List of instructions is zero-based array
	private int currentStep;
	private int lastStep;
	
    /** @inheritDoc */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);
        ActionBar bar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("recipeID");
        
        settings = new Settings();
        try {
			settings.signIn("Jeremy Lin", "Linsanity");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // field initialization
        		
        Generator g = new Generator();
        
		try {
			recipe = g.getRecipe(id, settings.getUser());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		//THIS IS HERE FOR TESTING PURPOSES BECAUSE THE RECIPE LOADING IS NOT WORKING
		//if (recipe == null) {
			/*List<String> dirLst = new ArrayList<String>();
			dirLst.add("Step one");
			dirLst.add("Step two");
			dirLst.add("Step three");
			dirLst.add("Step four");
			dirLst.add("Step five");
			
			Directions dir = new Directions(dirLst);
			
			List<Ingredient> ing = new ArrayList<Ingredient>();
			ing.add(new Ingredient("2 pounds of chicken"));
			
			recipe.setDirections(dir);
			recipe.setIngredients(ing);*/
			
		//}
		
		bar.setTitle(recipe.getName());
        vr = new VoiceRecognition(this);
        tts = new TextToSpeecher(this);
        currentStep = 0;
        
        lastStep = recipe.getDirections().getNumSteps() - 1;

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
    public void updateStep(Command c) {
    	if (c == Command.NEXT) {
    		if(currentStep < lastStep) {
    			currentStep++;
    			tts.speakInstruction(currentStep);
    		} else {
    			tts.speak("There are no more steps in this recipe");
    		}
    	} else if (c == Command.PREVIOUS) {
    		if (currentStep > 0) {
    			currentStep--;
    			tts.speakInstruction(currentStep);
    		} else {
    			tts.speak("This is the first step in this recipe");
    		}
    	} else if (c == Command.REPEAT) {
    		// repeat
    		tts.speakInstruction(currentStep);
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
				tts.speakInstruction(0);
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
    		
    		/*
    		 * DEBUGGING ALERT. WILL BE REMOVED FOR FINAL RELEASE
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
    		*/
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
