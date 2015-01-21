package com.ninerupees.hamilton.model.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The AccountTransaction class.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class AccountTransaction {
    private String transactionName;
    private double ammount;
    private Date transactionDate;
    private Date dateEntered;
    private String category;

    /**
     * Constructor.
     * @param data The data
     */
    public AccountTransaction(HashMap<String, Object> data) {
        transactionName = (String) data.get("transactionName");
        Object amt = data.get("amount");
        if (amt instanceof Long) {
            ammount = (Long) amt;
        } else {
            ammount = (Double) amt;
        }
        transactionDate = new Date((Long) data.get("transactionDate"));
        dateEntered = new Date((Long) data.get("dateEntered"));
        category = (String) data.get("category");
    }

    /**
     * Another constructor.
     * @param transName The transaction name
     * @param amount The amount
     * @param transDate The transaction date
     * @param dateEnteredIn The date entered
     * @param cat The category
     */
    public AccountTransaction(String transName, double amount,
            Date transDate, Date dateEnteredIn, String cat) {
        this.transactionName = transName;
        this.ammount = amount;
        this.transactionDate = transDate;
        this.dateEntered = dateEnteredIn;
        this.category = cat;
    }

    /**
     * toMap method.
     * @return The map of the transaction
     */
    public Map<String, Object> toMap() {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("transactionName", transactionName);
        temp.put("amount", ammount);
        temp.put("transactionDate", transactionDate);
        temp.put("dateEntered", dateEntered);
        temp.put("category", category);
        return temp;
    }

    /**
     * Returns the amount of the transaction.
     * @return The amount
     */
    public double getAmount() {
        return ammount;
    }

    /**
     * The toString method.
     * @return String representation
     */
    public String toString() {
        String temp = "Transaction(name: " + transactionName + ",amount: "
                + ammount + ",transactionDate: " + transactionDate.toString()
                + ",dateEntered: " + dateEntered.toString() + ",category: "
                + category + ")";
        return temp;
    }
}
