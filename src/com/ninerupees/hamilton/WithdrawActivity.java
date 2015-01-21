package com.ninerupees.hamilton;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.WithdrawPresenter;
import com.ninerupees.hamilton.view.WithdrawView;

/**
 * Activity that allows the user to add a new transaction.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class WithdrawActivity extends Activity implements WithdrawView {

    // Values for email and password at the time of the login attempt.
    private String accountName;
    private String username;
    private double balance;
    private WithdrawPresenter presenter;

    // UI references.
    private EditText mWithdrawalAmountView;
    private EditText mWithdrawalReasonView;
    private EditText mWithdrawalExpenseCategoryView;
    private DatePicker mWithdrawalDateView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new WithdrawPresenter(this, new DefaultDatabase());

        username = getIntent().getStringExtra("username");
        accountName = getIntent().getStringExtra("accountName");
        balance = getIntent().getDoubleExtra("balance", Integer.MAX_VALUE);

        setContentView(R.layout.activity_withdraw);

        TextView nameText = (TextView) findViewById(R.id.account_name_transaction);
        nameText.setText("Account name: " + accountName);

        // Set up the login form.

        mWithdrawalExpenseCategoryView = (EditText) findViewById(R.id.withdrawal_expense_category);

        mWithdrawalAmountView = (EditText) findViewById(R.id.withdrawal_amount);

        mWithdrawalReasonView = (EditText) findViewById(R.id.withdrawal_reason);

        mWithdrawalDateView = (DatePicker) findViewById(R.id.withdrawal_date);

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_transaction, menu);
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     * @param show The show param
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

    }

    @Override
    public void resetErrors() {
        mWithdrawalAmountView.setError(null);
        mWithdrawalReasonView.setError(null);
        mWithdrawalExpenseCategoryView.setError(null);
    }

    @Override
    public void setWithdrawalAmountError(int errorID) {
        mWithdrawalAmountView.setError(getString(errorID));
    }

    @Override
    public void setWithdrawalReasonError(int errorID) {
        mWithdrawalReasonView.setError(getString(errorID));
    }

    @Override
    public void setWithdrawalExpenseCategoryError(int errorID) {
        mWithdrawalExpenseCategoryView.setError(getString(errorID));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getReason() {
        return mWithdrawalReasonView.getText().toString();
    }

    /**
     * Called when the add transaction button is pressed in the activity; alerts
     * the presenter so that it can attempt to add the transaction.
     * 
     * @param view The view
     */
    public void onCompleteWithdrawalClick(View view) {
        presenter.onCompleteWithdrawal();
    }

    @Override
    public void goToAccountDetailsActivity() {
        finish();
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public double getWithdrawalAmount() {
        double amount = Double.parseDouble(mWithdrawalAmountView.getText()
                .toString());
        double formattedAmount = Double.parseDouble(new DecimalFormat("#.##")
                .format(amount));
        return formattedAmount;
    }

    @Override
    public Date getWithdrawalDate() {
        int day = mWithdrawalDateView.getDayOfMonth();
        int month = mWithdrawalDateView.getMonth();
        int year = mWithdrawalDateView.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = cal.getTime();
        return date;
    }

    @Override
    public double getPreviousBalance() {
        return balance;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public String getExpenseCategory() {
        return mWithdrawalExpenseCategoryView.getText().toString();
    }

}
