package uwcse403.recipe_reader;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.FileInputStream;

/**
 * Runs queries against a back-end database
 */

public class Searcher {
	private String configFilename;
	private Properties configProps = new Properties();

	private String mysqlDriver;
	private String mysqlUrl;
	private String mysqlUser;
	private String mysqlPassword;
	
	// DB Connection
	private Connection conn;

	// Queries
	private static final String SEARCH_SQL =
		"SELECT name, rating, img_loc FROM zfr_table WHERE id = ?";
	private PreparedStatement searchStatement;


	public Searcher(String configFilename) {
		this.configFilename = configFilename;
	}

    /* Connections to mysql */

	public void openConnection() throws Exception {
		configProps.load(new FileInputStream(configFilename));

		mysqlDriver   = configProps.getProperty("recipereader.jdbc_driver");
		mysqlUrl	   = configProps.getProperty("recipereader.url");
		mysqlUser	   = configProps.getProperty("recipereader.username");
		mysqlPassword = configProps.getProperty("recipereader.password");


		/* load jdbc drivers */
		Class.forName(mysqlDriver).newInstance();

		/* open connections to the database */

		conn = DriverManager.getConnection(mysqlUrl, // database
				mysqlUser, // user
				mysqlPassword); // password
	}

	public void closeConnection() throws Exception {
		conn.close();
	}


	public void prepareStatements() throws Exception {
		searchStatement = conn.prepareStatement(SEARCH_SQL);
	}

    /**********************************************************/
  
	public void transaction_getRecipeOverviewById(int id)
			throws Exception {
		
		searchStatement.clearParameters();
		searchStatement.setInt(1, id);
		ResultSet recipe = searchStatement.executeQuery();
		
		while (recipe.next()){
			String name = recipe.getString(1);
			System.out.println("recipe name: " + name);
			int rating = recipe.getInt(2);
			System.out.println("recipe rating: " + rating);
			String imgUrl = recipe.getString(3);
			System.out.println("recipe imgUrl: " + imgUrl);

		}
		recipe.close();
	}

}
