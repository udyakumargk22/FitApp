/*
 *
 *  * Purpose  StravaToken.java
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

package com.mouritech.healthapp.model.stravamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StravaToken {

    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_at")
    @Expose
    private Integer expiresAt;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public String toString() {
        return "StravaToken{" +
                "tokenType='" + tokenType + '\'' +
                ", expiresAt=" + expiresAt +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
