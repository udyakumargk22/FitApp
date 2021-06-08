/*
 *
 *  * Purpose  GoogleFitActivity.java
 *
 *  Copyright  2021  MouriTech
 *
 *  @author  udayv
 *
 *  Created on Apr 05, 2021
 *
 *  Modified on Apr 05, 2021
 *
 */

package com.mouritech.healthapp.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mouritech.healthapp.R;
import com.mouritech.healthapp.databinding.ActivityGoogleFitBinding;
import com.mouritech.healthapp.model.fitbitmodel.HeartRateZone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleFitActivity extends AppCompatActivity {

    private static final String TAG = "GoogleFitActivity";

    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    FitnessOptions fitnessOptions;

    ActivityGoogleFitBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_google_fit);
        initUI();
    }


    private void initUI() {

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE)
                .build();
        binding.googleFitLoginBtn.setOnClickListener(v -> loginUser());
        binding.googleFitLogoutBtn.setOnClickListener(v -> logout());
        binding.googleFitDataBtn.setOnClickListener(v -> accessGoogleFit());

        loginUser();
    }

    private void loginUser(){
        Log.d(TAG, "loginUser() called");
//        if (oAuthPermissionsApproved()) {
//            performActionForRequestCode();
//        }else {
            GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
            if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                        this, // your activity
                        GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                        account,
                        fitnessOptions);



            } else {
                accessGoogleFit();
            }
//        }
    }

    private void performActionForRequestCode() {

    }

    private GoogleSignInAccount getGoogleAccount() {
      return   GoogleSignIn.getAccountForExtension(this, fitnessOptions);
    }


    private void accessGoogleFit() {
        Log.d(TAG, "accessGoogleFit() called");
        LocalDateTime end = LocalDateTime.now();
//        LocalDateTime start = end.minusYears(1);
        LocalDateTime start = end.minusDays(10);
        long endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond();
        long startSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond();

        DataReadRequest readRequest = new DataReadRequest.Builder()
//                .aggregate(DataType.TYPE_HEART_POINTS)
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();
        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
        Fitness.getHistoryClient(this, account)
                .readData(readRequest)
                .addOnSuccessListener(dataReadResponse -> {
                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getBuckets());
                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getDataSets());
                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getStatus());


                    for (int i = 0; i < dataReadResponse.getBuckets().size(); i++) {
                        for (int j = 0; j < dataReadResponse.getBuckets().get(i).getDataSets().size(); j++) {
                            for (int k = 0; k < dataReadResponse.getBuckets().get(i).getDataSets().get(j).getDataPoints().size(); k++) {
                                Log.v(TAG, "accessGoogleFit: "+dataReadResponse.getBuckets().get(i).getDataSets().get(j).getDataPoints().get(k).getValue(Field.FIELD_STEPS) );
                            }
                        }
                    }

//                    for (int i = 0; i < dataReadResponse.getBuckets().size(); i++) {
////                        Log.e(TAG, "accessGoogleFit: "+dataReadResponse.getBuckets().get(i).getActivity() );
////                        Log.e(TAG, "accessGoogleFit: "+dataReadResponse.getBuckets().get(i).getBucketType() );
//                        Log.e(TAG, "accessGoogleFit: "+dataReadResponse.getBuckets().get(i).getDataSets() );
//                        Log.i(TAG, "accessGoogleFit: ========================================" );
//                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                    }
                });

        insertStepsData();
        insertHeartData();
        readData();
        readHeartData();

