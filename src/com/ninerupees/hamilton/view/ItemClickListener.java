package com.ninerupees.hamilton.view;

/**
 * Interface for presenters to respond to events happening in the respective
 * view. This is to ensure that presenters are in control, not the views.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface ItemClickListener {
    /**
     * Called when something is clicked.
     */
    void onItemClick();
}
