package com.ninerupees.hamilton.database;

import java.util.Map;

/**
 * The Database Interface.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface DatabaseInterface {
    /**
     * Has the database connect to the place.
     * @return Success or not
     */
    boolean connect();

    /**
     * Adds a user to the db.
     * @param username The username
     * @param password The pword
     * @param email The email
     * @param firstName First name
     * @param lastName Last name
     * @param eventListener The DB event listener
     */
    void addUser(String username, String password, String email,
            String firstName, String lastName,
            final DatabaseEventListener eventListener);

    /**
     * Logs in the user.
     * @param username The username
     * @param password The pword
     * @param eventListener The DB event listener
     */
    void login(String username, final String password,
            final DatabaseEventListener eventListener);

    /**
     * Creates a db account.
     * @param username THe username
     * @param accountName The account name
     * @param displayName Account display name
     * @param interestRate Interest rate
     * @param type Type
     * @param eventListener DB event listener
     */
    void createAccount(String username, final String accountName,
            final String displayName, final double interestRate,
            final String type, final DatabaseEventListener eventListener);

    /**
     * Gets the account names.
     * @param username The username
     * @param eventListener DB event listener
     */
    void getAccountNames(String username,
            final DatabaseEventListener eventListener);

    /**
     * Adds transaction.
     * @param username THe username
     * @param accountName The account name
     * @param balance The balance
     * @param transaction The transaction hashmap
     * @param eventListener DB event listener
     */
    void addTransactions(String username, final String accountName,
            final double balance, final Map<String, Object> transaction,
            final DatabaseEventListener eventListener);

    /**
     * Gets transactions.
     * @param username Username
     * @param accountName The account name
     * @param eventListener DB event listener
     */
    void getTransactions(String username, final String accountName,
            final DatabaseEventListener eventListener);

    /**
     * Gets the accounts.
     * @param username The username
     * @param eventListener DB event listener
     */
    void getAccounts(String username,
            final DatabaseEventListener eventListener);

    /**
     * Gets the user object.
     * @param username The username
     * @param eventListener The db event listener
     */
    void getUser(String username,
            final DatabaseEventListener eventListener);

}
