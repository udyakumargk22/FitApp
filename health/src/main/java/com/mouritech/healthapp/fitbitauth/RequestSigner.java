package com.mouritech.healthapp.fitbitauth;




public interface RequestSigner {

    void signRequest(BasicHttpRequestBuilder builder);

}
