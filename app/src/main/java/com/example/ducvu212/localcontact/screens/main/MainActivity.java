package com.example.ducvu212.localcontact.screens.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.ducvu212.localcontact.R;
import com.example.ducvu212.localcontact.screens.contact.ContactFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TRANSACTION_ADD_TAG = "Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main_layout, ContactFragment.newInstance(), TRANSACTION_ADD_TAG);
        transaction.commit();
    }
}
