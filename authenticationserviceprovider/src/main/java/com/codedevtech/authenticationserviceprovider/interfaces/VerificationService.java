package com.codedevtech.authenticationserviceprovider.interfaces;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptPhoneVerificationCallback;
import com.google.firebase.auth.PhoneAuthProvider;

public interface VerificationService {

    //for phone verification

    void verifyPhoneCode(String phoneNumber, int secondsToTimeout, AttemptPhoneVerificationCallback attemptPhoneVerificationCallback);

}
