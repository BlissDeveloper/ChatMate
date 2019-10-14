package com.bliss.chatmate.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInputActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private FloatingActionButton fabOpenGallery;
    private Toolbar toolbarProfileInput;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUsername;
    private Button buttonSubmitProfileInput;

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
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextUsername = findViewById(R.id.editTextUserName);
        buttonSubmitProfileInput = findViewById(R.id.buttonSubmitProfileInput);

        //Click Listener
        click = initiateClickListeners();
        fabOpenGallery.setOnClickListener(click);

        //Toolbar Setup
        setSupportActionBar(toolbarProfileInput);
        getSupportActionBar().setTitle(R.string.title_profile_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TextWatcher
        initializeTextWatcher();
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
                changeButtonAppearance(MyUtils.ADD);
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
                    case R.id.buttonSubmitProfileInput:
                        validateInput();
                        break;
                }
            }
        };
        return clickListener;
    }

    public void initializeTextWatcher() {
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //charSequence.toString().matches(getString(R.string.has_digits_checker)
                String fName = charSequence.toString();
                String lName = editTextLastName.getText().toString();
                String uName = editTextUsername.getText().toString();

                editTextValidations(fName, lName, uName);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void addValidations(String firstName, String lastName, String userName) {
        if (!TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName) || !TextUtils.isEmpty(userName)) {
            //Not empty

        } else {
            //Empty
            if (TextUtils.isEmpty(firstName)) {
                editTextFirstName.setError(getString(R.string.error_first_name_req));
            }
            if (TextUtils.isEmpty(lastName)) {
                editTextLastName.setError(getString(R.string.error_last_name_req));
            }
            if (TextUtils.isEmpty(userName)) {
                editTextUsername.setError(getString(R.string.error_user_name_req));
            }
        }
    }

    public void deleteImage() {
        circleImageView.setImageURI(null);
        imageUri = null;
        changeButtonAppearance(MyUtils.DELETE);
        circleImageView.setImageDrawable(getDrawable(R.drawable.icon_place_holder));
    }

    public void changeButtonAppearance(int flag) {
        if (flag == MyUtils.ADD) {
            fabOpenGallery.setImageDrawable(getDrawable(R.drawable.icon_delete));
            fabOpenGallery.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMaterialRed)));
        } else if (flag == MyUtils.DELETE) {
            fabOpenGallery.setImageDrawable(getDrawable(R.drawable.icon_add));
            fabOpenGallery.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMaterialGreen)));
        }
        //circleImageView.setImageDrawable(getDrawable(R.drawable.icon_place_holder));
    }

    public void validateInput() {
        String firstName, lastName, userName;
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        userName = editTextUsername.getText().toString();

        if (!MyUtils.areStringsEmpty(firstName, lastName, userName)) {

        } else {
            if (TextUtils.isEmpty(firstName)) {
                editTextFirstName.setError(getString(R.string.error_first_name_req));
            }
            if (TextUtils.isEmpty(lastName)) {
                editTextLastName.setError(getString(R.string.error_last_name_req));
            }
            if (TextUtils.isEmpty(userName)) {
                editTextUsername.setError(getString(R.string.error_user_name_req));
            }
        }
    }

    public void editTextValidations(String fName, String lName, String uName) {
        String firstName = fName;
        String lastName = lName;
        String userName = uName;
        int firstNameLen = firstName.length();
        int lastNameLen = lastName.length();
        int userNameLen = userName.length();

        if (firstNameLen > 0 && lastNameLen > 0 && userNameLen >= 6) {
            if (!firstName.matches(getString(R.string.has_digits_checker))) {

            } else {
                MyUtils.hideViews(buttonSubmitProfileInput);
            }
        } else {
            MyUtils.hideViews(buttonSubmitProfileInput);

        }
    }
}
