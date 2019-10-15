package com.bliss.chatmate.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bliss.chatmate.Models.DeniedPermissions;
import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextMobileNumber;
    private TextInputEditText editTextPassword;

    private Button buttonLogin;
    private TextView textViewGoToRegister;

    private final int PERMISSION_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLoginSubmit);
        textViewGoToRegister = findViewById(R.id.textViewDontHaveAnAccount);

        attachClickListener();

        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Hide the button on start:
        MyUtils.hideViews(buttonLogin);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(MyUtils.TAG, "All permissions granted");
            } else {
                checkPermissions();
            }
        }
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

    public void checkPermissions() {
        DeniedPermissions deniedPermissions = MyUtils.areAllPermissionsGranted(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!deniedPermissions.isAllGranted()) {
            //Not all permissions are granted:
            List<String> deniedPerm = deniedPermissions.getPermissions();
            ActivityCompat.requestPermissions(this,
                    deniedPerm.toArray(new String[0]), //Converting List to Array Object
                    PERMISSION_CODE);
        }
    }

}
