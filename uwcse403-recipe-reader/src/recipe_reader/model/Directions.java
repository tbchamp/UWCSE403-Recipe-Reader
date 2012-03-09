package recipe_reader.model;

import java.util.List;

/**
 * Directions holds a list of directions and methods to get next, previous, and current directions
 * @author $Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class Directions {
	private int curr;
	private final List<String> directions;
	private final int numDirections;

	/**
	 * class constructor initializes current step to 0
	 * @param directions
	 */
	public Directions(List<String> directions){
		this.directions = directions;
		numDirections = directions.size();
		curr = 0;
	}

	/**
	 * gets the next direction if one exists
	 * @return String corresponding to the next direction
	 * @throws IllegalStateException if already on the last direction
	 */
	public String getNextDirection(){
		if (curr + 1 >= numDirections){
			throw new IllegalStateException("There are no further directions");
		}
		curr++;
		return (curr + 1) + ". " + directions.get(curr);
	}

	/**
	 * gets the current direction
	 * @return String corresponding to current direction
	 */
	public String repeatDirection(){
		return (curr + 1) + ". " + directions.get(curr);
	}

	/**
	 * gets the previous direction if one exists
	 * @return String corresponding to previous direction
	 * @throws IllegalStateException if already on first direction
	 */
	public String getPreviousDirection(){
		if (curr - 1 < 0){
			throw new IllegalStateException("Already on the first direction");
		}
		curr--;
		return (curr + 1) + ". " + directions.get(curr);
	}

	/**
	 * gets all directions
	 * @return list of directions as strings
	 */
	public List<String> getDirectionList(){
		return directions;
	}

	/**
	 * @return the number of total directions
	 */
	public int getNumSteps() {
		return directions.size();
	}
}
