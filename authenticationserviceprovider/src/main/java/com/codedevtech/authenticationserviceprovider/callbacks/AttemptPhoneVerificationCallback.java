package com.codedevtech.authenticationserviceprovider.callbacks;

public interface AttemptPhoneVerificationCallback {

    //to be called when login fails
    void onAttemptFailed(String errorMessage);

    //to be called when we successfully login
    void onAttemptSuccess(String userAuthProviderId);


    void onCodeSent();

    void onCodeTimeout(String s);


    //sigh do i need this? maybe
    void onErrorOccurred(String errorMessage);

}
