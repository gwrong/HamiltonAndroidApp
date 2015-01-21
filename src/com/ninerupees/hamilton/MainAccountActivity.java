package com.ninerupees.hamilton;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.MainAccountPresenter;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.ItemClickListener;
import com.ninerupees.hamilton.view.MainAccountView;
import com.ninerupees.hamilton.view.RefreshListener;

/**
 * The main account activity for viewing accounts.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class MainAccountActivity extends Activity implements MainAccountView {

    // Changed the account name key to "accountName"
    public static final String ACCOUNT_NAME = "com.ninerupees.hamilton.ACCOUNT_NAME";

    private String username;
    private ClickListener listener;
    private RefreshListener refreshListener;
    private ItemClickListener itemClickListener;
    private MainAccountPresenter presenter;
    private TextView mAccountsList;
    private ArrayList<String> accountsList;
    private ListView listView;
    private Spinner reportView;
    private String itemValue;
    private String reportType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        listView = (ListView) findViewById(R.id.list);
        // mAccountsList = (TextView) findViewById(R.id.accounts_list);
        username = getIntent().getStringExtra("username");
        presenter = new MainAccountPresenter(this, new DefaultDatabase());

        reportView = (Spinner) findViewById(R.id.report_type);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.report_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        reportView.setAdapter(adapter);

        reportType = (String) reportView.getItemAtPosition(0);

        reportView.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
                reportType = (String) parent.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do Nothing
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_account, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListener.onRefresh();
    }

    /**
     * Called when the generate report button is pressed.
     * @param view The view
     */
    public void onGenerateReportClick(View view) {
        presenter.onGenerateReport();
    }

    /**
     * Changes activities.
     */
    public void goToGenerateReportActivity() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("reportType", reportType);
        intent.putStringArrayListExtra("accountsList", accountsList);
        startActivity(intent);
    }

    /**
     * Changes activities.
     * @param username The username
     */
    public void goToCreateAccountActivity(String username) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void addClickListener(ClickListener list) {
        this.listener = list;

    }

    /**
     * Called when the create account button is pressed.
     * @param view The view
     */
    public void onCreateAccountButtonClick(View view) {
        listener.onClick();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void passListOfAccounts(String[] accounts) {
        accountsList = new ArrayList<String>(Arrays.asList(accounts));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                accounts);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                //
                // ListView Clicked item value, store as instance var
                itemValue = (String) listView.getItemAtPosition(position);
                // Let the presenter know that an account has been chosen
                itemClickListener.onItemClick();
            }

        });
    }

    @Override
    public void addRefreshListener(RefreshListener list) {
        refreshListener = list;
    }

    @Override
    public void goToAccountDetailsActivity(String accountName) {
        Intent toAccountDetails = new Intent(getBaseContext(),
                AccountDetailsActivity.class);
        // System.out.println("got application context");
        toAccountDetails.putExtra("accountName", itemValue);
        toAccountDetails.putExtra("username", username);
        startActivity(toAccountDetails);

    }

    @Override
    public String getAccountName() {
        return itemValue;
    }

    @Override
    public void addItemClickListener(ItemClickListener list) {
        this.itemClickListener = list;

    }

}
