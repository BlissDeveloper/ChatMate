package com.bliss.chatmate.Models;

import android.widget.EditText;

public class EditTextErrorMessageImplementation {
    private EditText editText;
    private String errorMessage;

    public EditTextErrorMessageImplementation() {

    }

    public EditTextErrorMessageImplementation(EditText editText, String errorMessage) {
        this.editText = editText;
        this.errorMessage = errorMessage;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
