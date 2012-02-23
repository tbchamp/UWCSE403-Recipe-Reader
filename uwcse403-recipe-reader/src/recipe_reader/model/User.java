package recipe_reader.model;

public class User {
	private String username;
	private String UniqueId;
	private int id;
	
	public User(String username, int id, String UniqueId){
		this.username = username;
		this.UniqueId = UniqueId;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getId(){
		return id;
	}
	
	public String getUniqueId(){
		return UniqueId;
	}
}
