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

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.widget.Toast;

import com.mouritech.healthapp.client.ApiInterface;
import com.mouritech.healthapp.client.RetrofitClient;
import com.mouritech.healthapp.fitbitauth.AccessToken;
import com.mouritech.healthapp.fitbitauth.AuthenticationManager;
import com.mouritech.healthapp.fitbitauth.SecurePreferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StravaApiResponse {


    private static final String TAG = "StravaApiResponse";


    public interface MyCustomObjectListener {
        public void onObjectReady(String data);
    }

    private MyCustomObjectListener listener;


    ApiInterface apiInterface;
    private static SecurePreferences preferences;
    private static final String SECURE_KEY = "CVPdQNAT6fBI4rrPLEn9x0+UV84DoqLFiNHpKOPLRW0=";
    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN";

    public StravaApiResponse(Context context,int type) {
        preferences = new SecurePreferences(context, "STRAVA_AUTHENTICATION_PREFERENCES", SECURE_KEY, true);
        apiInterface = RetrofitClient.getClientStrava(preferences.getString(AUTH_TOKEN_KEY));

        this.listener = null;
        
        String token = preferences.getString(AUTH_TOKEN_KEY);
        if (token != null && !TextUtils.isEmpty(token)) {

            if (type == 1)
                loadDataAsync();
            else if (type == 2)
                getActivities();
            else if (type == 3)
                getSingleActivity();
            else if (type == 4)
                getClubs();
            else if (type == 5)
                getStreams();
            else if (type == 10)
                logout();

        }else {
            Toast.makeText(context, "Please Login to Application", Toast.LENGTH_SHORT).show();
        }

    }
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    public void loadDataAsync() {
        Call<ResponseBody> call = apiInterface.getAthlete();
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
    private void getActivities(){
        Call<ResponseBody> call = apiInterface.getActivities();
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
    private void getSingleActivity(){
        Call<ResponseBody> call = apiInterface.getSingleActivity();
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
    private void getClubs(){
        Call<ResponseBody> call = apiInterface.getClubs();
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
    private void getStreams(){
        Call<ResponseBody> call = apiInterface.getStreams();
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


    private void logout(){
        preferences.clear();
        WebStorage.getInstance().deleteAllData();

        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        Call<Void> call = apiInterface.deauthorize();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                try {
                    Log.e(TAG, "onResponse: "+response.body() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}