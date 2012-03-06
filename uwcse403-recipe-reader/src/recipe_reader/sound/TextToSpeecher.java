/**
 * @author Alisa Yamanaka
 * @version February 23, 2012 - 22:26
 * 
 * This class uses the TextToSpeech class provided by android to convert text
 * 	to speech. Implements the OnInitListener so other classes do not have to.
 */

package recipe_reader.sound;

import java.util.List;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

public class TextToSpeecher implements OnInitListener {
	
	private Activity act;
	private TextToSpeech tts;
	private List<String> instructList;
	private int instructCount;
	
	/**
	 * Creates a new TextToSpeecher object
	 * 
	 * @param a - The Activity calling the TextToSpeecher
	 * @throws NullPointerException if activity passed in is null
	 */
	public TextToSpeecher(Activity a){
		if (a != null){
			act = a;
			tts = new TextToSpeech(act, this);
			instructList = null;
			instructCount = 0;
		} else {
			throw new NullPointerException();
		}
	}
	
	
	/**
	 * Accepts a string and speaks it in the application
	 * 
	 * @param text - the string (not null and length > 0) to be spoken in the application
	 */
	public void speak(String text){
		if(text != null && text.length() > 0) {
			tts.speak(text, TextToSpeech.QUEUE_ADD, null);
		}
	}
	
	
	/**
	 * Accepts an instruction to read from the list of instructions
	 * 
	 * @param instr - the numbered instruction to read
	 */
	public void speakInstruction(int instr){
		if(instructList != null && instr >= 0 && instr < instructCount){
			String listItem = (String) instructList.get(instr);
			
			if (listItem.length() > 2){
				// Remove first part of string with the step number.
				// Comment out if you want it to read the step number.
				listItem = listItem.substring(2);
				
				this.speak(listItem);
			}
		}
	}
	
	
	/**
	 * Accepts and stores the list of instructions so that they may be read
	 *  later
	 * 
	 * @param il - The list of instructions
	 */
	public void setInstructionsList(List<String> il){
		if(il != null){
			instructList = il;
			instructCount = il.size();
		}
	}
	

	/**
	 * Checks that the Text-To-Speech engine is successfully initialized. Outputs
	 * 	a toast message with whether or not it has been successfully initialized.
	 * 
	 *  Don't worry about this method, you don't need to call it. You can un-comment
	 * 	the inner code to see if the engine is initialized, but you don't have to.
	 *  This just complains if there isn't an onInit() method.
	 *  
	 *  @param status - status code
	 */
	public void onInit(int status) {		
		/*if (status == TextToSpeech.SUCCESS) {
			Toast.makeText(act, "Text-To-Speech engine is initialized", Toast.LENGTH_LONG).show();
		}
		else if (status == TextToSpeech.ERROR) {
			Toast.makeText(act, "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
		}*/
	}

}
