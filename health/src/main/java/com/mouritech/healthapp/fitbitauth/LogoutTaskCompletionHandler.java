package com.mouritech.healthapp.fitbitauth;


public interface LogoutTaskCompletionHandler {
    void logoutSuccess();

    void logoutError(String message);
}
