package com.mouritech.healthapp.fitbitauth;


public interface UrlChangeHandler {
    void onUrlChanged(String newUrl);
    void onLoadError(int errorCode, CharSequence description);
}
