package com.bliss.chatmate.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUtils extends MyUtils {
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String currentUserID;

    public FirebaseUtils() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        currentUserID = firebaseUser.getUid();
    }

    public FirebaseUtils(FirebaseAuth mAuth, FirebaseUser firebaseUser, String currentUserID) {
        this.mAuth = mAuth;
        this.firebaseUser = firebaseUser;
        this.currentUserID = currentUserID;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }
}
