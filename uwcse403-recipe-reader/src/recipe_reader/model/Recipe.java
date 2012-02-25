package recipe_reader.model;

/**
 * Contains all the information about a specific recipe
 * Jonathan Ramaswamy
 */
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


public class Recipe {

	private RecipeOverview overview;
	private String prep;
	private String cook;
	private String yield;
	private List<Ingredient> ingredients;
	private Directions directions;
	private List<String> notes;
	private List<String> keywords;
	private int calories;
	private int fat;
	private int cholesterol;
	private String meal;
	private String readytime;
	private String imgLoc;

	public Recipe(RecipeOverview overview) {
		this.setOverview(overview);
	}
	
	public Recipe(String name) {
		//TODO: write this
		// Stub added so stuff would compile
	}
	
	public Recipe(String name, String prep, String cook, String yield, List<Ingredient> ingredients,
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

	public RecipeOverview getOverview() {
		return overview;
	}
	
	public String getName() {
		return overview.getName();
	}

	public void setOverview(RecipeOverview overview) {
		this.overview = overview;
	}

	public String getPrep() {
		return prep;
	}

	public void setPrep(String prep) {
		this.prep = prep;
	}

	public String getCook() {
		return cook;
	}

	public void setCook(String cook) {
		this.cook = cook;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Directions getDirections() {
		return directions;
	}

	public void setDirections(Directions directions) {
		this.directions = directions;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}

	public int getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(int cholesterol) {
		this.cholesterol = cholesterol;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
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
		overview.setId(id);
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


	private boolean addIngredientsToDB(int recipeId) throws Exception {
		for (Ingredient i : ingredients) {
			String result = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("recipe_id", "" + recipeId));
			nameValuePairs.add(new BasicNameValuePair("type", "ingredient"));
			nameValuePairs.add(new BasicNameValuePair("text", i.getText()));
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

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getReadytime() {
		return readytime;
	}

	public void setReadytime(String readytime) {
		this.readytime = readytime;
	}

	public String getImgLoc() {
		return imgLoc;
	}

	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}

}