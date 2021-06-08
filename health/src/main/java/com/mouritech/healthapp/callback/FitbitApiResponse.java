/*
 *
 *  * Purpose  MyCustomObject.java
 *
 *  Copyright  2021  MouriTech
 *
 *  @author  udayv
 *
 *  Created on Apr 06, 2021
 *
 *  Modified on Apr 06, 2021
 *
 */

package com.mouritech.healthapp.callback;

import android.util.Log;

import com.mouritech.healthapp.client.ApiInterface;
import com.mouritech.healthapp.client.RetrofitClient;
import com.mouritech.healthapp.fitbitauth.AuthenticationManager;
import com.mouritech.healthapp.model.fitbitmodel.HeartModel;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FitbitApiResponse {

    private static final String TAG = "MyCustomObject";

    public interface MyCustomObjectListener {
        public void onObjectReady(String title);
    }

    private MyCustomObjectListener listener;


    ApiInterface apiInterface;

    // Constructor where listener events are ignored
    public FitbitApiResponse(int type) {
        apiInterface = RetrofitClient.getClient(AuthenticationManager.getneratedAccesstoken());
        this.listener = null;

        if (type == 1)
        userProfile();
        else if (type == 2)
            userGoals();
        else if (type == 3)
            getUserWeight();
        else if (type == 4)
            userSleeptime();
        else if (type == 5)
            heartRate();
        else if (type == 6)
            userFoodGoals();
        else if (type == 7)
            userActivity();
        else
            noData();



    }
    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    public void userGoals() {
        Call<ResponseBody> call = apiInterface.getUserFoodGoals();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void userProfile() {
        Call<ResponseBody> call = apiInterface.fitbitProfiledata();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
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
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
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
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void heartRate() {

        Call<ResponseBody> call = apiInterface.fitbitHeartData("2021-01-01","2021-01-01");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void userFoodGoals() {

        Call<ResponseBody> call = apiInterface.getUserFoodGoals();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
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
                    if (listener != null)
                        listener.onObjectReady(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void noData(){
        try {
            if (listener != null)
                listener.onObjectReady("No Data");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}