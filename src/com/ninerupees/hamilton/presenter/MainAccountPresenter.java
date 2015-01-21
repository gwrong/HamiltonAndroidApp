package com.ninerupees.hamilton.presenter;

import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.view.ClickListener;
import com.ninerupees.hamilton.view.ItemClickListener;
import com.ninerupees.hamilton.view.MainAccountView;
import com.ninerupees.hamilton.view.RefreshListener;

/**
 * Presents the list of account.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class MainAccountPresenter implements ClickListener, RefreshListener,
        ItemClickListener {

    private MainAccountView view;
    private DatabaseInterface model;
    private String[] accountNames;

    /**
     * Instantiates the Main Account Presenter.
     * 
     * @param view
     *            The Login Activity details
     * @param model
     *            The database
     */
    public MainAccountPresenter(final MainAccountView view,
            DatabaseInterface model) {
        this.view = view;
        this.model = model;
        view.addClickListener(this);
        view.addRefreshListener(this);
        view.addItemClickListener(this);
        retrieveAccountNames();

    }

    /**
     * Gets the list of account names.
     */
    public void retrieveAccountNames() {
        model.getAccountNames(view.getUsername(), new DatabaseEventListener() {
            public void onSuccess(Object data) {

                // view.showProgress(false);
                if (data == null) {
                    accountNames = new String[0];
                } else {
                    accountNames = (String[]) data;
                }

                // System.out.println(accountsList);
                view.passListOfAccounts(accountNames);

                // goToNextActivity();
            }

            public void onFail(Object data) {

                // MainAccountPresenter.this.view.showProgress(false);
                // Context context = getApplicationContext();
                // CharSequence text = "Something went wrong. Huh.";
                // int duration = Toast.LENGTH_SHORT;
                // Toast toast = Toast.makeText(context, text, duration);
                // toast.show();
            }
        });
    }

    @Override
    public void onClick() {
        view.goToCreateAccountActivity(view.getUsername());
    }

    @Override
    public void onRefresh() {
        retrieveAccountNames();

    }

    @Override
    public void onItemClick() {
        view.goToAccountDetailsActivity(view.getAccountName());

    }

    /**
     * Called when the generate report button
     * is pressed.
     */
    public void onGenerateReport() {
        view.goToGenerateReportActivity();
    }
}
