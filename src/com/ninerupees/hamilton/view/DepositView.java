package com.ninerupees.hamilton.view;

import java.util.Date;

import android.content.Context;

/**
 * The interface for the DepositActivity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface DepositView {
    
    /**
     * Resets all errors.
     */
    void resetErrors();

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Gets the account name.
     * @return The account name
     */
    String getAccountName();

    /**
     * Shows the progress to the user.
     * @param b Yes or no
     */
    void showProgress(boolean b);

    /**
     * Gets the deposit amount.
     * @return The deposit amount
     */
    double getDepositAmount();

    /**
     * Sets an error on the deposit amount.
     * @param errorID error
     */
    void setDepositAmountError(int errorID);

    /**
     * Sets an error on the deposit source field.
     * @param errorID error
     */
    void setDepositSourceError(int errorID);

    /**
     * Advance to the account details activity.
     */
    void goToAccountDetailsActivity();

    /**
     * Get the deposit date.
     * @return Deposit date
     */
    Date getDepositDate();

    /**
     * Gets the source of the deposit.
     * @return The deposit source
     */
    String getSource();

    /**
     * Get the context.
     * @return Context
     */
    Context getContext();
}
