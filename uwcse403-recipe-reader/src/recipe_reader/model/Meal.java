package recipe_reader.model;

import java.util.HashMap;
import java.util.Map;

public class Meal {
	private int id;
	private String name;
	
	public Meal(String name){
		this.setName(name);
		this.setId(ids.get(name));
	}
	
	private static final Map<String, Integer> ids =
    	new HashMap<String, Integer>(){
        {
            put("Breakfast", 1);
            put("Lunch", 2);
            put("Dinner", 3);
            put("Snack", 4);
            put("Supper", 5);
    		put("Dessert", 6);
    		put("Other", 7);
        }
    };
    
    public int getIdByName(String meal) {
    	return ids.get(meal);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
