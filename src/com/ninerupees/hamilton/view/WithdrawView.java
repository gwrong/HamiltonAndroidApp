package com.ninerupees.hamilton.view;

import java.util.Date;

import android.content.Context;

/**
 * The interface for the Withdraw Activity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface WithdrawView {

    /**
     * Resets the input box errors.
     */
    void resetErrors();

    /**
     * Gets the user's decalred username.
     * @return The username of the User
     */
    String getUsername();

    /**
     * Gets the account name.
     * @return The account name of the user
     */
    String getAccountName();

    /**
     * Shows the progress to the user.
     * @param b Yes or no
     */
    void showProgress(boolean b);

    /**
     * Gets the amount to withdraw.
     * @return the withdrawal amount of the transaction
     */
    double getWithdrawalAmount();

    /**
     * Sets an error on the withdrawal amount.
     * @param errorID error
     */
    void setWithdrawalAmountError(int errorID);

    /**
     * Sets an error on the wtihdrawal reason.
     * @param errorID error
     */
    void setWithdrawalReasonError(int errorID);

    /**
     * Sets an error on the expense category.
     * @param errorID error
     */
    void setWithdrawalExpenseCategoryError(int errorID);

    /**
     * Advance to the Account Details Activity.
     */
    void goToAccountDetailsActivity();

    /**
     * Gets the withdrawal date.
     * @return The withdrawal date
     */
    Date getWithdrawalDate();

    /**
     * Gets the reason for withdrawal.
     * @return The withdrawal reason
     */
    String getReason();

    /**
     * Gets the expense category for withdrawal.
     * @return The expese category
     */
    String getExpenseCategory();

    /**
     * Gets the context.
     * @return the context
     */
    Context getContext();

    /**
     * Gets the previous balance.
     * @return The previous balance
     */
    double getPreviousBalance();
}
