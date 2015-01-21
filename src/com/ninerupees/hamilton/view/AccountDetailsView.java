package com.ninerupees.hamilton.view;

import android.content.Context;
import android.view.View;

/**
 * The interface for the AccountDetailsActivity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface AccountDetailsView {

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Goes to the add withdrawal activity.
     */
    void goToAddWithdrawal();

    /**
     * Goes to the add deposit activity.
     */
    void goToAddDeposit();
    
    /**
     * Called when Deposit is chosen.
     * @param view The view
     */
    void onAddDepositClick(View view);

    /**
     * Called when Withdrawal is chosen.
     * @param view The view
     */
    void onAddWithdrawalClick(View view);

    /**
     * Gets the context.
     * @return Context
     */
    Context getApplicationContext();

    /**
     * Gets the account name.
     * @return Account name
     */
    String getAccountName();

    /**
     * Indicates progress to user.
     * @param b yes or no
     */
    void showProgress(boolean b);

    /**
     * Passes the transaction list.
     * @param transactionsList THe list of transactions
     */
    void passTransactionList(String transactionsList);

    /**
     * Adds the refresh listener.
     * @param listener The listener
     */
    void addRefreshListener(RefreshListener listener);

    /**
     * Passes the balance.
     * @param balance Balance
     */
    void passBalance(double balance);

}
