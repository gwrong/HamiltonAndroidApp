package com.ninerupees.hamilton.model;

import java.util.Date;
import java.util.Map;

/**
 * The withdrawal model.
 * @author Nine Rupees
 * @version 1.0
 */
public class WithdrawalModel extends AbstractTransactionModel {

    private String reason;
    private String expenseCategory;

    /**
     * The constructor.
     * @param amount The amount
     * @param transactionDate The trans date
     * @param enterDate Enter date
     * @param reason The reason
     * @param expenseCategory The expense category
     */
    public WithdrawalModel(double amount, Date transactionDate, Date enterDate,
            String reason, String expenseCategory) {
        super(-amount, transactionDate, enterDate);
        this.reason = reason;
        this.expenseCategory = expenseCategory;
    }

    /**
     * Gets the reason.
     * @return The reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Gets the expense category.
     * @return The expense category
     */
    public String getExpenseCategory() {
        return this.expenseCategory;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> temp = super.toMap();
        temp.put("reason", reason);
        temp.put("expenseCategory", expenseCategory);
        return temp;
    }

    @Override
    public String toString() {
        String result = super.toString();
        result = "Withdrawal\n" + result;
        result += "Reason: " + reason + "\n";
        result += "Expense Category: " + expenseCategory + "\n";
        return result;
    }

}
