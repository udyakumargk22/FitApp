/*
 *
 *  * Purpose  FitbitActivity.java
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

package com.mouritech.healthapp.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.mouritech.healthapp.R;
import com.mouritech.healthapp.callback.FitbitApiResponse;
import com.mouritech.healthapp.client.ApiInterface;
import com.mouritech.healthapp.client.RetrofitClient;
import com.mouritech.healthapp.databinding.ActivityFitbitBinding;
import com.mouritech.healthapp.fitbitauth.AuthenticationHandler;
import com.mouritech.healthapp.fitbitauth.AuthenticationManager;
import com.mouritech.healthapp.fitbitauth.AuthenticationResult;
import com.mouritech.healthapp.fitbitauth.Scope;
import com.mouritech.healthapp.model.fitbitmodel.FitbitProfileModel;
import com.mouritech.healthapp.model.fitbitmodel.HeartModel;

import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FitbitActivity extends AppCompatActivity  implements AuthenticationHandler {

    private static final String TAG = "FitbitActivity";

//    String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMjdHNUwiLCJzdWIiOiI5QjdQNDQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIiLCJleHAiOjE2MTcwODExNzAsImlhdCI6MTYxNjk5NDc3MH0.G5eLqfv0nVLp3k5lRDtECpvEMurt7raLamvEzQWGXIk";
    ApiInterface apiInterface;

    ActivityFitbitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fitbit);

        initUI();

    }
    public void initUI(){


        binding.fitbitLoginBtn.setOnClickListener(v -> onLoginClick());
        binding.fitbitLogoutBtn.setOnClickListener(v -> logoutUser());
        binding.fitbitDataBtn.setOnClickListener(v -> getData());
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (AuthenticationManager.isLoggedIn()) {
            onLoggedIn();
        }
    }

    private void getData(){
        fitbitUserProfile();
        heartRate();
        userActivity();
        getUserWeight();
        userSleeptime();
        userFoodGoals();
    }

    public void onLoggedIn() {

        Log.e("TAG", "onLoggedIn: "+AuthenticationManager.getneratedAccesstoken() );

        apiInterface = RetrofitClient.getClient(AuthenticationManager.getneratedAccesstoken());
//        fitbitUserProfile();
//        heartRate();
//        userActivity();
//        getUserWeight();
//        userSleeptime();
//        userFoodGoals();

        FitbitApiResponse object = new FitbitApiResponse(1);
        object.setCustomObjectListener(new FitbitApiResponse.MyCustomObjectListener() {
            @Override
            public void onObjectReady(String title) {
                Log.e(TAG, "onObjectReady: "+title );
            }
        });

    }




    public void heartRate() {

        Call<HeartModel> call = apiInterface.fitbitHeart("2021-01-01","2021-01-01");
        call.enqueue(new Callback<HeartModel>() {
            @Override
            public void onResponse(Call<HeartModel> call, Response<HeartModel> response) {


            }

            @Override
            public void onFailure(Call<HeartModel> call, Throwable t) {

            }
        });
    }
    public void userFoodGoals() {

        Call<ResponseBody> call = apiInterface.getUserFoodGoals();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "userFoodGoals: "+response.body().string() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void userActivity() {

        Call<ResponseBody> call = apiInterface.getUserActivity("2021-01-01");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "userActivity: "+response.body().string() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void userSleeptime() {

        Call<ResponseBody> call = apiInterface.getUserSleep("2021-03-01");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "userSleeptime: "+response.body().string() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void getUserWeight() {

        Call<ResponseBody> call = apiInterface.getUserWeight("2021-01-01");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "getUserWeight: "+response.body().string() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void fitbitUserProfile() {

        Call<FitbitProfileModel> call = apiInterface.fitbitProfile();
        call.enqueue(new Callback<FitbitProfileModel>() {
            @Override
            public void onResponse(Call<FitbitProfileModel> call, Response<FitbitProfileModel> response) {


                Log.e(TAG, "onResponse: "+response.body().getUser().getDisplayName() );
            }

            @Override
            public void onFailure(Call<FitbitProfileModel> call, Throwable t) {

            }
        });
    }



    public void onLoginClick() {

        AuthenticationManager.login(this);

    }

    public void logoutUser(){
        AuthenticationManager.logout(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
            // Handle other activity results, if needed
        }

    }

    public void onAuthFinished(AuthenticationResult authenticationResult) {


        if (authenticationResult.isSuccessful()) {
            onLoggedIn();
        } else {
            displayAuthError(authenticationResult);
        }
    }

    private void displayAuthError(AuthenticationResult authenticationResult) {
        String message = "";

        switch (authenticationResult.getStatus()) {
            case dismissed:
                message = getString(R.string.login_dismissed);
                break;
            case error:
                message = authenticationResult.getErrorMessage();
                break;
            case missing_required_scopes:
                Set<Scope> missingScopes = authenticationResult.getMissingScopes();
                String missingScopesText = TextUtils.join(", ", missingScopes);
                message = getString(R.string.missing_scopes_error) + missingScopesText;
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.login_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create()
                .show();
    }


}