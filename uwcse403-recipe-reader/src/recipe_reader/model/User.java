package recipe_reader.model;

/*
 * Represents a User of the RecipeReader application. A User is described by a username, a unique string ID,
 * 	and a unique integer ID through which to identify a User.
 */

public class User {
	private String username;
	private String UniqueId;
	private int id;
	
	/**
	 * Constructor for a User object
	 * 
	 * @param username - username of the user being created
	 * @param id - non-negative integer id to identify the user by
	 * @param UniqueId - a unique string id to identify the user by
	 * @throws IllegalArgumentException if any inputs are null or empty or negative (bad)
	 */
	public User(String username, int id, String UniqueId) throws Exception{
		if(id < 0 || username == null || username.equals("") || UniqueId == null || UniqueId.equals("")){
			throw new IllegalArgumentException();
		}
		this.username = username;
		this.UniqueId = UniqueId;
		this.id = id;
	}
	
	/**
	 * @return returns the username of the User
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return returns the integer id of the User
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * @return returns the unique string id of the User
	 */
	public String getUniqueId(){
		return UniqueId;
	}
}
