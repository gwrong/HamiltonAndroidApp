package com.ninerupees.hamilton.model;

import java.util.Date;
import java.util.Map;

/**
 * The Deposit representation.
 * @author Nine Rupees
 *
 */
public class DepositModel extends AbstractTransactionModel {

    private String source;

    /**
     * Constructor.
     * @param amount Amount
     * @param transactionDate The transaction date
     * @param enterDate Enter date
     * @param source Source of it
     */
    public DepositModel(double amount, Date transactionDate, Date enterDate,
            String source) {
        super(amount, transactionDate, enterDate);
        this.source = source;
    }

    /**
     * Sets the source.
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Gets the source.
     * @return The source
     */
    private String getSource() {
        return source;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> temp = super.toMap();
        temp.put("source", source);
        return temp;
    }

    @Override
    public String toString() {
        String result = super.toString();
        result = "Deposit\n" + result;
        result += "Source: " + source + "\n";
        return result;

    }
}
