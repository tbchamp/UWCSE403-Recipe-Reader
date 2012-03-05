package recipe_reader.model;
import java.util.ArrayList;
import java.util.List;

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


/**
 * Runs queries against a back-end database
 */

public class Searcher {

	public Searcher() {
	}

	/* Connections to mysql */

	
	public static List<RecipeOverview> getRecipeOverviewsByKeywords(List<String> keywords, User searcher)
			throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","standard"));
		String terms = "";
		for (String s: keywords){
			terms += s + "_";
		}
		terms = terms.substring(0, terms.length() - 1);
		nameValuePairs.add(new BasicNameValuePair("terms",terms));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
		if (result.equals("search failed\n") || result.equals("null\n")){
			return null;
		}
		List<Integer> ids = new ArrayList<Integer>();
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				ids.add(json_data.getInt("recipe_id"));
			}
		} catch (JSONException e){
			System.out.println("2json nosj" + e.getMessage());
		}
		List<RecipeOverview> ovs = new ArrayList<RecipeOverview>();
		for (int i = 0; i < ids.size() && i < 20; i++){
			RecipeOverview temp = getOverviewFromId(ids.get(i), searcher);
			if (temp == null){
				return null;
			}
			ovs.add(temp);
		}
		return ovs;
	}

	public static RecipeOverview getOverviewFromId(int id, User searcher) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getOverview"));
		nameValuePairs.add(new BasicNameValuePair("id",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
		if (result.equals("GetOverview by id failed\n")){
			return null;
		}
		String name = "";
		Category category = null;
		boolean favorite = isFavorite(searcher, id);
		int rating = 0;
		String description = ""; 
		
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				name =  json_data.getString("name");
				description =  json_data.getString("description");
				rating = json_data.getInt("rating");
				category = new Category(json_data.getInt("id"), json_data.getString("cat"));
			}
		} catch (JSONException e){
			System.out.println("1json nosj");
		}
		
		RecipeOverview ov = new RecipeOverview(name, category, favorite, rating, description, id);
		return ov;
	}	
	
	public static boolean isFavorite(User u, int recipeId) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","check"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+recipeId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
		if (result.equals("check favorite failed\n")){
			return false;
		}
		int useri = 0;
		int recipei = 0;
		
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				useri =  json_data.getInt("user_id");
				recipei =  json_data.getInt("recipe_id");
			}
		} catch (JSONException e){
			System.out.println("3json nosj");
		}
		
		return (useri == u.getId() && recipei == recipeId);
	}
	
	public static boolean addRecipeToFavoriteById(User u, int id) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","add"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
		return (result.equals("favorite added\n"));
	}
	
	public static List<RecipeOverview> getFavoritesByUser(User u) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getall"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
		if (result.equals("get all favorites failed\n")){
			return null;
		} else if (result.equals("null\n")){
			return new ArrayList<RecipeOverview>();
		}
		List<RecipeOverview> list = new ArrayList<RecipeOverview>();
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				RecipeOverview temp = getOverviewFromId(json_data.getInt("recipe_id"), u);
				if (temp == null){
					return null;
				} else {
					list.add(temp);
				}
			}
		} catch (JSONException e){
			System.out.println("4json nosj" + e.getMessage());
		}
		
		return list;
	}
	
	public static boolean removeRecipeFromFavoriteById(User u, int id) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","remove"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
		return (result.equals("remove favorite successful\n"));
	}
	
	public static boolean addNoteForUserById(User u, int id, String note) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","add"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		nameValuePairs.add(new BasicNameValuePair("text",""+note));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/notes.php");
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
		return (result.equals("note added\n"));
	}
	
	public static List<Meal> getMeals() throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getMeals"));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
		if (result.equals("get meals failed\n")){
			return null;
		}
		List<Meal> meals = new ArrayList<Meal>();
		
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Meal m = new Meal(json_data.getInt("id"), json_data.getString("name"));
				meals.add(m);
			}
		} catch (JSONException e){
			System.out.println("5json nosj");
		}
		return meals;
	}
	
	public static List<Category> getCategories() throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getCategories"));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
		if (result.equals("get categories failed\n")){
			return null;
		}
		List<Category> categories = new ArrayList<Category>();
		
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Category m = new Category(json_data.getInt("id"), json_data.getString("cat"));
				categories.add(m);
			}
		} catch (JSONException e){
			System.out.println("6json nosj" + e.getMessage());
		}
		return categories;
	}
	
	public static List<String> getCategoryStrings() throws Exception {
		List<Category> categories = getCategories();
		List<String> catStrings = new ArrayList<String>();
		for (Category c: categories) {
			catStrings.add(c.getName());
		}
		return catStrings;
	}
	
	public static List<RecipeOverview> getOverviewByMealCategory(int mealId, int categoryId, User searcher) throws Exception{
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getIdsByCatMeal"));
		nameValuePairs.add(new BasicNameValuePair("categoryid",""+categoryId));
		nameValuePairs.add(new BasicNameValuePair("mealid",""+mealId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
		if (result.equals("get overviews by category and meal failed\n")){
			return null;
		}
		List<Integer> ids = new ArrayList<Integer>();
		//parse json data
		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				ids.add(json_data.getInt("id"));
			}
		} catch (JSONException e){
			System.out.println("7json nosj" + e.getMessage());
		}
		List<RecipeOverview> ovs = new ArrayList<RecipeOverview>();
		for (int i = 0; i < ids.size() && i < 20; i++){
			RecipeOverview temp = getOverviewFromId(ids.get(i), searcher);
			if (temp == null){
				return null;
			}
			ovs.add(temp);
		}
		return ovs;
	}
}
