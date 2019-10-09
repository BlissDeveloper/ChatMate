package com.bliss.chatmate.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bliss.chatmate.Models.EditTextErrorMessageImplementation;
import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextRegisterEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSubmitRegistration;
    private Toolbar toolbarRegisterActivity;

    private String title;
    private String incompletePhoneNumber = null;
    private String areaCode = null;
    private String completePhoneNumber = null;
    private final String TAG = "Avery";

    //Click Listener
    private Button.OnClickListener click;

    //Callbacks
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verifyCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextPassword = findViewById(R.id.editTextRegisterPassword);
        editTextConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);
        buttonSubmitRegistration = findViewById(R.id.buttonSubmitRegistration);
        toolbarRegisterActivity = findViewById(R.id.toolbarRegisterPhone);

        title = getString(R.string.title_phone_reg_activity);

        //Toolbar Setup
        MyUtils.setUpToolbar(RegisterActivity.this, toolbarRegisterActivity, title, true);

        //Edit Text Listeners
        initEditTextTextChangedListener();

        //Button Click Listeners
        click = setUpClickListener();
        buttonSubmitRegistration.setOnClickListener(click);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Hide buttons on start
        MyUtils.hideButtons(buttonSubmitRegistration);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    public Button.OnClickListener setUpClickListener() {
        Button.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonSubmitRegistration:
                        goTo();
                        break;
                    default:
                        break;
                }
            }
        };
        return clickListener;
    }

    public void initEditTextTextChangedListener() {
        editTextRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = editTextPassword.getText().toString();
                String password2 = editTextConfirmPassword.getText().toString();
                String email = charSequence.toString();

                executeValidations(email, password, password2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = editTextRegisterEmail.toString();
                String password = charSequence.toString();
                String password2 = editTextConfirmPassword.getText().toString();

                executeValidations(email, password, password2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = editTextRegisterEmail.toString();
                String password = editTextPassword.toString();
                String password2 = charSequence.toString();

                executeValidations(email, password, password2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void goTo() {
        String email = editTextRegisterEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        Intent intent = new Intent(RegisterActivity.this, ProfileInputActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void executeValidations(String email, String password, String password2) {
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(password2)) {
            boolean isValid = MyUtils.isEmailValid(email);
            int passLen = password.length();
            int pass2Len = password2.length();

            if (isValid || passLen >= 6 || pass2Len >= 6) {
                MyUtils.showButtons(buttonSubmitRegistration);
            } else {
                MyUtils.hideButtons(buttonSubmitRegistration);
                if (!isValid) {
                    editTextRegisterEmail.setError(getString(R.string.error_email_invalid));
                }
                if (passLen < 6) {
                    editTextPassword.setError(getString(R.string.error_password_invalid));
                }
                if (pass2Len < 6) {
                    editTextConfirmPassword.setError(getString(R.string.error_password_invalid));
                }
            }
        } else {
            MyUtils.hideButtons(buttonSubmitRegistration);
            if (TextUtils.isEmpty(email)) {
                editTextRegisterEmail.setError(getString(R.string.error_email_required));
            }
            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError(getString(R.string.error_password_required));
            }
            if (TextUtils.isEmpty(password2)) {
                editTextConfirmPassword.setError(getString(R.string.error_password_confirmation_required));
            }
        }
    }
}
