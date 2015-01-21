package com.ninerupees.hamilton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.ReportPresenter;
import com.ninerupees.hamilton.view.ReportView;

/**
 * The activity for setting up a report.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class ReportActivity extends Activity implements ReportView {

    private String reportType;
    private String username;
    private ArrayList<String> accountsList;
    private DatePicker startDateView;
    private DatePicker endDateView;
    private ReportPresenter presenter;
    private String report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportType = getIntent().getStringExtra("reportType");
        username = getIntent().getStringExtra("username");
        accountsList = getIntent().getStringArrayListExtra("accountsList");

        presenter = new ReportPresenter(this, new DefaultDatabase());

        System.out.println("Report Type" + reportType);

        endDateView = (DatePicker) findViewById(R.id.end_date);

        startDateView = (DatePicker) findViewById(R.id.start_date);
        startDateView.init(startDateView.getYear(), startDateView.getMonth(),
                startDateView.getDayOfMonth(), new OnDateChangedListener() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        Calendar startCal = new GregorianCalendar(year,
                                monthOfYear, dayOfMonth);
                        long minDate = startCal.getTimeInMillis();
                        endDateView.setMinDate(minDate);
                        Calendar endCal = new GregorianCalendar(endDateView
                                .getYear(), endDateView.getMonth(), endDateView
                                .getDayOfMonth());
                        if (endCal.before(startCal)) {
                            endDateView.updateDate(year, monthOfYear,
                                    dayOfMonth);
                        }
                    }

                });

        endDateView.init(endDateView.getYear(), endDateView.getMonth(),
                endDateView.getDayOfMonth(), new OnDateChangedListener() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        Calendar endCal = new GregorianCalendar(year,
                                monthOfYear, dayOfMonth);
                        long minDate = endCal.getTimeInMillis();
                        startDateView.setMaxDate(minDate);

                        Calendar startCal = new GregorianCalendar(startDateView
                                .getYear(), startDateView.getMonth(),
                                startDateView.getDayOfMonth());
                        if (startCal.after(endCal)) {
                            startDateView.updateDate(year, monthOfYear,
                                    dayOfMonth);
                        }

                    }

                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<String> getAccountsList() {
        return accountsList;
    }

    @Override
    public Date getStartDate() {
        Calendar startCal = new GregorianCalendar(startDateView.getYear(),
                startDateView.getMonth(), startDateView.getDayOfMonth());
        return new Date(startCal.getTimeInMillis());
    }

    @Override
    public Date getEndDate() {
        Calendar endCal = new GregorianCalendar(endDateView.getYear(),
                endDateView.getMonth(), endDateView.getDayOfMonth());
        return new Date(endCal.getTimeInMillis());
    }

    /**
     * Called when the generate report button is pressed.
     * @param view The view
     */
    public void onGenerateReportButtonClick(View view) {
        presenter.onGenerateReport();
    }

    @Override
    public void goToReportDisplayActivity() {
        Intent intent = new Intent(this, ReportDisplayActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("report", report);
        startActivity(intent);
    }

    @Override
    public void passReport(String r) {
        System.out.println(report);
        this.report = r;
    }

}
