package com.example.law_ita;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /**this.setTitle(getString(R.string.settingTitle));
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);**/

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}