package com.antonybaasan.receiptkeeper.restdata;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

class RequestB {
    public String token;
    public Boolean returnSecureToken;

    public RequestB(String token, Boolean returnSecureToken) {
        this.token = token;
        this.returnSecureToken = returnSecureToken;
    }
}

class ResponseB {
    public String kind;
    public String idToken;
    public String refreshToken;
    public String expiresIn;
}

public class FirebaseTestUtils {
    private static String url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=%s";

    public static String generateToken(String firebaseKey, String uid) throws FirebaseAuthException {
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<RequestB> request = new HttpEntity<>(new RequestB(customToken, true));
        ResponseB r = restTemplate.postForObject(String.format(url, firebaseKey), request, ResponseB.class);
        return r.idToken;
    }
}