//        readGoals();

    }

    private void insertStepsData(){
        ZonedDateTime currenttime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime endTime = currenttime.minusDays(6);
        ZonedDateTime startTime = endTime.minusDays(6);

        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(this)
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setStreamName("$TAG - step count")
                .setType(DataSource.TYPE_RAW)
                .build();

        int stepCountDelta = 100;
        DataPoint dataPoint =
                DataPoint.builder(dataSource)
                        .setField(Field.FIELD_STEPS, stepCountDelta)
                        .setTimeInterval(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                        .build();

        DataSet dataSet = DataSet.builder(dataSource)
                .add(dataPoint)
                .build();

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .insertData(dataSet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: Insert" );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Fail" );
                    }
                });
    }
    private void insertHeartData(){
        ZonedDateTime currenttime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime endTime = currenttime.minusDays(1);
        ZonedDateTime startTime = endTime.minusDays(2);

        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(this)
                .setDataType(DataType.TYPE_HEART_RATE_BPM)
                .setStreamName("$TAG - Heart Points")
                .setType(DataSource.TYPE_RAW)
                .build();

        float stepCountDelta = 70;
        DataPoint dataPoint =
                DataPoint.builder(dataSource)
                        .setField(Field.FIELD_BPM, stepCountDelta)
                        .setTimeInterval(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                        .build();

        DataSet dataSet = DataSet.builder(dataSource)
                .add(dataPoint)
                .build();

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .insertData(dataSet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: Insert" );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Fail "+e.getMessage() );
                    }
                });
    }


    private void readData() {
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(new OnSuccessListener<DataSet>() {
                    @Override
                    public void onSuccess(DataSet dataSet) {
                        for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                            Log.e(TAG, "readData: "+dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS) );
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " );
                    }
                });

    }
    private void readHeartData() {
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_HEART_RATE_BPM)
                .addOnSuccessListener(new OnSuccessListener<DataSet>() {
                    @Override
                    public void onSuccess(DataSet dataSet) {
                        Log.e(TAG, "onSuccess: "+dataSet );
                        for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                            Log.e(TAG, "readHeartData:readHeartData: "+dataSet.getDataPoints().get(0).getValue(Field.FIELD_BPM) );
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "readHeartData:onFailure: "+e.getMessage() );
                    }
                });


//        LocalDateTime end = LocalDateTime.now();
//        LocalDateTime start = end.minusDays(10);
//        long endSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond();
//        long startSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond();
//
//        DataReadRequest readRequest = new DataReadRequest.Builder()
////                .aggregate(DataType.TYPE_HEART_POINTS)
//                .aggregate(DataType.TYPE_HEART_RATE_BPM)
//                .setTimeRange(startSeconds, endSeconds, TimeUnit.SECONDS)
//                .bucketByTime(1, TimeUnit.DAYS)
//                .build();
//        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
//        Fitness.getHistoryClient(this, account)
//                .readData(readRequest)
//                .addOnSuccessListener(dataReadResponse -> {
//                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getBuckets());
//                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getDataSets());
//                    Log.v(TAG, "accessGoogleFit:Success "+dataReadResponse.getStatus());
//
//
//                    for (int i = 0; i < dataReadResponse.getBuckets().size(); i++) {
//                        for (int j = 0; j < dataReadResponse.getBuckets().get(i).getDataSets().size(); j++) {
//                            for (int k = 0; k < dataReadResponse.getBuckets().get(i).getDataSets().get(j).getDataPoints().size(); k++) {
//                                Log.v(TAG, "accessGoogleFit: "+dataReadResponse.getBuckets().get(i).getDataSets().get(j).getDataPoints().get(k).getValue(Field.FIELD_STEPS) );
//                            }
//                        }
//                    }
//
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: " + e.getMessage());
//                    }
//                });


    }

    private void readGoals(){
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_READ)
                .build();

        GoalsReadRequest goalsReadRequest = new GoalsReadRequest.Builder()
                .addDataType(DataType.TYPE_HEART_POINTS)
                .build();

        Fitness.getGoalsClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .readCurrentGoals(goalsReadRequest)
                .addOnSuccessListener(new OnSuccessListener<List<Goal>>() {
                    @Override
                    public void onSuccess(List<Goal> goals) {
                        Log.e(TAG, "onSuccess:goals " + goals);
                        if (goals != null) {
                            Log.e(TAG, "onSuccess:goals " + goals.size());
                            for (int i = 0; i < goals.size(); i++) {
                                Log.e(TAG, "onSuccess: "+goals.get(i).getActivityName() );
                                Log.e(TAG, "onSuccess: "+goals.get(i).getObjectiveType() );
                                Log.e(TAG, "onSuccess: "+goals.get(i).getRecurrence() );
                            }

                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: "+e.getMessage() );
            }
        });




            Log.i(TAG, "Recurrence: $repeatDetails");


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");

        if (resultCode == Activity.RESULT_OK && requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
//            accessGoogleFit();
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            finish();

        }
    }


    private void logout(){

            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder().addExtension(fitnessOptions).build();
            GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
            client.revokeAccess()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e(TAG, "onSuccess: 222222222222222222" );
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: 44444444444" );
                }
            });


    }




}