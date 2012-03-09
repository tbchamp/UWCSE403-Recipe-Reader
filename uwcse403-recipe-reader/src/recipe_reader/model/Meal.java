package recipe_reader.model;

import java.util.HashMap;
import java.util.Map;

public class Meal {
	private final int id;
	private final String name;

	/**
	 * 
	 * @param name
	 * @throws IllegalArgumentException if name is null or invalid
	 */
	public Meal(String name){
		if(name == null || !ids.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.id = ids.get(name);
	}

	private static final Map<String, Integer> ids =
			new HashMap<String, Integer>(){
		private static final long serialVersionUID = 1L;
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

	/**
	 * 
	 * @param meal you want id for
	 * @return id of meal passed
	 * @throws IllegalArgumentException if null or invalid meal
	 */
	public int getIdByName(String meal) {
		if(meal == null || !ids.containsKey(meal)) {
			throw new IllegalArgumentException();
		}
		return ids.get(meal);
	}

	/**
	 * 
	 * @return id of this meal
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return name of this meal
	 */
	public String getName() {
		return name;
	}

}
