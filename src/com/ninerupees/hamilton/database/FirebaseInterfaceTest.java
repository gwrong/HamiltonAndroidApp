package com.ninerupees.hamilton.database;

//import junit.framework.TestCase;

/**
 * Test for the Database.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class FirebaseInterfaceTest {

    /**
     * Set up method.
     * @return DatabaseInterfaces
     */
    public static DatabaseInterface selectDatabase() {
        return new FirebaseInterface();
    }

    /**
     * Tests adding the user.
     */
    public static void testAddUser() {
        DatabaseInterface database = selectDatabase();
        database.connect();
        try {
            database.addUser("ashaw596", "school", "ashaw596@gmail.com",
                    "Albert", "Shaw", new DatabaseEventListener() {
                        public void onSuccess(Object data) {
                            // System.out.println("hi");
                            // assertTrue("test", true);
                        }

                        public void onFail(Object data) {
                            // System.out.println("hi");
                            // fail("cause");
                        }
                    });
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            // Ignore.
        }
    }

}
