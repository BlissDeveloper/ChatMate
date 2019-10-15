package com.bliss.chatmate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.transition.ChangeTransform;

import com.bliss.chatmate.Fragments.ChatFragment;
import com.bliss.chatmate.R;
import com.bliss.chatmate.Utils.MyUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbarHome;
    private BottomNavigationView bottomNavigationView;

    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbarHome = findViewById(R.id.toolbarHome);
        bottomNavigationView = findViewById(R.id.bottomNavBarHome);

        loadFragment();
    }

    public void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome, new ChatFragment());
        fragmentTransaction.commit();
    }
}
