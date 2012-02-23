package recipe_reader.model;

import java.util.List;

public class Directions {
	private int curr;
	private List<String> directions;
	private int numDirections;
	
	public Directions(List<String> directions){
		this.directions = directions;
		numDirections = directions.size();
		curr = 0;
	}
	
	public String getNextDirection(){
		if (curr + 1 >= numDirections){
			throw new IllegalStateException("There are no further directions");
		}
		curr++;
		return (curr + 1) + ". " + directions.get(curr);
	}
	
	public String repeatDirection(){
		return (curr + 1) + ". " + directions.get(curr);
	}
	
	public String getPreviousDirection(){
		if (curr - 1 < 0){
			throw new IllegalStateException("Already on the first direction");
		}
		curr--;
		return (curr + 1) + ". " + directions.get(curr);
	}
	
	public List<String> getDirectionList(){
		return directions;
	}
}
