package com.antonybaasan.receiptkeeper.restdata.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class FbTokenValidator {

    public FbUserInfo validate(String token) {

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return new FbUserInfo(decodedToken.getUid(), decodedToken.getName(), decodedToken.getEmail(), token);
        } catch (Exception e) {
//            e.printStackTrace();
            //TODO: log
            System.out.print(e.getMessage());
        }
        return null;
    }
}
