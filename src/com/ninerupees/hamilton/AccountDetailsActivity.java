package com.ninerupees.hamilton;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.AccountDetailsPresenter;
import com.ninerupees.hamilton.view.AccountDetailsView;
import com.ninerupees.hamilton.view.RefreshListener;

/**
 * The activity for viewing account details.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class AccountDetailsActivity extends Activity implements
        AccountDetailsView {

    private String accountName;
    private String username;
    private double balance;
    private RefreshListener refreshListener;
    private AccountDetailsPresenter presenter;
    private String transactionsList;
    private TextView transactionsText;
    private TextView accountBalance;

    // private TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        accountName = intent.getStringExtra("accountName");
        presenter = new AccountDetailsPresenter(this, new DefaultDatabase());
        System.out.println("starting account Details");

        System.out.println("retrieved intent info: " + accountName);
        // TextView textView = new TextView(this);
        // textView.setTextSize(40);
        // textView.setText(accountName);
        // findAccountName();

        TextView nameText = (TextView) findViewById(R.id.account_name);
        nameText.setText(accountName);
        System.out.println("set account name");

        System.out.println("The transactions list is: " + transactionsList);
        transactionsText = (TextView) findViewById(R.id.transactions_list);
        transactionsText.setMovementMethod(ScrollingMovementMethod
                .getInstance());
        // transactionsText.setText(transactionsList);

        accountBalance = (TextView) findViewById(R.id.account_balance);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListener.onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_details, menu);
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void onAddWithdrawalClick(View view) {
        presenter.onAddWithdrawalClick();
    }

    @Override
    public void onAddDepositClick(View view) {
        presenter.onAddDepositClick();
    }

    @Override
    public void goToAddWithdrawal() {
        Intent intent = new Intent(this, WithdrawActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("accountName", accountName);
        intent.putExtra("balance", balance);
        startActivity(intent);

    }

    @Override
    public void goToAddDeposit() {
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("accountName", accountName);
        startActivity(intent);

    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public void showProgress(boolean b) {
        // TODO Auto-generated method stub
    }

    @Override
    public void passTransactionList(String transactions) {
        this.transactionsList = transactions;
        transactionsText.setText(transactionsList);
    }

    @Override
    public void addRefreshListener(RefreshListener listener) {
        refreshListener = listener;
    }

    @Override
    public void passBalance(double bal) {
        this.balance = bal;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(balance);
        accountBalance.setText("Account Balance: " + moneyString);
    }

}
