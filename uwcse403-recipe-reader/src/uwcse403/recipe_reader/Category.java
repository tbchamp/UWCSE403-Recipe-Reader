package uwcse403.recipe_reader;

public class Category {
	private int icon;
	private int id;
	private String name;
		
	Category(int id, String name) {
		this.setId(id);
		this.setName(name);
		this.icon = R.drawable.icon;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
