package recipe_reader.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
 * Runs queries against a back-end database
 */

public class Searcher {

	/**
	 * method gets creates a list of recipe overviews based on a keyword search
	 * @param keywords - what is being searched for
	 * @param searcher - who is doing the searching
	 * @return a list of RecipeOverview objects corresponding to the search keyterms, null on failure
	 */
	public static List<RecipeOverview> getRecipeOverviewsByKeywords(List<String> keywords, User searcher) {
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
			if (result.equals("search failed\n") || result.equals("null\n")){
				return null;
			}
		} catch (IOException e) {
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
			return null;
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

	/**
	 * gets a recipeOverview from a recipe id
	 * @param id - id of the recipe
	 * @param searcher - who is getting the overview
	 * @return recipeOverview corresponding to the id, null on failure
	 */
	public static RecipeOverview getOverviewFromId(int id, User searcher) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getOverview"));
		nameValuePairs.add(new BasicNameValuePair("id",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
			if (result.equals("GetOverview by id failed\n")){
				return null;
			}
		} catch (IOException e) {
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
				category = new Category(json_data.getString("cat"));
			}
		} catch (JSONException e){
			return null;
		}

		RecipeOverview ov = new RecipeOverview(name, category, favorite, rating, description, id);
		return ov;
	}	

	/**
	 * tells if a recipe is a favorite of a user
	 * @param u - user to check
	 * @param recipeId - id of the recipe to check
	 * @return true of a favorite, false otherwise or on failure
	 */
	public static boolean isFavorite(User u, int recipeId) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","check"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+recipeId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
			if (result.equals("check favorite failed\n")){
				return false;
			}
		} catch (IOException e) {
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
			return false;
		}

		return (useri == u.getId() && recipei == recipeId);
	}

	/**
	 * adds a recipe to a users favorites
	 * @param u - user to add recipe to favorites
	 * @param id - recipe id to add
	 * @return true on success, false on failure
	 */
	public static boolean addRecipeToFavoriteById(User u, int id) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","add"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
			return (result.equals("favorite added\n"));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * get all favorites of a user
	 * @param u - user to get favorites for
	 * @return list of RecipeOverviews corresponding to user's favorites, null on failure
	 */
	public static List<RecipeOverview> getFavoritesByUser(User u) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getall"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
			if (result.equals("get all favorites failed\n")){
				return null;
			} else if (result.equals("null\n")){
				return new ArrayList<RecipeOverview>();
			}
		} catch (IOException e) {
			return null;
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
			return null;
		}

		return list;
	}

	/**
	 * remove a recipe from a users favorites
	 * @param u - user to remove from
	 * @param id - recipe id to be removed
	 * @return true on success, false on failure
	 */
	public static boolean removeRecipeFromFavoriteById(User u, int id) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","remove"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/favorite.php");
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
			return (result.equals("remove favorite successful\n"));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * @deprecated
	 * @param u
	 * @param id
	 * @param note
	 * @return
	 */
	public static boolean addNoteForUserById(User u, int id, String note) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","add"));
		nameValuePairs.add(new BasicNameValuePair("userid",""+u.getId()));
		nameValuePairs.add(new BasicNameValuePair("recipeid",""+id));
		nameValuePairs.add(new BasicNameValuePair("text",""+note));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/notes.php");
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
			return (result.equals("note added\n"));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * get all meals in the database
	 * @return list of meals, null on failure
	 */
	public static List<Meal> getMeals() {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getMeals"));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
			if (result.equals("get meals failed\n")){
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		List<Meal> meals = new ArrayList<Meal>();

		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Meal m = new Meal(json_data.getString("name"));
				meals.add(m);
			}
		} catch (JSONException e){
			return null;
		}
		return meals;
	}

	/**
	 * get all categories in the database
	 * @return list of categories, null on failure
	 */
	public static List<Category> getCategories() {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getCategories"));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
			if (result.equals("get categories failed\n")){
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		List<Category> categories = new ArrayList<Category>();

		try{
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Category m = new Category(json_data.getString("cat"));
				categories.add(m);
			}
		} catch (JSONException e){
			return null;
		}
		return categories;
	}

	/**
	 * get the names of all categories
	 * @return list of strings, null on failure
	 */
	public static List<String> getCategoryStrings() {
		List<Category> categories = getCategories();
		List<String> catStrings = new ArrayList<String>();
		if (categories == null){
			return null;
		}
		for (Category c: categories) {
			catStrings.add(c.getName());
		}
		return catStrings;
	}

	/**
	 * get recipe overviews by meal and category
	 * @param mealId
	 * @param categoryId
	 * @param searcher
	 * @return list of overviews, null on failure
	 */
	public static List<RecipeOverview> getOverviewByMealCategory(int mealId, int categoryId, User searcher) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getIdsByCatMeal"));
		nameValuePairs.add(new BasicNameValuePair("categoryid",""+categoryId));
		nameValuePairs.add(new BasicNameValuePair("mealid",""+mealId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
			if (result.equals("get overviews by category and meal failed\n") || result.equals("null\n")){
				return null;
			}
		} catch (IOException e) {
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
			return null;
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
	
	/**
	 * get recipe overviews by category
	 * @param categoryId
	 * @param searcher
	 * @return list of overviews, null on failure
	 */
	public static List<RecipeOverview> getOverviewByCategory(int categoryId, User searcher) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type","getIdsByCat"));
		nameValuePairs.add(new BasicNameValuePair("categoryid",""+categoryId));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/search.php");
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
			if (result.equals("get overviews by category failed\n") || result.equals("null\n")){
				return null;
			}
		} catch (IOException e) {
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
			return null;
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
