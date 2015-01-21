package com.ninerupees.hamilton.view;

/**
 * Defines communication with the Register Activity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface RegisterView {

    /**
     * Gets the first name.
     * @return The first name
     */
    String getFirstName();

    /**
     * Gets the last name.
     * @return The last name
     */
    String getLastName();

    /**
     * Gets the user's username.
     * @return The user's username
     */
    String getUsername();

    /**
     * Gets the password.
     * @return The password
     */
    String getPassword();

    /**
     * Sets the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setFirstNameError(int errorID);

    /**
     * Sets the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setLastNameError(int errorID);

    /**
     * Sets the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setUsernameError(int errorID);

    /**
     * Sets the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setPasswordError(int errorID);

    /**
     * Resets errors on all input boxes.
     */
    void resetErrors();

    /**
     * Assigns a click listener to this view.
     * 
     * @param listener
     *            The listener to be assigned should be the presenter
     */
    void addClickListener(ClickListener listener);

    /**
     * Move to the next activity, passing along the user's username.
     * 
     * @param username The username
     */
    void goToNextActivity(String username);

    /**
     * Provide an animation for signing in.
     * 
     * @param show Yes or no
     */
    void showProgress(boolean show);
}
