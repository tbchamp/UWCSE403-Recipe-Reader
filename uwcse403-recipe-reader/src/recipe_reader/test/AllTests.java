package recipe_reader.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * AllTest is a test suite containing all tests for the application.
 * @author Tyler Beauchamp, Zach Evans, Kristin Ivarson, 
 * Anton Osobov, Jonathan Ramaswamy, Alisa Yamanaka
 *
 */
public class AllTests extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
    }
}