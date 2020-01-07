package com.codedevtech.authenticationserviceprovider.interfaces;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptPhoneVerificationCallback;

public interface VerificationService {

    //for phone verification

    void verifyPhoneCode(String phoneNumber, int secondsToTimeout, AttemptPhoneVerificationCallback attemptPhoneVerificationCallback);

}
