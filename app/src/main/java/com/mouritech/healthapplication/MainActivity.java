package com.mouritech.healthapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.mouritech.healthapp.callback.FitbitApiResponse;
import com.mouritech.healthapp.callback.StravaApiResponse;
import com.mouritech.healthapp.fitbitauth.AuthenticationHandler;
import com.mouritech.healthapp.fitbitauth.AuthenticationManager;
import com.mouritech.healthapp.fitbitauth.AuthenticationResult;
import com.mouritech.healthapp.fitbitauth.Scope;
import com.mouritech.healthapp.home.GoogleFitActivity;
import com.mouritech.healthapp.home.GoogleFitData;
import com.mouritech.healthapp.home.StravaActivity;
import com.mouritech.healthapplication.databinding.ActivityMainBinding;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements AuthenticationHandler {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initUI();
    }
    private void initUI(){
        binding.fitbitLoginBtn.setOnClickListener(v -> fitbitLogin());
        binding.fitbitDataBtn.setOnClickListener(v -> fitbitData());
        binding.fitbitLogoutBtn.setOnClickListener(v -> fitbitLogout());

        binding.stravaLoginBtn.setOnClickListener(v -> stravaLogin());
        binding.stravaDataBtn.setOnClickListener(v -> stravaData());
        binding.stravaLogoutBtn.setOnClickListener(v -> stravaLogout());

        binding.googleFitloginBtn.setOnClickListener(v -> googleFitLogin());
        binding.googleFitdataBtn.setOnClickListener(v -> googleFitData());
        binding.googleFitlogoutBtn.setOnClickListener(v -> googleFitLogout());

    }

    private void googleFitLogin(){
        startActivity(new Intent(this, GoogleFitActivity.class));

    }
    private void googleFitData(){
        GoogleFitData googleFitData = new GoogleFitData();
        googleFitData.accessGoogleFit(this);
    }
    private void googleFitLogout(){
        GoogleFitData googleFitData = new GoogleFitData();
        googleFitData.logout(this);
    }



    private void fitbitLogin(){
        AuthenticationManager.login(this);
    }
    private void fitbitLogout(){
        AuthenticationManager.logout(this);
    }
    private void fitbitData(){
        FitbitApiResponse object = new FitbitApiResponse(1);
        object.setCustomObjectListener(title -> Log.e(TAG, "onObjectReady: "+title ));

         FitbitApiResponse object2 = new FitbitApiResponse(2);
        object2.setCustomObjectListener(title -> Log.e(TAG, "onObjectReady: "+title ));

         FitbitApiResponse object3 = new FitbitApiResponse(3);
        object3.setCustomObjectListener(title -> Log.e(TAG, "onObjectReady: "+title ));

         FitbitApiResponse object4 = new FitbitApiResponse(4);
        object4.setCustomObjectListener(title -> Log.e(TAG, "onObjectReady: "+title ));


    }

    private void stravaLogin(){
        startActivity(new Intent(this, StravaActivity.class));
    }
    private void stravaData(){
        StravaApiResponse response = new StravaApiResponse(this,1);
        response.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });

        StravaApiResponse response2 = new StravaApiResponse(this,2);
        response2.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });

        StravaApiResponse response3 = new StravaApiResponse(this,3);
        response3.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });

        StravaApiResponse response4 = new StravaApiResponse(this,4);
        response4.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });

        StravaApiResponse response5 = new StravaApiResponse(this,5);
        response5.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });


    }
    private void stravaLogout(){
        StravaApiResponse response = new StravaApiResponse(this,10);
        response.setCustomObjectListener(title -> {
            Log.e(TAG, "getRunningRace: "+title );
        });
    }


    public void onAuthFinished(AuthenticationResult authenticationResult) {
        if (authenticationResult.isSuccessful()) {
            Log.e(TAG, "onAuthFinished: Succcess" );
        } else {
            displayAuthError(authenticationResult);
        }
    }
    private void displayAuthError(AuthenticationResult authenticationResult) {
        String message = "";

        switch (authenticationResult.getStatus()) {
            case dismissed:
                message = getString(com.mouritech.healthapp.R.string.login_dismissed);
                break;
            case error:
                message = authenticationResult.getErrorMessage();
                break;
            case missing_required_scopes:
                Set<Scope> missingScopes = authenticationResult.getMissingScopes();
                String missingScopesText = TextUtils.join(", ", missingScopes);
                message = getString(com.mouritech.healthapp.R.string.missing_scopes_error) + missingScopesText;
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle(com.mouritech.healthapp.R.string.login_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create()
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
            // Handle other activity results, if needed
        }

    }

}