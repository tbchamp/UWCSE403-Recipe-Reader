/*
 * Copyright (C) 2008 Google Inc.
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
 * Modified by aosobov
 * 
 * This class measures noise levels through the devices microphone.
 * It should only be used by the VoiceRecognition object
 * 
 * DO NOT USE THIS CLASS DIRCTLY. IT SHOULD ONLY BE INTERACTED WITH
 * BY THE VOICERECOGNITION OBJECT
 * 
 */

package recipe_reader.sound;

import java.io.IOException;

import android.media.MediaRecorder;

/**
 * SoundMeter measures noise levels through the devices microphone.
 * It should only be used by the VoiceRecognition object
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class SoundMeter {
	// Exponential Moving Average Filter
	static final private double EMA_FILTER = 0.6;
	
	// MediaRecorder used for interacting with the mic
	private MediaRecorder mRecorder;
	
	// The Exponential Moving Average
	private double mEMA;
	
	/**
	 * Public constructor
	 */
	public SoundMeter() {
		mRecorder = null;
		mEMA = 0.0;
	}
	
	/**
	 * Begin audio recording/sampling
	 * @throws IOException
	 */
	public void start() {
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setOutputFile("/dev/null");
			
			try {
				mRecorder.prepare();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		mRecorder.start();
		mEMA = 0.0;
	}
	      
	/**
	 * Stop recording and free up resources
	 */
	public void stop() {
		if (mRecorder != null) {
			mRecorder.stop();       
			mRecorder.release();
			mRecorder = null;
		}
	}
	
	/**
	 * 
	 * @return returns ant int representing the largest amplitude observed since the last call
	 * 			to this method. The amplitude is scaled down. 
	 */
	public int getAmplitude() {
		if (mRecorder != null) {
			return (mRecorder.getMaxAmplitude() / 4000);
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	 * @return Returns the current Exponential Moving Average for the amplitude without scaling.
	 */
	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
