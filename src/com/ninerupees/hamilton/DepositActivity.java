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
import com.ninerupees.hamilton.presenter.DepositPresenter;
import com.ninerupees.hamilton.view.DepositView;

/**
 * The activity for making a deposit.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class DepositActivity extends Activity implements DepositView {

    // Values for email and password at the time of the login attempt.
    private String accountName;
    private String username;
    private DepositPresenter presenter;

    // UI references.
    private EditText mDepositAmountView;
    private EditText mDepositSourceView;
    private DatePicker mDepositDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new DepositPresenter(this, new DefaultDatabase());

        username = getIntent().getStringExtra("username");
        accountName = getIntent().getStringExtra("accountName");

        setContentView(R.layout.activity_deposit);

        TextView nameText = (TextView) findViewById(R.id.account_name_transaction);
        nameText.setText("Account name: " + accountName);

        // Set up the login form.

        mDepositAmountView = (EditText) findViewById(R.id.deposit_amount);

        mDepositSourceView = (EditText) findViewById(R.id.deposit_reason);

        mDepositDateView = (DatePicker) findViewById(R.id.deposit_date);

    }

    @Override
    //On create
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_transaction, menu);
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     * @param show Show var
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

    }

    @Override
    public void resetErrors() {
        mDepositAmountView.setError(null);
        mDepositSourceView.setError(null);
    }

    @Override
    public void setDepositAmountError(int errorID) {
        mDepositAmountView.setError(getString(errorID));
    }

    @Override
    public void setDepositSourceError(int errorID) {
        mDepositSourceView.setError(getString(errorID));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getSource() {
        return mDepositSourceView.getText().toString();
    }

    /**
     * Called when the add transaction button is pressed in the activity; alerts
     * the presenter so that it can attempt to add the transaction.
     * 
     * @param view The view
     */
    public void onCompleteDepositClick(View view) {
        presenter.onCompleteDeposit();
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public double getDepositAmount() {
        double amount = Double.parseDouble(mDepositAmountView.getText()
                .toString());
        double formattedAmount = Double.parseDouble(new DecimalFormat("#.##")
                .format(amount));
        return formattedAmount;
    }

    @Override
    public void goToAccountDetailsActivity() {
        finish();
    }

    @Override
    public Date getDepositDate() {
        int day = mDepositDateView.getDayOfMonth();
        int month = mDepositDateView.getMonth();
        int year = mDepositDateView.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = cal.getTime();
        return date;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}
