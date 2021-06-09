/*
 *
 *  * Purpose  RetrofitClient for Call apis
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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit client.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    public static ApiInterface getClient(){
        try {
            okHttpClient = new OkHttpClient().newBuilder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .addInterceptor(new HttpLoggingInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        //}
    } catch (Exception e) {
        e.printStackTrace();
    }
        return retrofit.create(ApiInterface.class);

    }
    public static ApiInterface getClient(String token){

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            })
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .addInterceptor(logging)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        //}
    } catch (Exception e) {
        e.printStackTrace();
    }
        return retrofit.create(ApiInterface.class);

    }
    public static ApiInterface getClientStrava(){

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
                okHttpClient = new OkHttpClient().newBuilder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
//                        .addInterceptor(new HttpLoggingInterceptor())
                    .build();


                retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL_STRAVA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        //}
    } catch (Exception e) {
        e.printStackTrace();
    }
        return retrofit.create(ApiInterface.class);

    }
    public static ApiInterface getClientStrava(String token){

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            })
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .addInterceptor(logging)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL_STRAVA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        //}
    } catch (Exception e) {
        e.printStackTrace();
    }
        return retrofit.create(ApiInterface.class);

    }



}
