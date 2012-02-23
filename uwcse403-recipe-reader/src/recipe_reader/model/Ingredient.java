package recipe_reader.model;

public class Ingredient {
	private String type;
	private Double amount;
	private String unit;
	
	
	public Ingredient(String type, double amount, String unit){
		this.type = type;
		this.amount = amount;
		this.unit = unit;
	}
	
	public String toString(){
		String res = String.format("%.2f %s of %s", amount, unit, type);
		return res;
	}
	
	public String getType(){
		return type;
	}
	
	public Double getAmount(){
		return amount;
	}
	
	public String getUnit(){
		return unit;
	}
}
