package uwcse403.recipe_reader;

import java.io.File;

public class test2 {

	//this is a test file to see if this is working!!
	//comment added by zaphans

	public static void main(String [] args) {
		Searcher s = new Searcher();
		try {
			RecipeOverview ro = s.transaction_getRecipeOverviewById(1);
			System.out.println("Success!\n Recipe Title: " + ro.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// hey look it's a comment! -kristini

}
