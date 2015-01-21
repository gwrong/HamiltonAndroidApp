package com.ninerupees.hamilton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.ninerupees.hamilton.database.DefaultDatabase;
import com.ninerupees.hamilton.presenter.ReportDisplayPresenter;
import com.ninerupees.hamilton.view.ReportDisplayView;

/**
 * The activity for displaying a report.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class ReportDisplayActivity extends Activity implements
        ReportDisplayView {

    private String username;
    private String report;
    private ReportDisplayPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_display);

        username = getIntent().getStringExtra("username");
        report = getIntent().getStringExtra("report");

        presenter = new ReportDisplayPresenter(this, new DefaultDatabase());

        TextView reportView = (TextView) findViewById(R.id.report_text);

        reportView.setText(report);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_display, menu);
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
