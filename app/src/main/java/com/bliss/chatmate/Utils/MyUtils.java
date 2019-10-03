package com.bliss.chatmate.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bliss.chatmate.Activities.SplashScreenActivity;

public class MyUtils {

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
}
