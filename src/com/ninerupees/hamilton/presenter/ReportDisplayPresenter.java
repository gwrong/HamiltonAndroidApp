package com.ninerupees.hamilton.presenter;

import com.ninerupees.hamilton.database.DatabaseInterface;
import com.ninerupees.hamilton.view.ReportDisplayView;

/**
 * Manages the Display of the report.
 * @author Nine Rupees
 * @version 1.0
 */
public class ReportDisplayPresenter {
    private ReportDisplayView view;
    private DatabaseInterface model;
    private String username;

    /**
     * Constructor.
     * 
     * @param view
     *            The activity
     * @param model
     *            The database
     */
    public ReportDisplayPresenter(ReportDisplayView view,
            DatabaseInterface model) {
        this.view = view;
        this.model = model;
        username = view.getUsername();
    }

}
