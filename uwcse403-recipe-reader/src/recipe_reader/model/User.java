package recipe_reader.model;

/**
 * User represents a single user of the application. Each user has a name, password, and unique ID
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class User {
	private final String username;
	private final String UniqueId;
	private final int id;
	
	/**
	 * Constructor for a User object. Uniqueness of ids is handled by the database.
	 * 
	 * @param username - username of the user being created
	 * @param id - non-negative integer id to identify the user by
	 * @param UniqueId - a unique string id to identify the user by
	 * @throws IllegalArgumentException if any inputs are null or empty or negative (bad)
	 */
	public User(String username, int id, String UniqueId) {
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
