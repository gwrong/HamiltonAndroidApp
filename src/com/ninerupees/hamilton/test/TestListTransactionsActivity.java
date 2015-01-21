package com.ninerupees.hamilton.test;

import java.util.HashMap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.model.data.AccountTransaction;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class TestListTransactionsActivity extends Activity {
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[] {
            "foo@example.com:hello", "bar@example.com:world" };

    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // Values for email and password at the time of the login attempt.
    private String mUsername;
    private String mAccountName;
    private String transactionsList;
    // UI references.
    private EditText mUsernameView;
    private EditText mAccountNameView;
    private View mListTransactionsView;
    private View mListTransactionsStatusView;
    private TextView mListTransactionsStatusMessageView;
    private TextView mTransactionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_list_transactions);

        // Set up the login form.
        mUsername = getIntent().getStringExtra(EXTRA_EMAIL);
        mUsernameView = (EditText) findViewById(R.id.test_transactions_username);
        mUsernameView.setText(mUsername);

        mAccountNameView = (EditText) findViewById(R.id.test_transactions_account_name);

        mListTransactionsView = findViewById(R.id.test_list_transactions_form);
        mListTransactionsStatusView = findViewById(R.id.test_list_transactions_status);
        mListTransactionsStatusMessageView = (TextView) findViewById(R.id.test_status_list_transactions_message);

        findViewById(R.id.test_list_transactions_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptLogin();
                    }
                });

        mTransactionsList = (TextView) findViewById(R.id.test_transactions_list);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        mUsername = mUsernameView.getText().toString();
        mAccountName = mAccountNameView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(mUsername)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mListTransactionsStatusMessageView
                    .setText(R.string.login_progress_signing_in);
            showProgress(true);
            DatabaseInterface database = new DefaultDatabase();
            database.getTransactions(mUsername, mAccountName,
                    new DatabaseEventListener() {
                        public void onSuccess(Object data) {
                            showProgress(false);
                            if (data == null) {
                                transactionsList = "You have no transactions";
                            } else {

                                transactionsList = data.getClass().toString()
                                        + "\n";
                                HashMap<String, Object> temp = (HashMap<String, Object>) data;
                                for (Object transactions : temp.values()) {
                                    HashMap<String, Object> transaction = (HashMap<String, Object>) transactions;
                                    AccountTransaction trans = new AccountTransaction(
                                            transaction);
                                    transactionsList += trans.toString() + "\n";
                                    // transactionsList +=
                                    // transactions.getClass().toString() +
                                    // "\n";
                                }
                                // String[] accountNames = (String[]) data;
                                // for(int i=0; i<accountNames.length; i++) {
                                // transactionsList += accountNames[i] + ", ";
                                // }
                            }
                            mTransactionsList.setText(transactionsList);

                            // goToNextActivity();
                        }

                        public void onFail(Object data) {
                            showProgress(false);
                            Context context = getApplicationContext();
                            CharSequence text = "Something went wrong. Huh.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text,
                                    duration);
                            toast.show();
                        }
                    });
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mListTransactionsStatusView.setVisibility(View.VISIBLE);
            mListTransactionsStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mListTransactionsStatusView
                                    .setVisibility(show ? View.VISIBLE
                                            : View.GONE);
                        }
                    });

            mListTransactionsView.setVisibility(View.VISIBLE);
            mListTransactionsView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mListTransactionsView
                                    .setVisibility(show ? View.GONE
                                            : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mListTransactionsStatusView.setVisibility(show ? View.VISIBLE
                    : View.GONE);
            mListTransactionsView
                    .setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
