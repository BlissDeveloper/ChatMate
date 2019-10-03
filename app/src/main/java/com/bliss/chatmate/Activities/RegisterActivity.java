package com.bliss.chatmate.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextIncompletePhoneNumber;
    private EditText editTextVerificationCode;
    private Button buttonSendCode;
    private Button buttonSubmitPhoneNumber;
    private Toolbar toolbarRegisterActivity;

    private String title;
    private String incompletePhoneNumber = null;
    private String areaCode = null;
    private String completePhoneNumber = null;
    private final String TAG = "Avery";

    //Callbacks
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verifyCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextIncompletePhoneNumber = findViewById(R.id.editTextIncompleteNumber);
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        buttonSendCode = findViewById(R.id.buttonSendVerificationCode);
        buttonSubmitPhoneNumber = findViewById(R.id.buttonSubmitPhoneNumber);
        toolbarRegisterActivity = findViewById(R.id.toolbarRegisterPhone);

        areaCode = getString(R.string.hint_area_code);
        title = getString(R.string.title_phone_reg_activity);

        //Toolbar Setup
        MyUtils.setUpToolbar(RegisterActivity.this, toolbarRegisterActivity, title, true);

        //Edit Text Listeners
        initEditTextTextChangedListener();

        //Button Click Listeners
        buttonSendCode.setOnClickListener(setUpClickListener());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Hide buttons on start
        MyUtils.hideButtons(buttonSendCode, buttonSubmitPhoneNumber);
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

    public void sendCode() {
        completePhoneNumber = "+63" + editTextIncompletePhoneNumber.getText().toString().trim();

        verifyCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        };

        Log.d(TAG, "Sending to " + completePhoneNumber);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                completePhoneNumber,
                60,
                TimeUnit.SECONDS,
                RegisterActivity.this,
                verifyCallback
        );
    }

    public Button.OnClickListener setUpClickListener() {
        Button.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonSendVerificationCode:
                        MyUtils.hideButtons(buttonSendCode);
                        sendCode();
                        break;
                    case R.id.buttonSubmitPhoneNumber:

                        break;
                    default:
                        break;
                }
            }
        };
        return clickListener;
    }

    public void initEditTextTextChangedListener() {
        editTextIncompletePhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String incompleteNumber = charSequence.toString().trim();
                int len = incompleteNumber.length();
                if (len < 9) {
                    MyUtils.hideButtons(buttonSendCode);
                } else {
                    MyUtils.showButtons(buttonSendCode);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
