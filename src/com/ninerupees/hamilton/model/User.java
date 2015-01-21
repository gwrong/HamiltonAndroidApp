package com.ninerupees.hamilton.model;

import java.util.HashMap;

import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;

/**
 * Represents a User who uses hamilton.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private HashMap<String, AccountModel> accounts;

    /**
     * Creates an account.
     * @param account The account
     * @param eventListener The db event listener
     */
    public void createAccount(final AccountModel account,
            final DatabaseEventListener eventListener) {
        DatabaseInterface database = DefaultDatabase.getDatabase();
        database.createAccount(username, account.getFullName(),
                account.getDisplayName(), account.getMonthlyInterestRate(),
                account.getType(), new DatabaseEventListener() {

                    @Override
                    public void onSuccess(Object data) {
                        accounts.put(account.getFullName(), account);
                        eventListener.onSuccess(account);
                    }

                    @Override
                    public void onFail(Object data) {
                        eventListener.onFail(data);
                    }

                });
    }

    /**
     * Registers the user.
     * @param user The user
     * @param password The password
     * @param eventListener The db event listener
     */
    public static void registerUser(final User user, String password,
            final DatabaseEventListener eventListener) {
        DatabaseInterface database = DefaultDatabase.getDatabase();
        database.addUser(user.getUsername(), password, user.getEmail(),
                user.getFirstName(), user.getLastName(),
                new DatabaseEventListener() {

                    @Override
                    public void onSuccess(Object data) {
                        eventListener.onSuccess(user);
                    }

                    @Override
                    public void onFail(Object data) {
                        eventListener.onFail(data);
                    }
                });
    }

    /**
     * Gets the user.
     * @param username The username
     * @param eventListener The db event listener
     */
    public static void getUser(String username,
            final DatabaseEventListener eventListener) {
        DatabaseInterface database = DefaultDatabase.getDatabase();
        database.getUser(username, new DatabaseEventListener() {
            @Override
            public void onSuccess(Object data) {
                if (data == null) {
                    eventListener.onFail(null);
                } else {
                    eventListener.onSuccess(new User(
                            (HashMap<String, Object>) data));
                }
            }

            @Override
            public void onFail(Object data) {
                eventListener.onFail(null);
            }

        });
    }

    /**
     * Creates a new User.
     * 
     * @param username The username
     * @param firstName The first name
     * @param lastName The last name
     * @param email The email
     */
    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        accounts = new HashMap<String, AccountModel>();
    }

    /**
     * Constructor.
     * @param data The hashmap
     */
    public User(HashMap<String, Object> data) {
        username = (String) data.get("username");
        lastName = (String) data.get("last_name");
        firstName = (String) data.get("first_name");
        email = (String) data.get("email");
        accounts = new HashMap<String, AccountModel>();
        Object oAccounts = data.get("accounts");
        if (oAccounts instanceof HashMap<?, ?>) {
            HashMap<String, Object> accountList = (HashMap<String, Object>) oAccounts;
            for (Object o : accountList.values()) {
                HashMap<String, Object> map = (HashMap<String, Object>) o;
                AccountModel account = new AccountModel(map);
                String name = account.getFullName();
                accounts.put(name, account);
            }
        }
    }

    /**
     * Gets the accounts.
     * @return The accounts
     */
    public HashMap<String, AccountModel> getAccounts() {
        return accounts;
    }

    /**
     * Returns map representation.
     * @return The Hashmap
     */
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> temp = new HashMap<String, Object>();
        temp.put("username", username);
        temp.put("last_name", lastName);
        temp.put("first_name", firstName);
        temp.put("email", email);
        return temp;
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    /**
     * Gets the username.
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the first name.
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * @param lastName THe last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

     /**
      * Gets the email.
      * @return The email
      */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the username.
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
