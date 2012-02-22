package uwcse403.recipe_reader;

import java.io.BufferedReader;
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

public class Settings {
	private User user;
	
	public Settings(){	
	}
	
	public boolean createUser(String username, String password) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username",username));
		nameValuePairs.add(new BasicNameValuePair("password",password));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/createuser.php");
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
			System.out.println("8json nosj");
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}
	
	public boolean signIn(String username, String password) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","byUserPass"));
		nameValuePairs.add(new BasicNameValuePair("username",username));
		nameValuePairs.add(new BasicNameValuePair("password",password));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/signin.php");
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
			System.out.println("9json nosj" + e.getMessage());
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}
	
	public boolean signIn(String UniqueId) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","byUniqueId"));
		nameValuePairs.add(new BasicNameValuePair("id",UniqueId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/signin.php");
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
			System.out.println("10json nosj");
			return false;
		}
		this.user = new User(username, id, UniqueId);
		return true;
	}
	
	public int getUserId(){
		return user.getId();
	}
	
	public String getUserUniqueId(){
		return user.getUniqueId();
	}
	
	public void signOut(){
		this.user = null;
	}
	
	public User getUser(){
		return user;
	}
}
