/*
 *
 *  * Purpose  ApiConstants.java
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

import retrofit2.http.GET;

interface ApiConstants {

//    /1/user/-/activities/heart/date/{date}/{end-date}/{detail-level}.json
    String BASE_URL = "https://api.fitbit.com";
    String BASE_URL_STRAVA = "https://www.strava.com";

//    String FITBIT_HEART_DATA = "/1/user/-/activities/heart/date/2021-01-01/2021-01-01/1min.json";
    String FITBIT_HEART_DATA = "/1/user/-/activities/heart/date/{fromdate}/{todate}/1min.json";
    String USER_PROFILE = "/1/user/-/profile.json";
    String USER_ACTIVITY = "/1/user/-/activities/date/{actdate}.json";
    String USER_WEIGHT = "/1/user/-/body/log/fat/date/{userweight}.json";
    String USER_SLEEP = "/1.2/user/-/sleep/date/{sleepdate}.json";
    String USER_FOOD_GOALS = "/1/user/-/foods/log/goal.json";


    String STRAVA_TOKEN = "/api/v3/oauth/token";
    String STRAVA_ATHLETE = "/api/v3/athlete";
    String STRAVA_DE_AUTHORIZE = "/api/v3/oauth/deauthorize";
    String STRAVA_ACTIVITIES = "/api/v3/athlete/activities?per_page=30";
    String STRAVA_SINGLE_ACTIVITIES = "/api/v3/activities/5134358874?include_all_efforts=false";
    String STRAVA_GET_CLUBS = "/api/v3/athlete/clubs?per_page=30";
    String STRAVA_GET_STREAMS = "/api/v3/activities/5134358874/streams?keys=distance&key_by_type=true";

}
