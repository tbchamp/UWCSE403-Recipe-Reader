/**
 * @author aosobov
 * This class is responsible for Speech to Text processing
 * Some of this code is taken directly from an android demo showing an example
 * of using the speach processing intent
 */

package recipe_reader.sound;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/** 
 * Sample code that invokes the speech recognition intent API.
 */
public class VoiceRecognition extends Observable {
    
	// an enum of the possible commands the user can give to the application
	public enum Command {NEXT, PREVIOUS, REPEAT, UNKNOWN}
	
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    
    // a reference to the RecipeViewActivity that this object was instantiated in
    private Activity rReference;
    
    // Noise Monitor variables --------------------------------------------------------------------
	 // how often the SoundMeter object should check to see if a loud noise was made  
     private static final int POLL_INTERVAL = 300;
     // the threshold used to determine how loud a noise needs to be to trigger voice recognition
	 private int THRESHOLD = 6;
	 private int THRESHOLD_MIN = 1;
	 private int THRESHOLD_MAX = 10;
	 
	 // Possible words the user may say to mean one of the Commands
	 private String[] next_arr = {"next", "text", "nice", "post", "max", "thanks", "maxed", "next stop", "next up", "next feb", "forward", "foreign"};
	 private String[] prev_arr = {"previous", "prius", "please", "previous stop", "previous top", "back", "go back", "goback", "go bak"};
	 private String[] repeat_arr = {"repeat", "repeater", "repeat it", "repeats", "again"};
	 
	 // Lists that will contain the possible words for easy searching and manipulation
	 private List<String> next;
	 private List<String> prev;
	 private List<String> repeat;
	 
	 // The handler that will be responsible for checking the max amplitude every POLL_INTERVAL milliseconds
	 private Handler Handler = new Handler();
	 // The SoundMeter object used to monitor the noise level
	 private SoundMeter Sensor;
	 
	 //---------------------------------------------------------------------------------------------

	 /**
	  * Will do nothing and the object will not be instantiated
	  * You have to use the constructor that accepts an Activity as a parameter
	  */
	 public VoiceRecognition() {
		 return;
	 }
	 
	 /**
	  * Create a new instance of the VoiceRecognition object. 
	  * @param current - a reference to the RecipeViewActivity that instantiated an instance of this object
	  * If the activity passed is null, nothing will instantiate and the object will be unusable
	  */
	 public VoiceRecognition(Activity current) {
		 if(current == null) {
			 return;
		 }
		 
		 rReference = current;

		// initialize the SoundMeter
        Sensor = new SoundMeter();

        // populate the word lists
        next = Arrays.asList(next_arr);
        prev = Arrays.asList(prev_arr);
        repeat = Arrays.asList(repeat_arr);
        
        // Check to see if a recognition activity is present
        PackageManager pm = rReference.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
        	// YES
        } else {
    		AlertDialog.Builder builder = new AlertDialog.Builder(rReference);
    		builder.setMessage("Voice Recognition not supported")
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
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which step do you want to hear?");
        rReference.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);            
            Command res = processResults(matches);
            
            // notify the observers and pass them the Command detected
            setChanged();
            notifyObservers(res);
        }
        // restart the sound meter
        start();
    }
    
    /**
     * Process the results from the voice recognition activity and determine which command
     * the user was trying to use
     * @param res - A list of possible words the user may have said
     * @return a Command thought to be the one most likely wanted by the user
     */
    private Command processResults(List<String> res) {
    	
    	int nextSize = intersectionCount(res, next);
    	int prevSize = intersectionCount(res, prev);
    	int repSize = intersectionCount(res, repeat);
    	
    	if(nextSize > prevSize && nextSize > repSize) {
    		return Command.NEXT;
    	} else if (prevSize > nextSize && prevSize > repSize) {
    		return Command.PREVIOUS;
    	} else if (repSize > prevSize && repSize > nextSize) {
    		return Command.REPEAT;
    	} else {
    		return Command.UNKNOWN;
    	}
    }
    
    /**
     * @return returns the size of the intersection of the two sets passed as parameters
     */
    private int intersectionCount(List<String> a, List<String> b) {
    	int count = 0;
    	for(String s : a) {
    		if (b.contains(s)) {
    			count++;
    		}
    	}
    	
    	return count;
    }
     
    /**
     * This is a snippet of code that can be passed to a Handler object to be executed at a user
     * specified time.
     * 
     * This Runnable gets the current amplitude from the SoundMeter and if it is greater than
     * the THRESHOLD, a new speach recognition sequence is started
     */
    private Runnable PollTask = new Runnable() {
		 public void run() {
			 double amp = Sensor.getAmplitude();
			 //speakButton.setText("Amplitude is " + amp + ", h: " + HitCount);
			 if (amp > THRESHOLD) {
					 stop();
					 startVoiceRecognitionActivity();
					 return;
			 }
			 // add another call to PollTask to the handler. This makes a cycle
			 Handler.postDelayed(PollTask, POLL_INTERVAL);
		 }
	 };
	 
	 /**
	  *  Start a new cycle of noise monitoring
	  */
	 public void start() {
		 Sensor.start();
		 Handler.postDelayed(PollTask, POLL_INTERVAL);
	 }
	 
	 /**
	  * Stop the current cycle of noise monitoring
	  */
	 public void stop() {
		 Sensor.stop();
		 Handler.removeCallbacks(PollTask);
		
	 }
	 
	 /**
	  * Adjust the sensitivity of the loud noise detector
	  * @param level - sensitivity level for the loud noise detector. The lower the number, the higher
	  * the sensitivity. Ranges from 1 to 10
	  * @throws IllegalArgumentException if level is not within the expected range
	  */
	 public void setSensitivity(int level) {
		 if (level < THRESHOLD_MIN || level > THRESHOLD_MAX) {
			 throw new IllegalArgumentException("Level must be between " + THRESHOLD_MIN + " and " + THRESHOLD_MAX);
		 }
		 THRESHOLD = level;
	 }
	 
	 /**
	  * Get the sensitivity that the loud noise detector is currently set to.
	  * @return current threshold that the loud noise detector is set to
	  */
	 public int getSensitivity() {
		 return THRESHOLD;
	 }
}