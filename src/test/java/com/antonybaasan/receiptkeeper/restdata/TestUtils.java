package com.antonybaasan.receiptkeeper.restdata;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class TestUtils {
    public static Record createRecord(long id) {
        Record record = new Record();
        record.setId(id);
        return record;
    }

    public static Record createRecord(long id, UserProfile userProfile) {
        Record record = new Record();
        record.setId(id);
        record.setOwner(userProfile);
        return record;
    }

    public static UserProfile createUserProfile(long id) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setFirstName("Antony");
        userProfile.setLastName("Baasan");
        return userProfile;
    }

    static class RequestB {
        public String token;
        public Boolean returnSecureToken;

        public RequestB(String token, Boolean returnSecureToken) {
            this.token = token;
            this.returnSecureToken = returnSecureToken;
        }
    }

    static class ResponseB {
        public String kind;
        public String idToken;
        public String refreshToken;
        public String expiresIn;
    }

    public static String generateToken(String uid) throws FirebaseAuthException {
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=[API_KEY]";
        HttpEntity<RequestB> request = new HttpEntity<>(new RequestB(customToken, true));
        ResponseB r = restTemplate.postForObject(url, request, ResponseB.class);
        return r.idToken;
    }
}
