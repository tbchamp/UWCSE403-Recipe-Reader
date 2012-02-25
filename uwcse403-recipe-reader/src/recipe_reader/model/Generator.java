package recipe_reader.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class Generator {

	public Generator() {
	}
	
	/**

	private Set<Ingredient> ingredients;
	private Directions directions;
	private List<String> notes;
	private List<String> keywords;
	 * @throws Exception 

	 */
	
	public Recipe getRecipe(int id, User searcher) throws Exception {
		RecipeOverview temp = Searcher.getOverviewFromId(id, searcher);
		return getRecipe(temp);
	}
	
	public Recipe getRecipe(RecipeOverview overview) throws Exception {
		String result = "";
		int id = overview.getId();
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getMain"));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/fetcher.php");
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
		if (result.equals("get main recipe failed\n")){
			return null;
		}
		Recipe r = new Recipe(overview);
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				r.setPrep(json_data.getString("r.prep_time"));
				r.setCook(json_data.getString("r.cook_time"));
				r.setYield(json_data.getString("r.yield"));
				r.setReadytime(json_data.getString(r.getReadytime()));
				r.setCalories(json_data.getInt("r.calories"));
				r.setFat(json_data.getInt("r.fat"));
				r.setCholesterol(json_data.getInt("r.cholesterol"));
				r.setMeal(json_data.getString("m.name"));
				r.setImgLoc(json_data.getString("r.img_loc"));
			}
		} catch (JSONException e){
			System.out.println("11json nosj");
		}
		r.setIngredients(getIngredients(id));
		r.setDirections(getDirections(id));
		r.setKeywords(getKeywords(id));
		r.setNotes(getNotes(id));
		return r;
	}
	
	
	private List<Ingredient> getIngredients(int id) throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","ingredients"));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/fetcher.php");
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
		if (result.equals("get ingredients failed\n")){
			return null;
		}
		List<Ingredient> ing = new ArrayList<Ingredient>();
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				String type = json_data.getString("type");
				double amount = json_data.getDouble("amount");
				String unit = json_data.getString("unit");
				ing.add(new Ingredient(type, amount, unit));
			}
		} catch (JSONException e){
			System.out.println("12json nosj");
		}
		return ing;
	}
	
	private Directions getDirections(int id) throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","directions"));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/fetcher.php");
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
		if (result.equals("get directions failed\n")){
			return null;
		}

		List<String> d = new ArrayList<String>();
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				d.add(json_data.getString("text"));
			}
		} catch (JSONException e){
			System.out.println("13json nosj");
		}
		return new Directions(d);
	}
	
	private List<String> getKeywords(int id) throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","keywords"));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/fetcher.php");
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
		if (result.equals("get keywords failed\n")){
			return null;
		}

		List<String> k = new ArrayList<String>();
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				k.add(json_data.getString("phrase"));
			}
		} catch (JSONException e){
			System.out.println("14json nosj");
		}
		return k;
	}
	
	private List<String> getNotes(int id) throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","notes"));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/fetcher.php");
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
		if (result.equals("get notes failed\n")){
			return null;
		}

		List<String> n = new ArrayList<String>();
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				n.add(json_data.getString("text"));
			}
		} catch (JSONException e){
			System.out.println("15json nosj");
		}
		return n;
	}
}
