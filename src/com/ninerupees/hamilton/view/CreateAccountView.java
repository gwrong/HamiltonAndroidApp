package com.ninerupees.hamilton.view;

import android.content.Context;

/**
 * The interface for the CreateAccountActivity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface CreateAccountView {
    /**
     * Gets the account name.
     * @return The account name
     */
    String getAccountName();

    /**
     * Gets the account notes.
     * @return The account notes
     */
    String getAccountNotes();

    /**
     * Gets the account type.
     * @return The account type
     */
    String getAccountType();

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Gets the interest rate.
     * @return The interest rate
     */
    double getInterestRate();

    /**
     * Sets an error on the name.
     * @param errorID error
     */
    void setNameError(int errorID);

    /**
     * Resets all errors.
     */
    void resetErrors();

    /**
     * Adds the click listener.
     * @param listener The listener
     */
    void addClickListener(ClickListener listener);

    /**
     * Advances to the next activity.
     * @param username The username
     */
    void goToNextActivity(String username);

    /**
     * Gets the context.
     * @return The context
     */
    Context getContext();

    /**
     * Shows the progress to the user.
     * @param show Yes or no
     */
    void showProgress(boolean show);

    /**
     * Gets the display name for the account.
     * @return The display name
     */
    String getDisplayName();
}
