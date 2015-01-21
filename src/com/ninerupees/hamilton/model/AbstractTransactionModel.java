package com.ninerupees.hamilton.model;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The abstract transaction model.
 * @author Nine Rupees
 * @version 1.0
 */
public abstract class AbstractTransactionModel {

    private double amount;
    private Date transactionDate;
    private Date enterDate;

    /**
     * Constructor.
     * @param amount The amount
     * @param transactionDate The transaction date
     * @param enterDate The date entered
     */
    public AbstractTransactionModel(double amount, Date transactionDate, Date enterDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.enterDate = enterDate;
    }

    /**
     * Constructor.
     * @param data THe transaction
     * @return The TransactionModel
     */
    public static AbstractTransactionModel parse(HashMap<String, Object> data) {
        Object amt = data.get("amount");
        double amount;
        if (amt instanceof Long) {
            amount = (Long) amt;
        } else {
            amount = (Double) amt;
        }
        Date transactionDate = new Date((Long) data.get("transactionDate"));
        Date enterDate = new Date((Long) data.get("dateEntered"));
        if (amount < 0) {
            String reason = (String) data.get("reason");
            String category = (String) data.get("expenseCategory");
            return new WithdrawalModel(-amount, transactionDate, enterDate,
                    reason, category);
        } else if (amount > 0) {
            String source = (String) data.get("source");
            return new DepositModel(amount, transactionDate, enterDate, source);
        } else {
            throw new RuntimeException(
                    "Cannot create transaction of amount zero.");
        }
    }

    /**
     * Gets the amount.
     * @return The amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction date.
     * @return The date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }
    /**
     * Gets the date entered.
     * @return The enter date
     */
    public Date getEnterDate() {
        return enterDate;
    }
    
    /**
     * Gets map representation.
     * @return The map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("amount", amount);
        temp.put("transactionDate", transactionDate);
        temp.put("dateEntered", enterDate);
        return temp;
    }

    @Override
    public String toString() {
        String result = "";
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(amount);
        result += "Amount: " + moneyString + "\n";
        result += "Transaction Date: " + transactionDate.toString() + "\n";
        result += "Date Entered: " + enterDate.toString() + "\n";
        return result;
    }

}
