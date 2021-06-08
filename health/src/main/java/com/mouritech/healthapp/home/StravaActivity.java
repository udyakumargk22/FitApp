/*
 *
 *  * Purpose  StravaActivity.java
 *
 *  Copyright  2021  MouriTech
 *
 *  @author  udayv
 *
 *  Created on Apr 14, 2021
 *
 *  Modified on Apr 14, 2021
 *
 */

package com.mouritech.healthapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mouritech.healthapp.R;
import com.mouritech.healthapp.callback.FitbitApiResponse;
import com.mouritech.healthapp.callback.StravaApiResponse;
import com.mouritech.healthapp.client.ApiInterface;
import com.mouritech.healthapp.client.RetrofitClient;
import com.mouritech.healthapp.client.StravaConstants;
import com.mouritech.healthapp.databinding.ActivityStravaBinding;
import com.mouritech.healthapp.fitbitauth.AccessToken;
import com.mouritech.healthapp.fitbitauth.AuthenticationManager;
import com.mouritech.healthapp.fitbitauth.SecurePreferences;
import com.mouritech.healthapp.model.stravamodel.StravaToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StravaActivity extends AppCompatActivity {

    private static final String SECURE_KEY = "CVPdQNAT6fBI4rrPLEn9x0+UV84DoqLFiNHpKOPLRW0=";

    private static final String TAG = "StravaActivity";

    ActivityStravaBinding binding;
    ApiInterface apiInterface,apiInterfaceToken;

    String token = "";

    private static SecurePreferences preferences;
    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_strava);

        initUI();
    }
    private void initUI(){
        preferences = new SecurePreferences(this, "STRAVA_AUTHENTICATION_PREFERENCES", SECURE_KEY, true);

        apiInterface = RetrofitClient.getClientStrava();
        binding.stravaLoginBtn.setOnClickListener(v -> configureWebViewClient());
        binding.stravaLogoutBtn.setOnClickListener(v -> logout(""));
        binding.stravaDataBtn.setOnClickListener(v -> getAthlete());
        binding.stravaActBtn.setOnClickListener(v -> getActivities());

        configureWebViewClient();

    }



    private void configureWebViewClient() {
        binding.stravaWv.setVisibility(View.VISIBLE);
        binding.stravaWv.getSettings().setJavaScriptEnabled(true);
        binding.stravaWv.getSettings().setUserAgentString("Mozilla/5.0 Google");

        binding.stravaWv.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrl(Uri.parse(url)) || super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return handleUrl(uri) || super.shouldOverrideUrlLoading(view, request);
            }

            private boolean handleUrl(Uri uri) {
                String redirectURL = "https://developers.strava.com/";
                if(uri.toString().startsWith(redirectURL)) {
                    String code = uri.getQueryParameter("code");
                    return makeResult(code);
                }
                return false;
            }

            private boolean makeResult(String code) {
                if(code != null && !code.isEmpty()) {

                    Log.e(TAG, "makeResult:code-----> "+code );
                    getToken(code);
//                    Intent result = new Intent();
//                    result.putExtra(RESULT_CODE, code);
//                    setResult(RESULT_OK, result);
//                    finish();
                    return true;
                }
                finish();
                return false;
            }
        });




        Uri intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", "64287")
                .appendQueryParameter("redirect_uri", "https://developers.strava.com/")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("approval_prompt", "auto")
                .appendQueryParameter("grant_type", "refresh_token")
                .appendQueryParameter("scope", "activity:read")
//                .appendQueryParameter("scope", "activity:write,read_all")
                .build();

        Log.e(TAG, "loadLoginURL: "+intentUri.toString() );



        binding.stravaWv.loadUrl(intentUri.toString());
    }


    private void clearWeb(){
        WebStorage.getInstance().deleteAllData();

        // Clear all the cookies
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

//        binding.stravaWv.clearCache(true);
//        binding.stravaWv.clearFormData();
//        binding.stravaWv.clearHistory();
//        binding.stravaWv.clearSslPreferences();
    }


    private void getToken(String code){

        Call<StravaToken> call = apiInterface.stravaToken(StravaConstants.STRAVA_CLIENT_ID,StravaConstants.STRAVA_CLIENT_SECRET,code,StravaConstants.STRAVA_GRANT_TYPE);
        call.enqueue(new Callback<StravaToken>() {
            @Override
            public void onResponse(Call<StravaToken> call, Response<StravaToken> response) {

                finish();

                binding.stravaWv.setVisibility(View.GONE);
                try {
                    Log.e(TAG, "onResponse: "+response.body() );
                    Log.e(TAG, "onResponse: "+response.body().getAccessToken() );
                    token = response.body().getAccessToken();
                    preferences.put(AUTH_TOKEN_KEY, token);
//                    getAthlete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StravaToken> call, Throwable t) {

            }
        });

    }
    private void getAthlete(){

        StravaApiResponse response = new StravaApiResponse(this,1);
        response.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });

//        apiInterfaceToken = RetrofitClient.getClientStrava(token);
//        Call<ResponseBody> call = apiInterfaceToken.getAthlete();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                try {
//                    Log.e(TAG, "onResponse: "+response.body().string() );
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

    }
    private void getActivities(){
        StravaApiResponse response = new StravaApiResponse(this,2);
        response.setCustomObjectListener(data -> {
            Log.e(TAG, "getActivities: "+data );
        });

        getSingleActivity();
        getClubs();
        getStreams();
    }
    private void getSingleActivity(){
        StravaApiResponse response = new StravaApiResponse(this,3);
        response.setCustomObjectListener(data -> {
            Log.e(TAG, "getSingleActivity: "+data );
        });
    }
    private void getClubs(){
        StravaApiResponse response = new StravaApiResponse(this,4);
        response.setCustomObjectListener(data -> {
            Log.e(TAG, "getClubs: "+data );
        });
    }
    private void getStreams(){
        StravaApiResponse response = new StravaApiResponse(this,5);
        response.setCustomObjectListener(data -> {
            Log.e(TAG, "getStreams: "+data );
        });
    }

    private void getRunningRace(){

        StravaApiResponse response = new StravaApiResponse(this,1);
        response.setCustomObjectListener(new StravaApiResponse.MyCustomObjectListener() {
            @Override
            public void onObjectReady(String data) {
                Log.e(TAG, "onObjectReady: "+data );
            }
        });


        apiInterfaceToken = RetrofitClient.getClientStrava(token);
        Call<ResponseBody> call = apiInterfaceToken.getAthlete();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Log.e(TAG, "onResponse: "+response.body().string() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    private void logout(String code){
        clearWeb();
        apiInterfaceToken = RetrofitClient.getClientStrava(token);
        Call<Void> call = apiInterfaceToken.deauthorize();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                binding.stravaWv.setVisibility(View.GONE);
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