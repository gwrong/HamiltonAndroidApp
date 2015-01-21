package com.ninerupees.hamilton.view;

/**
 * The interface for the MainAccountActivity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface MainAccountView {
    
    /**
     * Adds the click listener.
     * @param listener The listener
     */
    void addClickListener(ClickListener listener);

    /**
     * Adds the refresh listener.
     * @param listener The listener
     */
    void addRefreshListener(RefreshListener listener);

    /**
     * Advance to create an account.
     * @param username The username
     */
    void goToCreateAccountActivity(String username);

    /**
     * Advance to look at an account.
     * @param accountName The name of the account
     */
    void goToAccountDetailsActivity(String accountName);

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Gets the account name.
     * @return The account names
     */
    String getAccountName();

    /**
     * Pass the accounts list.
     * @param accountsList List of accounts
     */
    void passListOfAccounts(String[] accountsList);

    /**
     * Adds the item click listener.
     * @param listener The listener
     */
    void addItemClickListener(ItemClickListener listener);

    /**
     * Advance to the generate report Activity.
     */
    void goToGenerateReportActivity();
}
