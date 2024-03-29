package com.bliss.chatmate.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bliss.chatmate.Activities.SplashScreenActivity;
import com.bliss.chatmate.Models.DeniedPermissions;
import com.bliss.chatmate.Models.EditTextErrorMessageImplementation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
            view.setEnabled(true);
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

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static DeniedPermissions areAllPermissionsGranted(Context context, String... strings) {
        boolean isAllGranted = true;
        DeniedPermissions deniedPermissions = new DeniedPermissions();
        List<String> ungrantedPermissions = new ArrayList<>();
        for (String s : strings) {
            if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                ungrantedPermissions.add(s);
            }
        }
        deniedPermissions.setAllGranted(isAllGranted);
        deniedPermissions.setPermissions(ungrantedPermissions);

        return deniedPermissions;
    }

    public static void loadFragment(Activity context, Fragment fragment, int frameLayoutID) {
        FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction().replace(frameLayoutID, fragment);
        fragmentTransaction.commit();
    }


}
