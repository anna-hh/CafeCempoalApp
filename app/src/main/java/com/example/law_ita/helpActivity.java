package com.example.law_ita;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;

public class helpActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.setTitle(getString(R.string.helpTitle));
        Toolbar toolbar = (Toolbar) findViewById(R.id.helpActivity);
        //toolbar.setTitle("Ayuda");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}