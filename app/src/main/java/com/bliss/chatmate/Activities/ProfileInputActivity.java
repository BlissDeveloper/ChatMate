package com.bliss.chatmate.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInputActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private FloatingActionButton fabOpenGallery;

    private Uri imageUri = null;
    private final int OPEN_GALLERY_CODE = 5;

    private Button.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_input);

        circleImageView = findViewById(R.id.circleImageViewUserImageReg);
        fabOpenGallery = findViewById(R.id.fabAddImage);

        //Click Listener
        click = initiateClickListeners();
        fabOpenGallery.setOnClickListener(click);
    }

    @Override
    protected void onStart() {
        super.onStart();

        circleImageView.setImageDrawable(getDrawable(R.drawable.icon_place_holder));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            circleImageView.setImageURI(imageUri);
        }
    }

    public Button.OnClickListener initiateClickListeners() {
        Button.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fabAddImage:
                        //MyUtils.openGallery(ProfileInputActivity.this, OPEN_GALLERY_CODE);
                        if (imageUri == null) {
                            MyUtils.openGallery(ProfileInputActivity.this, OPEN_GALLERY_CODE);
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
}
