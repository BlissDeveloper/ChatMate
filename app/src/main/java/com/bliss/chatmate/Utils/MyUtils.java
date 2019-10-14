package com.bliss.chatmate.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bliss.chatmate.Activities.SplashScreenActivity;
import com.bliss.chatmate.Models.EditTextErrorMessageImplementation;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class MyUtils {
    public static final String TAG = "TAG";
    public static final int DELETE = 0;
    public static final int ADD = 1;

    public MyUtils() {

    }

    public static void goToActivity(Context origin, Class<?> end) {
        Intent intent = new Intent(origin, end);
        origin.startActivity(intent);
    }

    public static void setUpToolbar(AppCompatActivity activity, Toolbar toolbar, String title, boolean hasBackButton) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackButton);
    }

    public static void openGallery(AppCompatActivity context, int request_code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), request_code);
    }

    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
            view.setEnabled(false);
        }
    }

    public static void hideButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setVisibility(View.GONE);
            button.setEnabled(false);
        }
    }

    public static void showButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean areStringsEmpty(String... strings) {
        boolean falseHit = false;

        for (String s : strings) {
            if (!TextUtils.isEmpty(s)) {
                falseHit = true;
            }
        }

        return falseHit;
    }

    public static void showErrorMessages(List<EditTextErrorMessageImplementation> errorList) {
        for (EditTextErrorMessageImplementation errorMessage : errorList) {
            EditText editText = errorMessage.getEditText();
            String message = errorMessage.getErrorMessage();
            editText.setError(message);
        }
    }

    public static void openCropper(Activity activity) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }
}
