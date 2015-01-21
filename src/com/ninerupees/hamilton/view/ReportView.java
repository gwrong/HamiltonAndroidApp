package com.ninerupees.hamilton.view;

import java.util.Date;
import java.util.List;

/**
 * Defines communication with the Report Activity.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface ReportView {

    /**
     * Gets the username.
     * @return The username
     */
    String getUsername();

    /**
     * Gets the account list.
     * @return List of accounts
     */
    List<String> getAccountsList();

    /**
     * Gets the start date.
     * @return start date
     */
    Date getStartDate();

    /**
     * Gets the end date.
     * @return The end date
     */
    Date getEndDate();

    /**
     * Advance to the Report Display Activity.
     */
    void goToReportDisplayActivity();

    /**
     * Pass the report.
     * @param report The report
     */
    void passReport(String report);

}
