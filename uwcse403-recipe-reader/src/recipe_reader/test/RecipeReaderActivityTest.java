package recipe_reader.test;

// White box test

import android.test.ActivityInstrumentationTestCase2;
import recipe_reader.view.RecipeReaderActivity;

public class RecipeReaderActivityTest extends ActivityInstrumentationTestCase2<RecipeReaderActivity> {
	
	private RecipeReaderActivity rra;
	
	public RecipeReaderActivityTest(){
		super(RecipeReaderActivity.class);
	}
	
	@Override
	public void setUp(){
		rra = (RecipeReaderActivity) getActivity();
	}
	
	// prefix with "test"
	public void test1(){
		System.out.println("WTF1!");
		assertNotNull(rra);
	}


}
