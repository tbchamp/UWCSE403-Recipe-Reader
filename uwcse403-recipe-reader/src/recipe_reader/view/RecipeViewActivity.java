/**
 * Activity for viewing a single recipe, and for executing
 * the recipe via voice commands.
 * @author Kristin Ivarson
 */

package recipe_reader.view;


import java.util.Observable;
import java.util.Observer;

import com.michaelnovakjr.numberpicker.NumberPickerDialog;

import recipe_reader.model.Generator;
import recipe_reader.model.Recipe;
import recipe_reader.model.Settings;
import recipe_reader.sound.VoiceRecognition;
import recipe_reader.sound.TextToSpeecher;
import recipe_reader.sound.VoiceRecognition.Command;

import uwcse403.recipe_reader.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class RecipeViewActivity extends FragmentActivity {

	private Recipe recipe;
	
	private Settings settings;
	
	private VoiceRecognition vr;
	
	private TextToSpeecher tts;
	
	private ListView instructListView;
	
	// the recipe step that were currently on (is highlighted)
	// initialized to 0 and updated by vr as the user talks to the app
	// Note: List of instructions is zero-based array
	private int currentStep;
	private int numSteps;
	
	private final int HIGHLIGHTING_COLOR = Color.rgb(87, 174, 74);
	
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
		
		bar.setTitle(recipe.getName());
        vr = new VoiceRecognition(this);
        tts = new TextToSpeecher(this);
        currentStep = 0;
        
        numSteps = recipe.getDirections().getNumSteps();
        
        // make sure the screen will not turn off while this activity is running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | 
        		WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
    		if(currentStep < numSteps - 1) {
    			currentStep++;
    			highlightStep(currentStep, currentStep - 1);
    			tts.speakInstruction(currentStep);
    		} else {
    			tts.speak("There are no more steps in this recipe.");
    		}
    	} else if (c == Command.PREVIOUS) {
    		if (currentStep > 0) {
    			currentStep--;
    			highlightStep(currentStep, currentStep + 1);
    			tts.speakInstruction(currentStep);
    		} else {
    			tts.speak("This is the first step in this recipe.");
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
			if(v.getId() == R.id.start) {
				tts.setInstructionsList(recipe.getDirections().getDirectionList());
				tts.speakInstruction(0);
				vr.start();
				v.setEnabled(false);
			} else {
				vr.stop();
				findViewById(R.id.start).setEnabled(true);
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
    
    /**
     * @author aosobov
     * Inflates the options menu when it is first opened
     */
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
    
    /**
     * @author aosobov
     * Called when an item is selected from the options menu
     * @param item the menu item selected
     */
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.sensitivity) {
			// display the number picker
			NumberPickerDialog dialog = new NumberPickerDialog(this, 4, vr.getSensitivity());
			dialog.setTitle("Select sensitivity threshold");
			dialog.setOnNumberSetListener(new SensitivityUpdater());
			dialog.show();
		}
	    return true;
	}
	
	/**
	 * @author aosobov
	 * Custom handler for a new number selection in the NumberPickerDialog
	 */
	private class SensitivityUpdater implements NumberPickerDialog.OnNumberSetListener {
		public void onNumberSet(int selectedNumber) {
			vr.setSensitivity(selectedNumber);
		}
	}
	
	
	/**
	 * @author yamana
	 * 
	 * Gives the activity access to the ListView of the Instructions fragment so highlighting can occur
	 * 
	 * @param v - This is the ListView of the InstructionsFragment
	 */
	public void setInstructionsListView(ListView v){
		instructListView = v;
	}
	
	
	/**
	 * @author yamana
	 * 
	 * Highlights the next step in the recipe and un-highlights the previously
	 * 	highlighted step
	 * 
	 * @param nextStep - The step that is being switched to
	 * @param previousStep - The last highlighted step
	 */
	private void highlightStep(int nextStep, int previousStep){
		View previous = instructListView.getChildAt(previousStep);
		View next = instructListView.getChildAt(nextStep);
		
		previous.setBackgroundColor(Color.BLACK);
		((TextView) previous).setTextColor(Color.LTGRAY);
		
		next.setBackgroundColor(HIGHLIGHTING_COLOR);
		((TextView) next).setTextColor(Color.BLACK);
	}
	
	
	/**
	 * @author yamana
	 * 
	 * Overrides onDestroy() method so that the TextToSpeech stuff can be shutdown
	 *  and avoids leakage issue.
	 */
	@Override
	protected void onDestroy() {
		tts.shutDown();
	    super.onDestroy();
	}

		
}