package com.ninerupees.hamilton.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ninerupees.hamilton.R;
import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.model.AccountModel;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.CreateAccountView;

/**
 * Presenter that deals with the creation of accounts.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class CreateAccountPresenter implements ClickListener {

    private CreateAccountView view;
    private DatabaseInterface model;
    private String username;

    /**
     * Constructor.
     * 
     * @param view
     *            Has the user input and
     * @param model
     *            The database
     */
    public CreateAccountPresenter(CreateAccountView view,
            DatabaseInterface model) {
        this.view = view;
        this.model = model;
        view.addClickListener(this);
        username = view.getUsername();
    }

    /**
     * Attempts to create the account.
     */
    public void attemptCreateAccount() {
        view.resetErrors();

        String accountName = view.getAccountName();
        String type = view.getAccountType();
        String notes = view.getAccountNotes();
        String displayName = view.getDisplayName();
        double interestRate = view.getInterestRate();

        boolean cancel = false;

        if (TextUtils.isEmpty(accountName)) {
            view.setNameError(R.string.error_field_required);
            // focusView = mPasswordView;
            cancel = true;
        }

        if (!cancel) {
            view.showProgress(true);

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // mAccountRegisterStatusMessageView.setText(R.string.login_progress_signing_in);
            AccountModel account = new AccountModel(type, accountName,
                    displayName, interestRate);
            model.createAccount(username, accountName, displayName,
                    interestRate, type, new DatabaseEventListener() {
                        public void onSuccess(Object data) {
                            // view.showProgress(false);
                            Context context = view.getContext();
                            CharSequence text = "Account:"
                                    + view.getAccountName() + " created!!!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text,
                                    duration);
                            toast.show();
                            view.goToNextActivity(username);
                        }

                        public void onFail(Object data) {
                            view.showProgress(false);

                            if (data == null) {
                                // I'm not sure when this is ever called.
                            } else {
                                // mPasswordView
                                view.setNameError(R.string.test_account_exists_error);
                            }
                        }
                    });
        } else {
            view.showProgress(false);
        }
    }

    @Override
    public void onClick() {
        attemptCreateAccount();
    }

}
