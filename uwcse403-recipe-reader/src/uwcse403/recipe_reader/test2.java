package uwcse403.recipe_reader;

public class test2 {

	//this is a test file to see if this is working!!
	//comment added by zaphans

	public static void main(String [] args) {
		Searcher s = new Searcher("dbconn.properties");
		try {
			s.openConnection();
			s.prepareStatements();
			s.transaction_getRecipeOverviewById(1);
			s.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// hey look it's a comment! -kristini

}
