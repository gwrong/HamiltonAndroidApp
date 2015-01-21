package com.ninerupees.hamilton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The first screen in hamilton.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);

    }

    /**
     * Moves to the Login Activity.
     * 
     * @param view
     *            The view being changed
     */
    public void goToLoginScreen(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the Register Activity.
     * 
     * @param view
     *            The view being changed
     */
    public void goToRegisterScreen(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
