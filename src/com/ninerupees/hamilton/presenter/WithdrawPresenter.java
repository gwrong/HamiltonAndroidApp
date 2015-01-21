package com.ninerupees.hamilton.presenter;

import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.model.WithdrawalModel;
import com.ninerupees.hamilton.view.WithdrawView;

/**
 * Manages withdrawal.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class WithdrawPresenter {
    private WithdrawView view;
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
    public WithdrawPresenter(WithdrawView view, DatabaseInterface model) {
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
        double withdrawalAmount = view.getWithdrawalAmount();
        String withdrawalReason = view.getReason();
        String withdrawalExpenseCategory = view.getExpenseCategory();
        Date withdrawalDate = view.getWithdrawalDate();
        String reason = view.getReason();
        String expenseCategory = view.getExpenseCategory();
        double balance = view.getPreviousBalance();

        if (withdrawalAmount == 0.0) {
            view.setWithdrawalAmountError(R.string.error_zero_transaction);
            cancel = true;
        }

        if (TextUtils.isEmpty(withdrawalReason)) {
            view.setWithdrawalReasonError(R.string.error_field_required);
            cancel = true;
        }

        if (TextUtils.isEmpty(withdrawalExpenseCategory)) {
            view.setWithdrawalExpenseCategoryError(R.string.error_field_required);
            cancel = true;
        }

        // View focusView = null;

        if (!cancel) {
            view.showProgress(true);
            //Calendar temp = Calendar.getInstance();
            // temp.set(1995, 04, 26);

            WithdrawalModel withdrawal = new WithdrawalModel(withdrawalAmount,
                    withdrawalDate, new Date(), reason, expenseCategory);
            DatabaseInterface database = new DefaultDatabase();
            database.addTransactions(username, accountName, balance,
                    withdrawal.toMap(), new DatabaseEventListener() {
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
                            if (data.equals("CannotWithdraw")) {
                                view.setWithdrawalAmountError(R.string.withdrawal_negative_balance);
                            } else {
                                view.showProgress(false);
                                Context context = view.getContext();
                                CharSequence text = "No transactions present!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text,
                                        duration);
                                toast.show();
                            }
                        }
                    });
        }
    }

    /**
     * Called when the user chooses
     * complete withdrawal.
     */
    public void onCompleteWithdrawal() {
        attemptAddTransaction();
    }
}
