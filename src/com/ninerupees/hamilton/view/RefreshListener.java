package com.ninerupees.hamilton.view;

/**
 * Interface for changes on refresh, such as moving from creating accounts and
 * back to the account listing; there needs to be a refresh in order to update
 * the list of accounts.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface RefreshListener {

    /**
     * Occurs when data has changed and must be updated.
     */
    void onRefresh();
}
