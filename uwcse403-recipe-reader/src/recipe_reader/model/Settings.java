package recipe_reader.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Settings holds the information of the current user and allows users to sign in and out
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class Settings {
	private User user;

	/**
	 * class constructor
	 * attempts to log user into guest account, on failure sets user to null
	 */
	public Settings(){	
		if (!signIn("guest", "guestPwd")){
			user = null;
		}
	}

	/**
	 * creates a new user and sets field to new user
	 * @param username
	 * @param password
	 * @return true on success, false on error
	 */
	public boolean createUser(String username, String password) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username",username));
		nameValuePairs.add(new BasicNameValuePair("password",password));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/createuser.php");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			//convert response to string
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();

			if (result.equals("Create User Failed!\n")){
				return false;
			}
		} catch (IOException e){
			return false;
		}

		String UniqueId = "";
		int id = 0;
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				id = json_data.getInt("id");
				UniqueId = json_data.getString("unique_id");
			}
		} catch (JSONException e){
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}

	/**
	 * attempts to sign a user in 
	 * @param username
	 * @param password
	 * @return true on success, false on error
	 */
	public boolean signIn(String username, String password) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","byUserPass"));
		nameValuePairs.add(new BasicNameValuePair("username",username));
		nameValuePairs.add(new BasicNameValuePair("password",password));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/signin.php");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			//convert response to string

			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();

			if (result.equals("Sign in failed\n")){
				return false;
			}
		} catch (IOException e){
			return false;
		}

		String UniqueId = "";
		int id = 0;
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				id = json_data.getInt("id");
				UniqueId = json_data.getString("unique_id");
			}
		} catch (JSONException e){
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}

	/**
	 * attempts to sign a user in using users UniqueId
	 * @param UniqueId
	 * @return true on success, false on error
	 */
	public boolean signIn(String UniqueId) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","byUniqueId"));
		nameValuePairs.add(new BasicNameValuePair("id",UniqueId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/signin.php");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			//convert response to string
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();

			if (result.equals("Sign in failed\n")){
				return false;
			}
		} catch (IOException e){
			return false;
		}

		String username = "";
		int id = 0;
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				id = json_data.getInt("id");
				username = json_data.getString("username");
			}
		} catch (JSONException e){
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}

	/**
	 * fetcher for userId
	 * @return id of current user, -1 if user is null
	 */
	public int getUserId() {
		if (user != null) {
			return user.getId();
		} else {
			return -1;
		}
	}

	/**
	 * fetcher for UniqueId
	 * @return uniqueId of current user, null if user is null
	 */
	public String getUserUniqueId() {
		if (user != null) {
			return user.getUniqueId();
		} else {
			return null;
		}
	}

	/**
	 * signs out current user by signing into guest account
	 * sets user to null on failure
	 */
	public void signOut() {
		if (!signIn("guest", "guestPwd")){
			user = null;
		}
	}

	/**
	 * 
	 * @return user object for this settings
	 */
	public User getUser(){
		return user;
	}
}
