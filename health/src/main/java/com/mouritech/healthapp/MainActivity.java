/*
 *
 *  * Purpose  MainActivity.java
 *
 *  Copyright  2021  MouriTech
 *
 *  @author  udayv
 *
 *  Created on Mar 29, 2021
 *
 *  Modified on Mar 29, 2021
 *
 */

package com.mouritech.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mouritech.healthapp.databinding.ActivityMainBinding;
import com.mouritech.healthapp.home.FitbitActivity;
import com.mouritech.healthapp.home.GoogleFitActivity;
import com.mouritech.healthapp.home.StravaActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

//        startActivity(new Intent(this, FitbitActivity.class));
//        startActivity(new Intent(this, GoogleFitActivity.class));
        initUI();
    }

    private void initUI(){
        binding.mainFitbitBtn.setOnClickListener(v -> startActivity(new Intent(this, FitbitActivity.class)));
        binding.mainGooglebitBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GoogleFitActivity.class)));
        binding.mainStravaBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StravaActivity.class)));
    }
}