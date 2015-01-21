package com.ninerupees.hamilton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.model.AccountModel;
import com.ninerupees.hamilton.presenter.CreateAccountPresenter;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.CreateAccountView;

/**
 * Activity where the user can create a new account.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class CreateAccountActivity extends Activity implements
        OnItemSelectedListener, CreateAccountView {

    private ClickListener listener;
    private CreateAccountPresenter presenter;

    // Values for email and password at the time of the login attempt.
    private String mName;
    private String mNotes;
    private String mAccountType;
    private String username;
    private AccountModel account;

    // UI references.
    private EditText mNameView;
    private EditText mDisplayNameView;
    private EditText mNotesView;
    private EditText mInterestRateView;
    private View mCreateAccountFormView;
    private View mAccountStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getIntent().getStringExtra("username");

        // eventually fix the database in the presenter (Graham)
        presenter = new CreateAccountPresenter(this, new DefaultDatabase());

        setContentView(R.layout.activity_create_account);

        // Set up the login form.
        mNameView = (EditText) findViewById(R.id.account_name);
        // mNameView.setText(mName);
        mNameView.requestFocus();

        mNotesView = (EditText) findViewById(R.id.account_notes);
        // mNotesView.setText(mNotes);

        mInterestRateView = (EditText) findViewById(R.id.interest_rate);

        mDisplayNameView = (EditText) findViewById(R.id.account_notes);

        mCreateAccountFormView = findViewById(R.id.login_form);
        mAccountStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        Spinner spinner = (Spinner) findViewById(R.id.account_type);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.account_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.create_account, menu);
        return true;
    }

    /**
     * Alerts the presenter that the make account button was pressed to maintain
     * MVP.
     * 
     * @param view The view
     */
    public void onMakeAccountClick(View view) {
        listener.onClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
            long id) {
        mAccountType = (String) parent.getItemAtPosition(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        System.out.println("Nothing was selected");
    }

    /**
     * Shows the progress UI and hides the login form.
     * @param show Show var
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mAccountStatusView.setVisibility(View.VISIBLE);
            mAccountStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mAccountStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mCreateAccountFormView.setVisibility(View.VISIBLE);
            mCreateAccountFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mCreateAccountFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mAccountStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCreateAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public String getAccountType() {
        return mAccountType;
    }

    @Override
    public String getAccountName() {
        return mNameView.getText().toString();
    }

    @Override
    public String getAccountNotes() {
        return mNotesView.getText().toString();
    }

    @Override
    public void setNameError(int errorID) {
        mNameView.setError(getString(errorID));

    }

    @Override
    public String getDisplayName() {
        return mDisplayNameView.getText().toString();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void resetErrors() {
        mNameView.setError(null);
        mNotesView.setError(null);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void addClickListener(ClickListener list) {
        this.listener = list;
    }

    @Override
    public void goToNextActivity(String userName) {
        Intent intent = new Intent(this, MainAccountActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
        finish();

    }

    @Override
    public double getInterestRate() {
        return Double.parseDouble(mInterestRateView.getText().toString());
    }

}
