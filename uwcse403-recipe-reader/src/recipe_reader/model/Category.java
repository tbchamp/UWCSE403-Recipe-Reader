package recipe_reader.model;

import java.util.HashMap;
import java.util.Map;

import uwcse403.recipe_reader.R;

public class Category {
	private int icon;
	private int id;
	private String name;
		
	public Category(int id, String name) {
		this.setId(id);
		this.setName(name);
		this.icon = icons.get(name);
	}
	
	private static final Map<String, Integer> icons = 
		new HashMap<String, Integer>(){
        {
            put("Vegetarian", R.drawable.vegetarian);
            put("Dairy", R.drawable.dairy);
    		put("Entree",R.drawable.entree);
    		put("Side Dish",R.drawable.sidedish);
            put("Gluten Free", R.drawable.gluten_free);
            put("Vegan", R.drawable.vegan);
            put("Beverage", R.drawable.beverage);
        }
    };
	
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
	
	public String toString() {
		return name;
	}
}
