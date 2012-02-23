package recipe_reader.test;
/*
 * JUnit test class to assure that the Settings object functions correctly
 * This class contains black-box tests.
 * 
 * @author aosobov
 */


import org.junit.Test;

import recipe_reader.model.Settings;
import recipe_reader.model.User;

import junit.framework.TestCase;

public class SettingsTest extends TestCase {
	
	@Test
	public void test_constructor_1() {
		Settings s = new Settings();
		assertEquals(s.getUserId(), -1, .000000000000001);
	}
	
	@Test
	public void test_constructor_2() {
		Settings s = new Settings();
		assertEquals(s.getUserUniqueId(), null);
	}
	
	@Test
	public void test_newUserCreation_create() {
		Settings s = new Settings();
		
		try {
			assert(s.createUser("antons_tester_1", "blackbox"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_newUserCreation_usernameTaken() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assertFalse(s.createUser("antons_tester_2", "blackbox_2"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_userSignIn_byName_present() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assert(s.signIn("antons_tester_2", "blackbox"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_userSignIn_byName_absent() {
		Settings s = new Settings();
		
		try {
			assertFalse(s.signIn("antons_tester_33", "blackbox"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_userSignIn_byName_wrongPassword() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			assertFalse(s.signIn("antons_tester_2", "idontknow"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_signOut_username() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_5", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.signOut();
		assertEquals(s.getUser(), null);
	}
	
	@Test
	public void test_signOut_ID() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_5", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.signOut();
		assertEquals(s.getUserId(), -1, .000000000001);
	}
	
	@Test
	public void test_signOut_uniqueID() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_5", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.signOut();
		assertEquals(s.getUserUniqueId(), null);
	}
	
	@Test
	public void test_userSignIn_byID_IDnotregistered() {
		Settings s = new Settings();
		
		try {
			assertFalse(s.signIn("nope"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_userSignIn_byID_IDvalid() {
		Settings s = new Settings();
		
		try {
			s.createUser("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String testUniqueID = s.getUserUniqueId();
		s.signOut();
		
		try {
			assert(s.signIn(testUniqueID));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_getUser_UniqueID() {
		Settings s = new Settings();
		
		try {
			s.signIn("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User test = s.getUser();
		assertEquals(test.getUniqueId(), s.getUserUniqueId());
	}
	
	@Test
	public void test_getUser_ID() {
		Settings s = new Settings();
		
		try {
			s.signIn("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User test = s.getUser();
		assertEquals(test.getId(), s.getUserId());
	}
	
	@Test
	public void test_getUser_username() {
		Settings s = new Settings();
		
		try {
			s.signIn("antons_tester_2", "blackbox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User test = s.getUser();
		assertEquals(test.getUsername(), "antons_tester_2");
	}
	
	@Test
	public void test_getUser_badSigin() {
		Settings s = new Settings();
		
		try {
			s.signIn("antons_tester_2", "idontknow");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User test = s.getUser();
		assertEquals(test, null);
	}
	
}
