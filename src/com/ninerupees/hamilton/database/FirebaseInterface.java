package com.ninerupees.hamilton.database;

import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * The Firebase Database Interfacer.
 * 
 * @author Nine Rupees
 * @version 1.0
 */
public class FirebaseInterface implements DatabaseInterface {
    private Firebase rootRef;
    private Firebase userRef;

    /**
     * Firebase constructor.
     */
    public FirebaseInterface() {
        connect();
    }

    @Override
    public boolean connect() {
        rootRef = new Firebase("https://hamiltontech.firebaseio.com/");

        return true;
    }

    @Override
    public void addUser(final String username, final String password,
            final String email, final String firstName, final String lastName,
            final DatabaseEventListener eventListener) {
        final Firebase loginRef = rootRef.child("login").child(username);
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Object user = snapshot.getValue();
                // System.out.println(user);
                if (user == null) {
                    userRef.child("first_name").setValue(firstName);
                    userRef.child("last_name").setValue(lastName);
                    userRef.child("email").setValue(email);
                    userRef.child("username").setValue(username);
                    loginRef.child("password").setValue(password);
                    eventListener.onSuccess(user);
                } else {
                    eventListener.onFail(user);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    @Override
    public void login(String username, final String password,
            final DatabaseEventListener eventListener) {
        userRef = rootRef.child("login").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object data = snapshot.getValue();
                if (data != null) {
                    Object pass = snapshot.child("password").getValue();
                    if (password.equals(pass)) {
                        eventListener.onSuccess(data);
                    } else {
                        eventListener.onFail(false);
                    }
                } else {
                    eventListener.onFail(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    @Override
    public void createAccount(String username, final String accountName,
            final String displayName, final double interestRate,
            final String type, final DatabaseEventListener eventListener) {
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);
        final Firebase accountRef = usersRef.child(username).child("accounts")
                .child(accountName);

        accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object account = snapshot.getValue();
                // System.out.println(user);
                if (account == null) {
                    // System.out.println("bob");
                    accountRef.child("name").setValue(accountName);
                    accountRef.child("displayName").setValue(displayName);
                    accountRef.child("interestRate").setValue(interestRate);
                    accountRef.child("type").setValue(type);
                    accountRef.child("balance").setValue(0.0);
                    eventListener.onSuccess(account);
                } else {
                    eventListener.onFail(account);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    @Override
    public void getUser(String username,
            final DatabaseEventListener eventListener) {
        userRef = rootRef.child("users").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object data = snapshot.getValue();
                if (data != null) {
                    eventListener.onSuccess(data);
                } else {
                    eventListener.onFail(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    @Override
    public void getAccounts(String username,
            final DatabaseEventListener eventListener) {
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);
        final Firebase accountsRef = usersRef.child(username).child("accounts");

        accountsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object data = snapshot.getValue();
                if (snapshot == null || data == null) {
                    eventListener.onSuccess(null);
                    return;
                } else {
                    eventListener.onSuccess(data);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    /*
     * public void getAccountData(String username, final String accountName,
     * final DatabaseEventListener eventListener) { final Firebase accountRef =
     * rootRef
     * .child("users").child(username).child("accounts").child(accountName);
     * accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
     * 
     * @Override public void onDataChange(DataSnapshot snapshot) { Object
     * account = snapshot.getValue(); if (account == null) {
     * eventListener.onFail(null);
     * //accountRef.child("name").setValue(accountName);
     * //eventListener.onSuccess(account); } else { Firebase ref =
     * accountRef.child("transactions").push(); ref.setValue(transaction);
     * accountRef.child("balance").setValue(balance);
     * eventListener.onSuccess(ref); } }
     * 
     * @Override public void onCancelled(FirebaseError arg0) {
     * eventListener.onFail(null); } }); }
     */

    @Override
    public void getAccountNames(String username,
            final DatabaseEventListener eventListener) {
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);
        final Firebase accountsRef = usersRef.child(username).child("accounts");

        accountsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null) {
                    eventListener.onSuccess(null);
                }
                String[] children = new String[(int) snapshot
                        .getChildrenCount()];
                int i = 0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    DataSnapshot temp = child.child("name");
                    if (temp != null) {
                        children[i] = (String) temp.getValue();
                        i++;
                    }
                }
                if (i == 0) {
                    eventListener.onSuccess(null);
                } else {
                    eventListener.onSuccess(children);
                }

            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }

    /**
     * Store transaction name, date, date entered, money, type, and other stuff
     * 
     * @param username
     * @param accountName
     * @param eventListener
     */
    /*
     * public void addTransactions(String username, final String accountName,
     * final String transactionName, double ammount, final Date transactionDate,
     * final Date dateEntered, final String category, final
     * DatabaseEventListener eventListener) { Firebase usersRef =
     * rootRef.child("users"); userRef = usersRef.child(username); final
     * Firebase accountRef =
     * usersRef.child(username).child("accounts").child(accountName);
     * 
     * accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
     * 
     * @Override public void onDataChange(DataSnapshot snapshot) { Object
     * account = snapshot.getValue(); if (account == null) {
     * eventListener.onFail(null);
     * accountRef.child("name").setValue(accountName);
     * eventListener.onSuccess(account); } else {
     * 
     * accountRef.child("transactions").push().setValue(value);
     * snapshot.child("transactions").getValue();
     * 
     * 
     * } }
     * 
     * @Override public void onCancelled(FirebaseError arg0) {
     * eventListener.onFail(null); } }); }
     */

    @Override
    public void addTransactions(String username, final String accountName,
            final double balance, final Map<String, Object> transaction,
            final DatabaseEventListener eventListener) {
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);
        final Firebase accountRef = usersRef.child(username).child("accounts")
                .child(accountName);
        // If it's a withdrawal of more than the current balance, then fail the
        // add transaction
        if (transaction.get("source") == null
                && balance < Math.abs((Double) transaction.get("amount"))) {
            eventListener.onFail("CannotWithdraw");
        } else {
            accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Object account = snapshot.getValue();
                    if (account == null) {
                        eventListener.onFail(null);
                        // accountRef.child("name").setValue(accountName);
                        // eventListener.onSuccess(account);
                    } else {
                        Firebase ref = accountRef.child("transactions").push();
                        ref.setValue(transaction);

                        accountRef.child("balance").setValue(balance);
                        eventListener.onSuccess(ref);
                    }
                }

                @Override
                public void onCancelled(FirebaseError arg0) {
                    eventListener.onFail(null);
                }
            });
        }

    }

    @Override
    public void getTransactions(String username, final String accountName,
            final DatabaseEventListener eventListener) {
        Firebase usersRef = rootRef.child("users");
        userRef = usersRef.child(username);
        final Firebase accountRef = usersRef.child(username).child("accounts")
                .child(accountName).child("transactions");

        accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object transactions = snapshot.getValue();
                if (transactions == null) {
                    eventListener.onFail(null);
                    // accountRef.child("name").setValue(accountName);
                    // eventListener.onSuccess(account);
                } else {
                    eventListener.onSuccess(transactions);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
                eventListener.onFail(null);
            }
        });
    }
}
