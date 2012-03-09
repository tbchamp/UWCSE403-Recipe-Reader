package recipe_reader.model;

import java.io.BufferedReader;
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

/**
 * Recipe holds all the information for any given recipe
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class Recipe {

	private RecipeOverview overview;
	private String prep;
	private String cook;
	private String yield;
	private List<String> ingredients;
	private Directions directions;
	private List<String> notes;
	private List<String> keywords;
	private int calories;
	private int fat;
	private int cholesterol;
	private String meal;
	private String readytime;
	private String imgLoc;

	/**
	 * class constructor using overview
	 * @param overview
	 */
	public Recipe(RecipeOverview overview) {
		this.setOverview(overview);
	}

	/**
	 * empty class constructor for testing
	 */
	public Recipe(){}

	/**
	 * class constructor to init all fields
	 * @param name
	 * @param prep
	 * @param cook
	 * @param yield
	 * @param ingredients
	 * @param directions
	 * @param notes
	 * @param keywords
	 * @param calories
	 * @param fat
	 * @param cholesterol
	 * @param category
	 * @param isFavoriteOfUser
	 * @param description
	 * @param meal
	 * @param readytime
	 * @param imgLoc
	 */
	public Recipe(String name, String prep, String cook, String yield, List<String> ingredients,
			Directions directions, List<String> notes, List<String> keywords, int calories, 
			int fat, int cholesterol, Category category, boolean isFavoriteOfUser, 
			String description, String meal, String readytime, String imgLoc) {
		setOverview(new RecipeOverview(name, category, isFavoriteOfUser, description, 0));
		this.setPrep(prep);
		this.setCook(cook);
		this.setYield(yield);
		this.setIngredients(ingredients);
		this.setDirections(directions);
		this.setNotes(notes);
		this.setKeywords(keywords);
		this.setCalories(calories);
		this.setFat(fat);
		this.setCholesterol(cholesterol);
		this.setMeal(meal);
		this.setReadytime(readytime);
		this.setImgLoc(imgLoc);
	}

	/**
	 * fetcher for overview
	 * @return overview for this recipe
	 */
	public RecipeOverview getOverview() {
		return overview;
	}

	/**
	 * fetcher for name
	 * @return name for this recipe
	 */
	public String getName() {
		return overview.getName();
	}

	/**
	 * fetcher for prep
	 * @return prep for this recipe
	 */
	public String getPrep() {
		return prep;
	}

	/**
	 * fetcher for cook
	 * @return cook for this recipe
	 */
	public String getCook() {
		return cook;
	}

	/**
	 * fetcher for yield
	 * @return yield for this recipe
	 */
	public String getYield() {
		return yield;
	}

	/**
	 * fetcher for ingredients
	 * @return ingredients for this recipe
	 */
	public List<String> getIngredients() {
		return ingredients;
	}

	/**
	 * fetcher for directions
	 * @return directions for this recipe
	 */
	public Directions getDirections() {
		return directions;
	}

	/**
	 * fetcher for notes
	 * @return notes for this recipe
	 */
	public List<String> getNotes() {
		return notes;
	}

	/**
	 * fetcher for calories
	 * @return calories for this recipe
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * fetcher for fat
	 * @return fat for this recipe
	 */
	public int getFat() {
		return fat;
	}

	/**
	 * fetcher for cholesterol
	 * @return cholesterol for this recipe
	 */
	public int getCholesterol() {
		return cholesterol;
	}

	/**
	 * fetcher for keywords
	 * @return keywords for this recipe
	 */
	public List<String> getKeywords() {
		return keywords;
	}

	/**
	 * fetcher for meal
	 * @return meal for this recipe
	 */
	public String getMeal() {
		return meal;
	}

	/**
	 * fetcher for readytime
	 * @return readytime for this recipe
	 */
	public String getReadytime() {
		return readytime;
	}

	/**
	 * fetcher for imgLoc
	 * @return imgLoc for this recipe
	 */
	public String getImgLoc() {
		return imgLoc;
	}

	/**
	 * set overview for this recipe
	 * @param overview
	 * @throws IllegalStateException if overview already set
	 */
	public void setOverview(RecipeOverview overview) {
		if (this.overview != null){
			throw new IllegalStateException();
		}
		this.overview = overview;
	}

	/**
	 * set prep for this recipe
	 * @param prep
	 * @throws IllegalStateException if prep already set
	 */
	public void setPrep(String prep) {
		if (this.prep != null){
			throw new IllegalStateException();
		}
		this.prep = prep;
	}

	/**
	 * set cook for this recipe
	 * @param cook
	 * @throws IllegalStateException if cook already set
	 */
	public void setCook(String cook) {
		if (this.cook != null){
			throw new IllegalStateException();
		}
		this.cook = cook;
	}

	/**
	 * set yield for this recipe
	 * @param yield
	 * @throws IllegalStateException if yield already set
	 */
	public void setYield(String yield) {
		if (this.yield != null){
			throw new IllegalStateException();
		}
		this.yield = yield;
	}

	/**
	 * set ingredients for this recipe
	 * @param ingredients
	 * @throws IllegalStateException if ingredients already set
	 */
	public void setIngredients(List<String> ingredients) {
		if (this.ingredients != null){
			throw new IllegalStateException();
		}
		this.ingredients = ingredients;
	}

	/**
	 * set directions for this recipe
	 * @param directions
	 * @throws IllegalStateException if directions already set
	 */
	public void setDirections(Directions directions) {
		if (this.directions != null){
			throw new IllegalStateException();
		}
		this.directions = directions;
	}

	/**
	 * set notes for this recipe
	 * @param notes
	 * @throws IllegalStateException if notes already set
	 */
	public void setNotes(List<String> notes) {
		if (this.notes != null){
			throw new IllegalStateException();
		}
		this.notes = notes;
	}

	/**
	 * set calories for this recipe
	 * @param calories
	 * @throws IllegalStateException if calories already set
	 */
	public void setCalories(int calories) {
		if (this.calories != 0){
			throw new IllegalStateException();
		}
		this.calories = calories;
	}

	/**
	 * set fat for this recipe
	 * @param fat
	 * @throws IllegalStateException if fat already set
	 */
	public void setFat(int fat) {
		if (this.fat != 0){
			throw new IllegalStateException();
		}
		this.fat = fat;
	}

	/**
	 * set cholesterol for this recipe
	 * @param cholesterol
	 * @throws IllegalStateException if cholesterol already set
	 */
	public void setCholesterol(int cholesterol) {
		if (this.cholesterol != 0){
			throw new IllegalStateException();
		}
		this.cholesterol = cholesterol;
	}

	/**
	 * set keywords for this recipe
	 * @param keywords
	 * @throws IllegalStateException if keywords already set
	 */
	public void setKeywords(List<String> keywords) {
		if (this.keywords != null){
			throw new IllegalStateException();
		}
		this.keywords = keywords;
	}

	/**
	 * set meal for this recipe
	 * @param meal
	 * @throws IllegalStateException if meal already set
	 */
	public void setMeal(String meal) {
		if (this.meal != null){
			throw new IllegalStateException();
		}
		this.meal = meal;
	}

	/**
	 * set readytime for this recipe
	 * @param readytime
	 * @throws IllegalStateException if readytime already set
	 */
	public void setReadytime(String readytime) {
		if (this.readytime != null){
			throw new IllegalStateException();
		}
		this.readytime = readytime;
	}

	/**
	 * set imgLoc for this recipe
	 * @param imgLoc
	 * @throws IllegalStateException if imgLoc already set
	 */
	public void setImgLoc(String imgLoc) {
		if (this.imgLoc != null){
			throw new IllegalStateException();
		}
		this.imgLoc = imgLoc;
	}

	/**
	 * @deprecated
	 * @param adder
	 * @return
	 * @throws Exception
	 */
	public boolean addToDatabase(User adder) throws Exception {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("name", overview.getName()));
		nameValuePairs.add(new BasicNameValuePair("description", overview.getDescription()));
		nameValuePairs.add(new BasicNameValuePair("rating", ""+overview.getRating()));
		nameValuePairs.add(new BasicNameValuePair("preptime",prep));
		nameValuePairs.add(new BasicNameValuePair("cooktime",cook));
		nameValuePairs.add(new BasicNameValuePair("yield",yield));
		nameValuePairs.add(new BasicNameValuePair("calories","" + calories));
		nameValuePairs.add(new BasicNameValuePair("fat","" + fat));
		nameValuePairs.add(new BasicNameValuePair("cholesterol","" + cholesterol));
		nameValuePairs.add(new BasicNameValuePair("meal",meal));
		nameValuePairs.add(new BasicNameValuePair("img_loc",imgLoc));
		nameValuePairs.add(new BasicNameValuePair("readytime",readytime));
		nameValuePairs.add(new BasicNameValuePair("category",overview.getCategory().getName()));
		//http post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/add_recipe.php");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		//convert response to string

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		is.close();
		result=sb.toString();
		System.out.println(result);
		if (result.equals("Recipe Create Failed!") || result.equals("Invalid Meal Name") 
				|| result.equals("Add Meal Failed") || result.equals("Invalid Category Name")
				|| result.equals("Add Category Failed")){
			return false;
		}
		int id = Integer.parseInt(result);
		//overview.setId(id);
		if (!addIngredientsToDB(id) || !addKeywordsToDB(id) || !addDirectionsToDB(id)
				|| !addNotesToDB(id, adder)){
			return false;
		}
		if (overview.getFavorite()){
			if (!Searcher.addRecipeToFavoriteById(adder, id)){
				return false;
			}
		}
		return true;
	}


	/**
	 * @deprecated
	 * @param recipeId
	 * @return
	 * @throws Exception
	 */
	private boolean addIngredientsToDB(int recipeId) throws Exception {
		for (String i : ingredients) {
			String result = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("recipe_id", "" + recipeId));
			nameValuePairs.add(new BasicNameValuePair("type", "ingredient"));
			nameValuePairs.add(new BasicNameValuePair("text", i));
			//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/addtorecipe.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			//convert response to string

			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			result=sb.toString();
			if (result.equals("Add Ingredient Failed")){
				return false;
			}
		}
		return true;		
	}

	/**
	 * @deprecated
	 * @param recipeId
	 * @return
	 * @throws Exception
	 */
	private boolean addKeywordsToDB(int recipeId) throws Exception{
		for (String k : keywords) {
			String result = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("recipe_id", "" + recipeId));
			nameValuePairs.add(new BasicNameValuePair("type", "keyword"));
			nameValuePairs.add(new BasicNameValuePair("phrase", k));
			//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/addtorecipe.php");
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
			if (result.equals("Add Keyword Failed\n")){
				return false;
			}
		}
		return true;			
	}

	/**
	 * @deprecated
	 * @param recipeId
	 * @return
	 * @throws Exception
	 */
	private boolean addDirectionsToDB(int recipeId) throws Exception{
		List<String> list = directions.getDirectionList();
		for (int i = 0; i < list.size(); i++) {
			String result = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("recipe_id", "" + recipeId));
			nameValuePairs.add(new BasicNameValuePair("type", "direction"));
			nameValuePairs.add(new BasicNameValuePair("text", list.get(i)));
			nameValuePairs.add(new BasicNameValuePair("order", ""+i));
			//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/addtorecipe.php");
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
			if (result.equals("Add Direction Failed\n")){
				return false;
			}
		}
		return true;		
	}

	/**
	 * @deprecated
	 * @param recipeId
	 * @param adder
	 * @return
	 * @throws Exception
	 */
	public boolean addNotesToDB(int recipeId, User adder) throws Exception {
		for (String n : notes) {
			String result = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("recipe_id", "" + recipeId));
			nameValuePairs.add(new BasicNameValuePair("type", "note"));
			nameValuePairs.add(new BasicNameValuePair("text", n));
			nameValuePairs.add(new BasicNameValuePair("userid", ""+adder.getId()));
			//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/addtorecipe.php");
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
			if (result.equals("Add Note Failed\n")){
				return false;
			}
		}
		return true;	
	}

}