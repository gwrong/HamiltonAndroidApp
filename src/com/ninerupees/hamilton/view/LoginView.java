package com.ninerupees.hamilton.view;

/**
 * The interface for the LoginActivity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface LoginView {

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Gets the password.
     * @return The password
     */
    String getPassword();

    /**
     * Applies the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setUsernameError(int errorID);

    /**
     * Applies the specified error to the input box.
     * 
     * @param errorID
     *            The int value of the error
     */
    void setPasswordError(int errorID);

    /**
     * Resets all input box errors.
     */
    void resetErrors();

    /**
     * Assigns the clicklistener to this view.
     * 
     * @param listener
     *            The listener to be assigned
     */
    void addClickListener(ClickListener listener);

    /**
     * Move to the next activity, passing the username.
     * 
     * @param username The username
     */
    void goToNextActivity(String username);

    /**
     * Display animation for logging in.
     * 
     * @param show Yes or no
     */
    void showProgress(boolean show);
}
