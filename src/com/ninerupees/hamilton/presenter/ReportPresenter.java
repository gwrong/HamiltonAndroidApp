package com.ninerupees.hamilton.presenter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.model.AbstractTransactionModel;
import com.ninerupees.hamilton.model.WithdrawalModel;
import com.ninerupees.hamilton.view.ReportView;

/**
 * The report presenter takes care of reports.
 * @author Nine Rupees
 * @version 1.0
 */
public class ReportPresenter {
    private ReportView view;
    private DatabaseInterface model;
    private String username;
    private List<String> accountsList;
    private List<WithdrawalModel> withdrawals;
    private boolean canContinue = false;
    int listenerCount = 0;

    /**
     * Constructor.
     * 
     * @param view
     *            The add transaction activity
     * @param model
     *            The database
     */
    public ReportPresenter(ReportView view, DatabaseInterface model) {
        this.view = view;
        this.model = model;
        username = view.getUsername();
        accountsList = view.getAccountsList();
        withdrawals = new ArrayList<WithdrawalModel>();
    }

    /**
     * Gets the withdrawal list.
     * @param start The start date
     * @param end The end date
     */
    public void retrieveWithdrawalList(final Date start, final Date end) {
        withdrawals = new ArrayList<WithdrawalModel>();
        for (String account : accountsList) {
            listenerCount++;
            SpendingReportDatabaseEventListener listener = new SpendingReportDatabaseEventListener(
                    start, end);
            model.getTransactions(username, account, listener);
        }

        System.out.println("Withdrawals size #2" + withdrawals.size());
    }

    /**
     * Generates the spending report.
     * @param start The start date
     * @param end The end date
     * @return The spending report
     */
    private String generateSpendingReport(Date start, Date end) {
        System.out.println("Withdrawals size #3" + withdrawals.size());
        HashMap<String, Double> categories = new HashMap<String, Double>();
        for (WithdrawalModel with : withdrawals) {
            if (categories.containsKey(with.getExpenseCategory())) {
                categories.put(
                        with.getExpenseCategory(),
                        categories.get(with.getExpenseCategory())
                                + with.getAmount());
            } else {
                categories.put(with.getExpenseCategory(), with.getAmount());
            }
        }

        List<String> expenses = new ArrayList<String>(categories.keySet());
        List<Double> amounts = new ArrayList<Double>(categories.values());
        double total = 0.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String report = "Your spending report\n";
        report += "From " + start.toString() + " to " + end.toString() + "\n";
        for (int i = 0; i < expenses.size(); i++) {
            report += expenses.get(i) + "\t\t\t\t\t\t\t"
                    + formatter.format(Math.abs(amounts.get(i))) + "\n";
            total += Math.abs(amounts.get(i));
        }
        report += "Total: " + formatter.format(total);
        return report;
    }

    /**
     * Called when the user presses generate report.
     */
    public void onGenerateReport() {
        Date start = view.getStartDate();
        Date end = view.getEndDate();
        retrieveWithdrawalList(start, end);
        // String report = generateSpendingReport(start, end);
        // view.passReport(report);
        // view.goToReportDisplayActivity();
    }

    /**
     * Goes ahead and passes the spending report.
     * @param start The start date
     * @param end The end date
     */
    public void continueSpendingReportGeneration(Date start, Date end) {
        String report = generateSpendingReport(start, end);
        view.passReport(report);
        view.goToReportDisplayActivity();
    }

    /**
     * DB event listener for spening reports.
     * @author Graham Wright
     * @version 1.0
     */
    private class SpendingReportDatabaseEventListener implements
            DatabaseEventListener {

        Date start;
        Date end;

        /**
         * Constructor.
         * @param start The start date
         * @param end The end date
         */
        public SpendingReportDatabaseEventListener(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void onSuccess(Object data) {
            if (data == null) {
                System.out.println("No transactions during report generation");
                listenerCount--;
                if (listenerCount == 0) {
                    continueSpendingReportGeneration(start, end);
                }
            } else {
                System.out.println("Entering search for withdrawals");
                HashMap<String, Object> temp = (HashMap<String, Object>) data;

                for (Object transactions : temp.values()) {
                    HashMap<String, Object> transaction = (HashMap<String, Object>) transactions;
                    AbstractTransactionModel trans = AbstractTransactionModel
                            .parse(transaction);
                    if (trans instanceof WithdrawalModel) {
                        if (trans.getTransactionDate().after(start)
                                && trans.getTransactionDate().before(end)) {
                            withdrawals.add((WithdrawalModel) trans);
                            System.out.println("Adding withdrawal: "
                                    + trans.toString());
                            // System.out.println("Withdrawals size " +
                            // withdrawals.size());
                        }
                    }
                }
            }
            listenerCount--;
            if (listenerCount == 0) {
                continueSpendingReportGeneration(start, end);
            }
        }

        @Override
        public void onFail(Object data) {
            System.out.println("ReportPresenter failed");
            listenerCount--;
            if (listenerCount == 0) {
                continueSpendingReportGeneration(start, end);
            }
        }
    }
}
