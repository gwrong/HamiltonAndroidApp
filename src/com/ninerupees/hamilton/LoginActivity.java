package com.ninerupees.hamilton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.LoginPresenter;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.LoginView;

/**
 * The activity/view for logging in.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class LoginActivity extends Activity implements LoginView {
    private ClickListener listener;
    // Not used, but necessary for MVP
    private LoginPresenter presenter;

    // Values for email and password at the time of the login attempt.
    private String mUsername;
    private String mPassword;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoginPresenter(this, new DefaultDatabase());

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mUsernameView.setText(mUsername);

        mPasswordView = (EditText) findViewById(R.id.password);

        mLoginFormView = findViewById(R.id.register_form);
        mLoginStatusView = findViewById(R.id.register_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.register_status_message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    /**
     * Called when the login button is pressed.
     * @param view The view
     */
    public void onLoginButtonPress(View view) {
        listener.onClick();
    }

    @Override
    public String getUsername() {
        return mUsernameView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void addClickListener(ClickListener list) {
        this.listener = list;

    }

    /**
     * Changed activity to Account screen.
     * @param username The username
     */
    public void goToNextActivity(String username) {

        Intent intent = new Intent(this, MainAccountActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();

    }

    @Override
    public void setUsernameError(int errorID) {
        mUsernameView.setError(getString(errorID));
        mUsernameView.requestFocus();
    }

    @Override
    public void setPasswordError(int errorID) {
        mPasswordView.setError(getString(errorID));
        mPasswordView.requestFocus();
    }

    @Override
    public void resetErrors() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
