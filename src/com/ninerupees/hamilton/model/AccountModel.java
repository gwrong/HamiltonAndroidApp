package com.ninerupees.hamilton.model;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.annotation.TargetApi;
import android.os.Build;

import com.ninerupees.hamilton.database.DatabaseEventListener;
import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.database.DefaultDatabase;

/**
 * The account model.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AccountModel {

    private String type;
    private String fullName;
    private String displayName;
    private String username;
    private double balance;
    private double monthlyInterestRate;
    private Deque<AbstractTransactionModel> transactionHistory;

    /**
     * Constructor.
     * @param type Account type
     * @param fullName Account fname
     * @param displayName Account display name
     * @param monthlyInterestRate Account interest rate
     */
    public AccountModel(String type, String fullName, String displayName,
            double monthlyInterestRate) {
        this.type = type;
        this.fullName = fullName;
        this.displayName = displayName;
        this.balance = 0;
        this.monthlyInterestRate = monthlyInterestRate;
        transactionHistory = new LinkedList<AbstractTransactionModel>();
    }

    /**
     * Parses the double.
     * @param o The object to parse
     * @return The Double
     */
    private Double parseDouble(Object o) {
        if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof String) {
            return Double.parseDouble((String) o);
        } else if (o instanceof Long) {
            return ((Long) (o)).doubleValue();
        } else {
            return 0.0;
        }
    }

    /**
     * Constructor.
     * @param data The hashmap
     */
    @SuppressWarnings("unchecked")
    public AccountModel(HashMap<String, Object> data) {
        type = (String) data.get("type");
        fullName = (String) data.get("fullName");
        displayName = (String) data.get("displayName");
        username = (String) data.get("username");
        balance = parseDouble(data.get("balance"));

        monthlyInterestRate = parseDouble(data.get("monthlyInterestRate"));
        transactionHistory = new LinkedList<AbstractTransactionModel>();
        HashMap<String, Object> transactionList = (HashMap<String, Object>) data
                .get("transactions");
        for (Object o : transactionList.values()) {
            HashMap<String, Object> map = (HashMap<String, Object>) o;
            AbstractTransactionModel transaction = AbstractTransactionModel.parse(map);
            transactionHistory.add(transaction);
        }
    }

    /**
     * To map function.
     * @return The map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("type", type);
        temp.put("fullName", fullName);
        temp.put("displayName", displayName);
        temp.put("username", username);
        temp.put("balance", balance);
        temp.put("monthlyInterestRate", monthlyInterestRate);
        return temp;
    }

    /**
     * Updates transactions.
     */
    private void updateTransactions() {
        DatabaseInterface database = new DefaultDatabase();
        database.getTransactions(username, fullName,
                new DatabaseEventListener() {
                    public void onSuccess(Object data) {
                        if (data != null) {
                            HashMap<String, Object> temp = (HashMap<String, Object>) data;
                            for (Object transactions : temp.values()) {
                                HashMap<String, Object> transaction = (HashMap<String, Object>) transactions;
                                AbstractTransactionModel trans = AbstractTransactionModel
                                        .parse(transaction);
                                transactionHistory.add(trans);
                            }
                        }
                    }

                    public void onFail(Object data) {

                    }
                });
    }

    /**
     * Sets the display name.
     * @param dispName New display name
     */
    public void setDisplayName(String dispName) {
        this.displayName = dispName;
    }

    /**
     * Sets the username.
     * @param uname THe username
     */
    public void setUsername(String uname) {
        this.username = uname;
    }

    /**
     * Gets the type.
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the full name.
     * @return The full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     * @param fName new full name
     */
    public void setFullName(String fName) {
        this.fullName = fName;
    }

    /**
     * Gets the balance.
     * @return The balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the interest rate.
     * @return The interest rate
     */
    public double getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    /**
     * Sets the interest rate.
     * @param monthlyIntRate interest rate
     */
    public void setMonthlyInterestRate(double monthlyIntRate) {
        this.monthlyInterestRate = monthlyIntRate;
    }

    /**
     * Gets the history.
     * @return The Deque of transaction
     */
    public Deque<AbstractTransactionModel> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Gets the display name.
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Adds the transaction.
     * @param transaction The transaction
     * @param event DB event listener
     */
    public void addTransaction(AbstractTransactionModel transaction,
            DatabaseEventListener event) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Cannot send in null transaction");
        }
        if (balance + transaction.getAmount() < 0) {
            event.onFail(new Double(balance));
        } else {
            balance += transaction.getAmount();
            transactionHistory.addFirst(transaction);

            DatabaseInterface database = new DefaultDatabase();
            database.addTransactions(username, fullName, balance,
                    transaction.toMap(), event);
        }

    }

}
