/* 
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author aosobov
 * This class is responsible for Speach to Text processing
 * 
 */

package uwcse403.recipe_reader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Sample code that invokes the speech recognition intent API.
 */
public class VoiceRecognition extends Observable {
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    
    private Activity rReference;
    
    // Noise Monitor variables --------------------------------------------------------------------
	 private static final int POLL_INTERVAL = 300;
	 private int THRESHOLD = 5;
	 
	 private String[] next_arr = {"next", "text", "nice", "post", "max", "thanks", "maxed", "next stop", "next up", "next feb", "forward", "foreign"};
	 private String[] prev_arr = {"previous", "prius", "please", "previous stop", "previous top", "back", "go back", "goback", "go bak"};
	 private String[] repeat_arr = {"repeat", "repeater", "repeat it", "repeats", "again"};
	 
	 private List<String> next;
	 private List<String> prev;
	 private List<String> repeat;
	 
	 private Handler Handler = new Handler();
	 
	 private SoundMeter Sensor;
	 
	 //---------------------------------------------------------------------------------------------

	 public VoiceRecognition() {
		 return;
	 }
	 
	 public VoiceRecognition(Activity current) {
		 if(current == null) {
			 return;
		 }
		 
		 rReference = current;
		 
		 this.addObserver((Observer) rReference);
		 
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
        	// YES. Start the noise monitoring
        	nmStart();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);            
            String res = processResults(matches);
             
            setChanged();
            notifyObservers(res);
        }
        // restart the sound meter
        nmStart();
        //super.onActivityResult(requestCode, resultCode, data);
    }
    
    /**
     * 
     * @param res
     * @return
     */
    private String processResults(List<String> res) {
    	
    	int nextSize = intersectionCount(res, next);
    	int prevSize = intersectionCount(res, prev);
    	int repSize = intersectionCount(res, repeat);
    	
    	if(nextSize > prevSize && nextSize > repSize) {
    		return "Next";
    	} else if (prevSize > nextSize && prevSize > repSize) {
    		return "Previous";
    	} else if (repSize > prevSize && repSize > nextSize) {
    		return "Repeat";
    	} else {
    		return "Not Sure";
    	}
    }
    
    /**
     *
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
					 nmStop();
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
	 private void nmStart() {
		 //speakButton.setText("Listening");
		 Sensor.start();
		 Handler.postDelayed(PollTask, POLL_INTERVAL);
	 }
	 
	 /**
	  * Stop the current cycle of noise monitoring
	  */
	 private void nmStop() {
		 //speakButton.setText("Not Listening");
		 Handler.removeCallbacks(PollTask);
		 Sensor.stop();
	 }
	 
	 /**
	  * Adjust the sensitivity of the loud noise detector
	  * 
	  * @param level - sensitivity level for the loud noise detector. The lower the number, the higher
	  * the sensitivity. Ranges from 1 to 15
	  * @throws IllegalArgumentException if level is not within the expected range
	  */
	 public void setSensitivity(int level) {
		 if (level < 1 || level > 15) {
			 throw new IllegalArgumentException("Level must be between 1 and 15");
		 }
		 THRESHOLD = level;
	 }
}