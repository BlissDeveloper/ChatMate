package com.bliss.chatmate.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInputActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private FloatingActionButton fabOpenGallery;
    private Toolbar toolbarProfileInput;

    private Uri imageUri = null;
    private final int OPEN_GALLERY_CODE = 5;

    private Button.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_input);

        circleImageView = findViewById(R.id.circleImageViewUserImageReg);
        fabOpenGallery = findViewById(R.id.fabAddImage);
        toolbarProfileInput = findViewById(R.id.toolbarProfileInput);

        //Click Listener
        click = initiateClickListeners();
        fabOpenGallery.setOnClickListener(click);

        //Toolbar Setup
        setSupportActionBar(toolbarProfileInput);
        getSupportActionBar().setTitle(R.string.title_profile_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //circleImageView.setImageDrawable(getDrawable(R.drawable.icon_place_holder));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(MyUtils.TAG, "** On Activity Result **");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                circleImageView.setImageURI(imageUri);
                changeButtonAppearance();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(MyUtils.TAG, error.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileInputActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public Button.OnClickListener initiateClickListeners() {
        Button.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fabAddImage:
                        //MyUtils.openGallery(ProfileInputActivity.this, OPEN_GALLERY_CODE);
                        if (imageUri == null) {
                            MyUtils.openCropper(ProfileInputActivity.this);
                        } else {
                            deleteImage();
                        }
                        break;
                }
            }
        };
        return clickListener;
    }

    public void deleteImage() {
        circleImageView.setImageURI(null);
        imageUri = null;
    }

    public void changeButtonAppearance() {
        fabOpenGallery.setImageDrawable(getDrawable(R.drawable.icon_delete));
        fabOpenGallery.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMaterialRed)));
        circleImageView.setImageDrawable(getDrawable(R.drawable.icon_place_holder));
    }
}
