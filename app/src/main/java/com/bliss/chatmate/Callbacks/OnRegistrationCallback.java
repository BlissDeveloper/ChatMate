package com.bliss.chatmate.Callbacks;

public interface OnRegistrationCallback {
    void onRegistrationSuccess();

    void onRegistrationFailed(Exception e);
}
