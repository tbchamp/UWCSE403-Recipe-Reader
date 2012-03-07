package recipe_reader.test;

import android.test.AndroidTestCase;
import recipe_reader.model.Category;

public class CategoryTest extends AndroidTestCase {
	
	Category c;
	
	@Override
	public void setUp(){
		c = new Category("Vegetarian");
	}
	
	public void testOINK(){
		assertNotNull(c);
	}
}
