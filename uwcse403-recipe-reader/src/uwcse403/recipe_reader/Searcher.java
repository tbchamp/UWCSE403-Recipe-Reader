package uwcse403.recipe_reader;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uwcse403.recipe_reader.RecipeOverview.Category;

/**
 * Runs queries against a back-end database
 */

public class Searcher {

	public Searcher() {
	}

	/* Connections to mysql */

	String result = "";
	public RecipeOverview transaction_getRecipeOverviewById(int id)
			throws Exception {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id","1"));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://students.washington.edu/zacheva/cse403/temp.php");
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

		String name = "";
		int rating = 0;
		String imgUrl = "";
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				rating = json_data.getInt("rating");
				name = json_data.getString("name");
				imgUrl = json_data.getString("img_loc");
			}
		} catch (JSONException e){
			System.out.println("json nosj");
		}
		boolean favorite = (rating > 50);
		return new RecipeOverview(name, Category.MAIN_COURSE, favorite);
	}

}
