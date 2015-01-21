package com.ninerupees.hamilton.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.model.AccountModel;
import com.ninerupees.hamilton.model.User;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class TestListAccountsActivity extends Activity {
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
    private String accountsList;
    // UI references.
    private EditText mUsernameView;
    private View mListAccountsView;
    private View mListAccountsStatusView;
    private TextView mListAccountsStatusMessageView;
    private TextView mAccountsList;

    private User tempUser;
    private AccountModel tempAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_list_accounts);

        // Set up the login form.
        mUsername = getIntent().getStringExtra(EXTRA_EMAIL);
        mUsernameView = (EditText) findViewById(R.id.test_username);
        mUsernameView.setText(mUsername);

        mListAccountsView = findViewById(R.id.test_list_account_form);
        mListAccountsStatusView = findViewById(R.id.test_list_account_status);
        mListAccountsStatusMessageView = (TextView) findViewById(R.id.test_status_list_account_message);

        findViewById(R.id.test_list_accounts_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptLogin();
                    }
                });

        mAccountsList = (TextView) findViewById(R.id.test_accounts_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.test_register_account, menu);
        return true;
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
            mListAccountsStatusMessageView
                    .setText(R.string.login_progress_signing_in);

            showProgress(true);
            /*
             * User.getUser(mUsername, new DatabaseEventListener() {
             * 
             * @Override public void onSuccess(Object data) { tempUser =
             * (User)data; mAccountsList.setText(tempUser.toString());
             * showProgress(false); }
             * 
             * @Override public void onFail(Object data) { // TODO
             * Auto-generated method stub
             * 
             * } });
             */
            /*
             * User.registerUser(new User("ashaw426", "Albert", "Shaw",
             * "ashaw596@gmail.com"), "school", new DatabaseEventListener() {
             * 
             * @Override public void onSuccess(Object data) { tempUser = (User)
             * data; mAccountsList.setText(tempUser.toString());
             * showProgress(false); }
             * 
             * @Override public void onFail(Object data) {
             * mAccountsList.setText("something went wrong");
             * showProgress(false); }
             * 
             * });
             */
            tempUser = new User("ashaw426", "Albert", "Shaw",
                    "ashaw596@gmail.com");
            tempUser.createAccount(new AccountModel("savings",
                    "SavingsAccount", "save", 1.5),
                    new DatabaseEventListener() {

                        @Override
                        public void onSuccess(Object data) {
                            tempAccount = (AccountModel) data;
                            mAccountsList.setText(tempAccount.toString());
                            showProgress(false);
                        }

                        @Override
                        public void onFail(Object data) {
                            showProgress(false);
                            mAccountsList.setText("huh?");
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

            mListAccountsStatusView.setVisibility(View.VISIBLE);
            mListAccountsStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mListAccountsStatusView
                                    .setVisibility(show ? View.VISIBLE
                                            : View.GONE);
                        }
                    });

            mListAccountsView.setVisibility(View.VISIBLE);
            mListAccountsView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mListAccountsView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mListAccountsStatusView.setVisibility(show ? View.VISIBLE
                    : View.GONE);
            mListAccountsView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
