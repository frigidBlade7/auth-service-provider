package com.codedevtech.authenticationserviceprovider.interface_implementations;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.callbacks.AttemptPhoneVerificationCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.VerificationService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebaseVerificationService implements VerificationService {

    private static final String TAG = "FirebaseVerificationSer";

    private FirebaseAuthenticationService firebaseAuthenticationService;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    public FirebaseVerificationService(FirebaseAuthenticationService firebaseAuthenticationService) {
        this.firebaseAuthenticationService = firebaseAuthenticationService;
    }

    public PhoneAuthProvider.ForceResendingToken getForceResendingToken() {
        return forceResendingToken;
    }

    public void setForceResendingToken(PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        this.forceResendingToken = forceResendingToken;
    }

    @Override
    public void verifyPhoneCode(final String phoneNumber, int secondsToTimeout,
                                final AttemptPhoneVerificationCallback attemptPhoneVerificationCallback) {
        verifyPhoneCode(phoneNumber, secondsToTimeout, attemptPhoneVerificationCallback, null);
    }

    public void verifyPhoneCode(final String phoneNumber, int secondsToTimeout,
                                final AttemptPhoneVerificationCallback attemptPhoneVerificationCallback,
                                Activity activity) {
        PhoneAuthOptions.Builder optionsBuilder = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneNumber)
                .setTimeout((long) secondsToTimeout, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        firebaseAuthenticationService.attemptLoginWithCredential(phoneAuthCredential, new AttemptLoginCallback() {
                            @Override
                            public void onAttemptLoginFailed(String errorMessage) {
                                attemptPhoneVerificationCallback.onAttemptFailed("at login: "+errorMessage);
                            }

                            @Override
                            public void onAttemptLoginSuccess(String userAuthProviderId) {
                                attemptPhoneVerificationCallback.onAttemptSuccess(userAuthProviderId);
                            }

                            @Override
                            public void onErrorOccurred(String errorMessage) {
                                attemptPhoneVerificationCallback.onErrorOccurred("at login: "+errorMessage);
                            }
                        });
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        attemptPhoneVerificationCallback.onAttemptFailed(e.getLocalizedMessage());
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        attemptPhoneVerificationCallback.onCodeTimeout(s);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        attemptPhoneVerificationCallback.onCodeSent(s, forceResendingToken);
                    }
                });

        if (activity != null) {
            optionsBuilder.setActivity(activity);
        }

        if (getForceResendingToken() != null) {
            optionsBuilder.setForceResendingToken(getForceResendingToken());
        }

        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build());
    }
}
