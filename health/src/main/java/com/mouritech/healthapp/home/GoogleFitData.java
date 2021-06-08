package com.mouritech.healthapp.home;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class GoogleFitData {

    public GoogleFitData() {
    }

    private static final String TAG = "GoogleFitData";

    public void accessGoogleFit(Context context) {
        Log.d(TAG, "accessGoogleFit() called");

       FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE)
                .build();


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
        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(context, fitnessOptions);
        Fitness.getHistoryClient(context, account)
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


    }


    public void logout(Context context){

        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_WRITE)
                .build();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder().addExtension(fitnessOptions).build();
        GoogleSignInClient client = GoogleSignIn.getClient(context, signInOptions);
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
