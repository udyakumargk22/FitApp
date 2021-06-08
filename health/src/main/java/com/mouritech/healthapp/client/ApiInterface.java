/*
 *
 *  * Purpose  ApiInterface.java
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

package com.mouritech.healthapp.client;

import com.mouritech.healthapp.model.fitbitmodel.FitbitProfileModel;
import com.mouritech.healthapp.model.fitbitmodel.HeartModel;
import com.mouritech.healthapp.model.stravamodel.StravaToken;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(ApiConstants.FITBIT_HEART_DATA)
    Call<HeartModel> fitbitHeart(@Path("fromdate")String fromdate,@Path(("todate")) String todate);

    @GET(ApiConstants.USER_PROFILE)
    Call<FitbitProfileModel> fitbitProfile();

    @GET(ApiConstants.USER_PROFILE)
    Call<ResponseBody> fitbitProfiledata();

    @GET(ApiConstants.FITBIT_HEART_DATA)
    Call<ResponseBody> fitbitHeartData(@Path("fromdate")String fromdate,@Path(("todate")) String todate);

//    https://api.fitbit.com/1/user/-/activities/date/20-01-2021.json

    @GET(ApiConstants.USER_ACTIVITY)
    Call<ResponseBody> getUserActivity(@Path("actdate") String actdate);

     @GET(ApiConstants.USER_WEIGHT)
    Call<ResponseBody> getUserWeight(@Path("userweight") String actdate);

     @GET(ApiConstants.USER_SLEEP)
    Call<ResponseBody> getUserSleep(@Path("sleepdate") String sleepdate);

     @GET(ApiConstants.USER_FOOD_GOALS)
    Call<ResponseBody> getUserFoodGoals();



    @POST(ApiConstants.STRAVA_TOKEN)
    @FormUrlEncoded
    Call<StravaToken> stravaToken(
            @Field("client_id") int clientID,
            @Field("client_secret") String clientSecret,
            @Field("code") String code,
            @Field("grant_type") String grant_type);

    @POST(ApiConstants.STRAVA_DE_AUTHORIZE)
    Call<Void> deauthorize();

    @GET(ApiConstants.STRAVA_ATHLETE)
    Call<ResponseBody> getAthlete();

     @GET(ApiConstants.STRAVA_ACTIVITIES)
    Call<ResponseBody> getActivities();

     @GET(ApiConstants.STRAVA_SINGLE_ACTIVITIES)
    Call<ResponseBody> getSingleActivity();

     @GET(ApiConstants.STRAVA_GET_CLUBS)
    Call<ResponseBody> getClubs();

     @GET(ApiConstants.STRAVA_GET_STREAMS)
    Call<ResponseBody> getStreams();


}
