package com.ninerupees.hamilton.presenter;

import android.text.TextUtils;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.LoginView;

/**
 * Manages the Login activity and database login needs.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class LoginPresenter implements ClickListener {

    private LoginView view;
    private DatabaseInterface model;

    /**
     * Instantiates the Login Presenter.
     * 
     * @param view
     *            The Login Activity details
     * @param model
     *            The database
     */
    public LoginPresenter(LoginView view, DatabaseInterface model) {
        this.view = view;
        this.model = model;
        view.addClickListener(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        view.resetErrors();

        // Store values at the time of the login attempt.
        final String username = view.getUsername();
        String password = view.getPassword();

        boolean cancel = false;
        // View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            view.setPasswordError(R.string.error_field_required);
            // focusView = mPasswordView;
            cancel = true;
        } else if (password.length() < 4) {
            view.setPasswordError(R.string.error_invalid_password);
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            view.setUsernameError(R.string.error_field_required);
            cancel = true;
        }

        if (!cancel) {
            view.showProgress(true);
            DatabaseInterface database = new DefaultDatabase();
            database.login(username, password, new DatabaseEventListener() {
                @Override
                public void onSuccess(Object data) {
                    // view.showProgress(false);
                    view.goToNextActivity(view.getUsername());
                }

                @Override
                public void onFail(Object data) {
                    view.showProgress(false);
                    view.setPasswordError(R.string.error_incorrect_password);
                }

            });
        }
    }

    @Override
    public void onClick() {
        attemptLogin();
    }

}
