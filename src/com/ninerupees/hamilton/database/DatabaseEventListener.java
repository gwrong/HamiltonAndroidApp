package com.ninerupees.hamilton.database;

/**
 * The event listener class for database.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public interface DatabaseEventListener {

    /**
     * Called on success.
     * @param data The data
     */
    void onSuccess(Object data);
    
    /**
     * Called on a failure.
     * @param data The data
     */
    void onFail(Object data);

}
