package recipe_reader.test;

import android.test.AndroidTestCase;
import recipe_reader.model.Category;

public class CategoryTest extends AndroidTestCase {
	
	Category dairyCategory;
	
	@Override
	public void setUp() {
		dairyCategory = new Category("Dairy");
	}
	
	
	/***************************************************	CONSTRUCTOR TESTS	***************************************************/
	
	// Tests the construction of a new Category is not null
	public void testConstructionOfNewCategoryIsNotNull() {
		assertNotNull(dairyCategory);
	}
	
	
	// Tests that two categories with the same name should not exist.
	//	May need to modify Category.java code for this to pass.
	public void testsThatTwoCategoriesWithSameNameShouldNotBeCreated() throws Exception {
		Category entree1 = new Category("Entree");
		
		try {
			Category entree2 = new Category("Entree");
			
			fail("Category with the same name of one already created was created, but it shouldn't have been.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	
	// Tests that a category with a name not contained in the ids/icons
	//	is not created.
	public void testsCategoryWithInvalidNameIsNotCreated() throws Exception {
		try {
			Category invalidName = new Category("Invalid Name");
			
			fail("Category with a name not found in icons or id sets was created, but it shouldn't have been.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	
	// Tests that when the Category constructor is passed a null name, an
	//	IllegalArgumentException is thrown.
	public void testsThatTheCategoryConstructorThrowsAnExceptionWhenPassedANullName() throws Exception {
		try {
			Category nullName = new Category(null);
			
			fail("Category with a null name was created, but it shouldn't have been.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	
	// Tests that when the Category constructor is passed an empty string, an
	//	IllegalArgumentException is thrown.
	public void testsThatTheCategoryConstructorThrowsAnExceptionWhenPassedAnEmptyString() throws Exception {
		try {
			Category empty = new Category("");
			
			fail("Category passed an empty string was created, but it shouldn't have been.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	
	
	/***************************************************	getId TESTS		***************************************************/
	
	// Tests that the correct ID is returned for Dairy Category
	public void testsThatTheCorrectIdIsReturnedWhenGetIdMethodIsCalledOnDairyCategory() {
		assertEquals("Dairy Category has an ID of 2.", 2, dairyCategory.getId());
	}
	
	
	// Tests that the correct ID is returned for Side Dish Category
	public void testsThatTheCorrectIdIsReturnedWhenGetIdMethodIsCalledOnSideDishCategory() {
		Category sideDish = new Category("Side Dish");
		assertEquals("Side Dish Category has an ID of 7.", 7, sideDish.getId());
	}
	
	
	
	/***************************************************	getIdByName TESTS	***************************************************/
	
	// Tests that getIdByName throws an exception when passed null.
	public void testsGetIdByNameMethodThrowsExceptionWhenPassedNull() throws Exception {
		try {
			dairyCategory.getIdByName(null);
			
			fail("Calling getIdByName() and passing null should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that getIdByName throws an exception when passed an empty string.
	public void testsGetIdByNameMethodThrowsExceptionWhenPassedAnEmptyString() throws Exception {
		try {
			dairyCategory.getIdByName("");
			
			fail("Calling getIdByName() and passing an empty String should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that getIdByName throws an exception when passed an invalid name string.
	public void testsGetIdByNameMethodThrowsExceptionWhenPassedAnInvalidNameString() throws Exception {
		try {
			dairyCategory.getIdByName("Gerbils");
			
			fail("Calling getIdByName() and passing an invalid name string should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that getIdByName returns the correct ID when passed a valid string.
	public void testsGetIdByNameMethodReturnsCorrectResultWhenPassedAValidString() {
		assertEquals("Passing getIdByName the Beverage string should result in the number 5.", 5, dairyCategory.getIdByName("Beverage"));
	}
	
	
	
	/***************************************************	getName TESTS	***************************************************/
	
	// Tests that the correct name is returned for Dairy Category.
	public void testsThatTheCorrectNameIsReturnedWhenGetNameMethodIsCalledOnDairyCategory() {
		assertEquals("Dairy Category's name is \"Dairy\".", "Dairy", dairyCategory.getName());
	}
	
	
	// Tests that the correct name is returned for Side Dish Category.
	public void testsThatTheCorrectNameIsReturnedWhenGetNameMethodIsCalledOnSideDishCategory() {
		Category sideDish = new Category("Side Dish");
		assertEquals("Side Dish Category's name is \"Side Dish\".", "Side Dish", sideDish.getName());
	}
	
	
	
	/***************************************************	getIcon TESTS	***************************************************/
	
	// Tests that the correct icon is returned for Dairy Category.
	public void testsThatTheCorrectIconIsReturnedWhenGetIconMethodIsCalledOnDairyCategory() {
		assertEquals("Dairy Category's icon is Something.", 2130837548, dairyCategory.getIcon());
	}
	
	
	// Tests that the correct icon is returned for Entree Category.
	public void testsThatTheCorrectNameIsReturnedWhenGetIconMethodIsCalledOnEntreeCategory() {
		Category entree = new Category("Entree");
		assertEquals("Entree Category's icon is Something.", 2130837549, entree.getIcon());
	}
	
	
	
	/***************************************************	setName TESTS	***************************************************/
	
	// Tests that passing setName null throws an exception.
	/*public void testsThatPassingSetNameNullThrowsAnException() throws Exception {
		Category testCategory = new Category("Beverage");
		
		try {
			testCategory.setName(null);
			
			fail("Passing setName() null should result in an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setName an empty string throws an exception.
	public void testsThatPassingSetNameAnEmptyStringThrowsAnException() throws Exception {
		Category testCategory = new Category("Beverage");
		
		try {
			testCategory.setName("");
			
			fail("Passing setName() an emptyString should result in an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setName an invalid name string throws an exception.
	public void testsThatPassingSetNameAnInvalidStringNameThrowsAnException() throws Exception {
		Category testCategory = new Category("Beverage");
		
		try {
			testCategory.setName("Smelly Cat");
			
			fail("Passing setName() an invalid string name should result in an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setName a valid name changes the Category's name.
	public void testsThatPassingSetNameAValidStringNameChangesTheCategorysName() {
		Category testCategory = new Category("Beverage");
		String initial = testCategory.getName();
		testCategory.setName("Entree");
		assertTrue("Beverage Category's name should switch from \"Beverage\" to \"Entree\" when setName is passed \"Entree\" string.",
				initial.equals("Beverage") && testCategory.getName().equals("Entree"));
	}
	
	// Since setName is called when a Category is initially constructed, this test ensures
	//	that the Category's name gets initially set correctly.
	public void testsThatTheCategorysNameIsInitiallySetCorrectlyUponConstruction() {
		assertEquals("Dairy's name should initally be \"Dairy\" when created.", "Dairy", dairyCategory.getName());
	}
	
	
	
	
	/***************************************************	setId TESTS		***************************************************/
	
	// Tests that passing setId zero throws an exception.
	/*public void testsThatPassingSetIdMethodZeroThrowsAnException() throws Exception {
		Category testCategory = new Category("Other");
		
		try {
			testCategory.setId(0);
			
			fail("Setting a Category's Id to zero should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setId a negative number throws an exception.
	public void testsThatPassingSetIdMethodANegativeNumberThrowsAnException() throws Exception {
		Category testCategory = new Category("Other");
		
		try {
			testCategory.setId(-1);
			
			fail("Setting a Category's Id to a negative number should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setId an integer larger than the largest ID throws an exception.
	public void testsThatPassingSetIdMethodANumberLargerThanTheLargestIdThrowsAnException() throws Exception {
		Category testCategory = new Category("Other");
		
		try {
			testCategory.setId(42);
			
			fail("Setting a Category's Id to a number larger than the largest Id should throw an exception.");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	// Tests that passing setId a valid integer Id changes the Category's Id.
	public void testsThatPassingSetIdMethodAValidNumberChnagesTheCategorysId() throws Exception {
		Category testCategory = new Category("Other");
		int initial = testCategory.getId();
		testCategory.setId(5);
		int ending = testCategory.getId();
		assertTrue("Calling setId(5) on Category Other should change its Id to 5.", initial == 8 && ending == 5);
	}*/
	
	// Since setId is called when a Category is initially constructed, this test ensures
	//	that the Category's Id gets initially set correctly.
	// Tests that the correct ID is returned for Dairy Category
	public void testsThatTheCategorysIdIsInitiallySetCorrectlyUponConstruction() {
		assertEquals("Dairy Category should initally have an Id of 2.", 2, dairyCategory.getId());
	}
	
	
	
	/***************************************************	toString TESTS	***************************************************/
	
	// Tests that the toString method returns the correct string for Dairy Category.
	public void testsThatTheCorrectStringIsReturnedWhenToStringMethodIsCalledOnDairyCategory() {
		assertEquals("Dairy Category's toString method should return the Category's name.", "Dairy", dairyCategory.toString());
	}
}
