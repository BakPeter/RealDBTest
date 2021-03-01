package com.bpapps.realmdbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bpapps.realmdbtest.repository.localdata.RealmDBHandler;
import com.bpapps.realmdbtest.view.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RealmDBHandler.init(RealmDBTestApp.getAppContext());

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, MainFragment.newInstance())
//                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RealmDBHandler.getInstance().destroy();
    }
}