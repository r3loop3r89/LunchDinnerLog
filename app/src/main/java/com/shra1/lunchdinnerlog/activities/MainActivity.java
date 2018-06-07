package com.shra1.lunchdinnerlog.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.shra1.lunchdinnerlog.R;
import com.shra1.lunchdinnerlog.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar tbToolbar;
    private FrameLayout flMAFragmentContainer;
    private int iFlFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setSupportActionBar(tbToolbar);

        changeFragment(HomeFragment.getInstance());

    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(iFlFragmentContainer, fragment)
                .commit();
    }

    private void initViews() {
        tbToolbar = (Toolbar) findViewById(R.id.tbToolbar);
        iFlFragmentContainer = R.id.flMAFragmentContainer;
        flMAFragmentContainer = (FrameLayout) findViewById(iFlFragmentContainer);
    }
}
