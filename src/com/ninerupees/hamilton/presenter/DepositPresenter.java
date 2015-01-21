package com.ninerupees.hamilton.presenter;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.model.DepositModel;
import com.ninerupees.hamilton.view.DepositView;

/**
 * Manages the deposit action.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class DepositPresenter {
    private DepositView view;
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
    public DepositPresenter(DepositView view, DatabaseInterface model) {
        this.view = view;
        this.model = model;
        username = view.getUsername();
    }

    /**
     * Try to add the transaction.
     */
    public void attemptAddTransaction() {
        view.resetErrors();

        boolean cancel = false;

        // Store values at the time of the login attempt.
        final String username = view.getUsername();
        String accountName = view.getAccountName();
        double depositAmount = view.getDepositAmount();
        Date depositDate = view.getDepositDate();
        String source = view.getSource();
        double balance = 0;

        if (depositAmount == 0.0) {
            view.setDepositAmountError(R.string.error_zero_transaction);
            cancel = true;
        }

        if (TextUtils.isEmpty(source)) {
            view.setDepositSourceError(R.string.error_field_required);
            cancel = true;
        }

        if (!cancel) {
            view.showProgress(true);
            Calendar temp = Calendar.getInstance();
            temp.set(1995, 04, 26);

            DepositModel deposit = new DepositModel(depositAmount, depositDate,
                    new Date(), source);
            DatabaseInterface database = new DefaultDatabase();
            database.addTransactions(username, accountName, balance,
                    deposit.toMap(), new DatabaseEventListener() {
                        public void onSuccess(Object data) {
                            // view.showProgress(false);
                            if (data == null) {
                                // accountsList = "huh?";
                            } else {
                                // accountsList = "Account Created";
                                Context context = view.getContext();
                                CharSequence text = "Transaction Added";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text,
                                        duration);
                                toast.show();
                                view.goToAccountDetailsActivity();
                            }
                            // mAccountsList.setText(accountsList);
                        }

                        public void onFail(Object data) {
                            view.showProgress(false);
                            Context context = view.getContext();
                            CharSequence text = "No transactions present!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text,
                                    duration);
                            toast.show();
                        }
                    });
        }
    }

    /**
     * Called when the  complete deposit
     * button is pressed.
     */
    public void onCompleteDeposit() {
        attemptAddTransaction();
    }
}
