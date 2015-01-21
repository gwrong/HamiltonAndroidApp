package com.ninerupees.hamilton.database;

/**
 * The Default implementation of the database.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class DefaultDatabase extends FirebaseInterface {
    private static DatabaseInterface database;

    /**
     * Gets the database.
     * @return The Database
     */
    public static DatabaseInterface getDatabase() {
        if (database == null) {
            database = new FirebaseInterface();
        }
        return database;
    }
}
