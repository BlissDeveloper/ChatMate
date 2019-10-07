package com.bliss.chatmate.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextMobileNumber;
    private TextInputEditText editTextPassword;

    private Button buttonLogin;
    private TextView textViewGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLoginSubmit);
        textViewGoToRegister = findViewById(R.id.textViewDontHaveAnAccount);

        attachClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Hide the button on start:
        MyUtils.hideViews(buttonLogin);
    }

    public void attachClickListener() {
        Button.OnClickListener clickListener = setUpClickListener();
        textViewGoToRegister.setOnClickListener(clickListener);
    }

    public Button.OnClickListener setUpClickListener() {
        Button.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textViewDontHaveAnAccount:
                        MyUtils.goToActivity(LoginActivity.this, RegisterActivity.class);
                        break;
                }
            }
        };
        return clickListener;
    }
}
