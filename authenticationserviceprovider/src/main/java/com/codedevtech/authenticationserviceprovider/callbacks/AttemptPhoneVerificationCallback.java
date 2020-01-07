package com.codedevtech.authenticationserviceprovider.callbacks;

import com.google.firebase.auth.PhoneAuthProvider;

public interface AttemptPhoneVerificationCallback {

    //to be called when login fails
    void onAttemptFailed(String errorMessage);

    //to be called when we successfully login
    void onAttemptSuccess(String userAuthProviderId);


    void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken);

    void onCodeTimeout(String verificationId);


    //sigh do i need this? maybe
    void onErrorOccurred(String errorMessage);

}
