package com.ninerupees.hamilton.presenter;

import java.util.HashMap;

import android.content.Context;
import android.widget.Toast;

import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.model.AbstractTransactionModel;
import com.ninerupees.hamilton.view.AccountDetailsView;
import com.ninerupees.hamilton.view.RefreshListener;

/**
 * Manages Account Details.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class AccountDetailsPresenter implements RefreshListener {
    private AccountDetailsView view;
    private DatabaseInterface model;
    private String username;

    /**
     * Constructor.
     * 
     * @param view
     *            The add transaction activity
     * @param model
     *            The database
     */
    public AccountDetailsPresenter(AccountDetailsView view,
            DatabaseInterface model) {
        this.view = view;
        this.model = model;
        view.addRefreshListener(this);
        username = view.getUsername();
        retrieveTransactionsList();
    }

    /**
     * Gathers the transactions list.
     */
    public void retrieveTransactionsList() {

        String username = view.getUsername();
        String accountName = view.getAccountName();

        view.showProgress(true);
        DatabaseInterface database = new DefaultDatabase();
        database.getTransactions(username, accountName,
                new DatabaseEventListener() {
                    @SuppressWarnings("unchecked")
                    public void onSuccess(Object data) {
                        view.showProgress(false);
                        String transactionsList;
                        double balance = 0;
                        if (data == null) {
                            transactionsList = "You have no transactions";
                        } else {

                            // transactionsList = data.getClass().toString() +
                            // "\n";
                            transactionsList = "";
                            HashMap<String, Object> temp = (HashMap<String, Object>) data;

                            for (Object transactions : temp.values()) {
                                HashMap<String, Object> transaction = (HashMap<String, Object>) transactions;
                                AbstractTransactionModel trans = AbstractTransactionModel
                                        .parse(transaction);
                                balance += trans.getAmount();
                                transactionsList += trans.toString() + "\n\n";
                                // transactionsList +=
                                // transactions.getClass().toString() + "\n";
                            }
                            // String[] accountNames = (String[]) data;
                            // for(int i=0; i<accountNames.length; i++) {
                            // transactionsList += accountNames[i] + ", ";
                            // }
                        }
                        view.passBalance(balance);
                        view.passTransactionList(transactionsList);
                        System.out.println("The transactions were passed");
                        // goToNextActivity();
                    }

                    public void onFail(Object data) {
                        // view.showProgress(false);
                        Context context = view.getApplicationContext();
                        CharSequence text = "You currently have no transactions!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
    }

    @Override
    public void onRefresh() {
        retrieveTransactionsList();
    }

    /**
     * Called when withdraw is called.
     */
    public void onAddWithdrawalClick() {
        view.goToAddWithdrawal();
        ;
    }

    /**
     * Called when user chooses to deposit.
     */
    public void onAddDepositClick() {
        view.goToAddDeposit();
    }

}
