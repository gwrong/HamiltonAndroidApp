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
import com.ninerupees.hamilton.presenter.RegisterPresenter;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.RegisterView;

/**
 * The activity for registering a new user.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class RegisterActivity extends Activity implements RegisterView {

    private ClickListener listener;
    // Required for MVP
    private RegisterPresenter presenter;

    // Values for email and password at the time of the login attempt.
    private String mFirstName;
    private String mLastName;
    private String mUsername;
    private String mPassword;

    // UI references.
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mRegisterUsernameView;
    private EditText mRegisterPasswordView;
    private View mRegisterFormView;
    private View mRegisterStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new RegisterPresenter(this, new DefaultDatabase());

        setContentView(R.layout.activity_register);

        mFirstNameView = (EditText) findViewById(R.id.first_name);
        mFirstNameView.setText(mFirstName);

        mLastNameView = (EditText) findViewById(R.id.last_name);
        mLastNameView.setText(mLastName);

        mRegisterUsernameView = (EditText) findViewById(R.id.username);
        mRegisterUsernameView.setText(mUsername);

        mRegisterPasswordView = (EditText) findViewById(R.id.password);

        mRegisterFormView = findViewById(R.id.register_form);
        mRegisterStatusView = findViewById(R.id.register_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.register_status_message);

        /*
         * fairly certain we don't need this mPasswordView
         * .setOnEditorActionListener(new TextView.OnEditorActionListener() {
         * 
         * @Override public boolean onEditorAction(TextView textView, int id,
         * KeyEvent keyEvent) { onRegisterButtonPress(null); return true; } });
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    /**
     * Alerts the presenter when the register button is pressed (since the
     * presenter does all the coordination).
     * 
     * @param view
     *            The view to be changed
     */
    public void onRegisterButtonPress(View view) {
        listener.onClick();
    }

    @Override
    public String getFirstName() {
        return mFirstNameView.getText().toString();
    }

    @Override
    public String getLastName() {
        return mLastNameView.getText().toString();
    }

    @Override
    public String getUsername() {
        return mRegisterUsernameView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mRegisterPasswordView.getText().toString();
    }

    @Override
    public void addClickListener(ClickListener list) {
        this.listener = list;

    }

    @Override
    public void goToNextActivity(String username) {
        Intent intent = new Intent(this, MainAccountActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    @Override
    public void setUsernameError(int errorID) {
        mRegisterUsernameView.setError(getString(errorID));
        mRegisterUsernameView.requestFocus();
    }

    @Override
    public void setPasswordError(int errorID) {
        mRegisterPasswordView.setError(getString(errorID));
        mRegisterPasswordView.requestFocus();
    }

    @Override
    public void setFirstNameError(int errorID) {
        mFirstNameView.setError(getString(errorID));
        mFirstNameView.requestFocus();
    }

    @Override
    public void setLastNameError(int errorID) {
        mLastNameView.setError(getString(errorID));
        mLastNameView.requestFocus();
    }

    @Override
    public void resetErrors() {
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mRegisterUsernameView.setError(null);
        mRegisterPasswordView.setError(null);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mRegisterStatusView.setVisibility(View.VISIBLE);
            mRegisterStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mRegisterFormView.setVisibility(View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
