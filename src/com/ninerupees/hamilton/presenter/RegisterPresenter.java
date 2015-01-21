package com.ninerupees.hamilton.presenter;

import android.text.TextUtils;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.RegisterView;

/**
 * The presenter for registering which manages the Register Activity and the
 * communication with registering through the database.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class RegisterPresenter implements ClickListener {

    private RegisterView view;
    private DatabaseInterface model;

    /**
     * The register presenter holds a RegisterView and means of communicating
     * with the database.
     * @param view The view
     * @param model The database
     */
    public RegisterPresenter(RegisterView view, DatabaseInterface model) {
        this.view = view;
        this.model = model;
        view.addClickListener(this);
    }

    /**
     * Attempts to register the account specified by the login form. If there
     * are form errors (invalid email, missing fields, etc.), the errors are
     * presented and no actual login attempt is made.
     */
    public void attemptRegister() {

        view.resetErrors();

        // Store values at the time of the login attempt.
        String firstName = view.getFirstName();
        String lastName = view.getLastName();
        String username = view.getUsername();
        String password = view.getPassword();

        boolean cancel = false;
        // View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            view.setFirstNameError(R.string.error_field_required);
            // focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastName)) {
            view.setLastNameError(R.string.error_field_required);
            // focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            view.setPasswordError(R.string.error_field_required);
            // focusView = mPasswordView;
            cancel = true;
        } else if (password.length() < 4) {
            view.setPasswordError(R.string.error_password_too_short);
            // focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            view.setUsernameError(R.string.error_field_required);
            // focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            // focusView.requestFocus();
            // return false;
        } else {
            view.showProgress(true);
            model.addUser(username, password, null, firstName, lastName,
                    new DatabaseEventListener() {
                        @Override
                        public void onSuccess(Object data) {
                            // view.showProgress(false);
                            view.goToNextActivity(view.getUsername());
                        }

                        @Override
                        public void onFail(Object data) {
                            view.showProgress(false);

                            if (data == null) {
                                // I'm not sure when this is ever called.
                            } else {
                                // mPasswordView
                                view.setUsernameError(R.string.register_user_exists);
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick() {
        attemptRegister();
    }

}
