package com.antonybaasan.receiptkeeper.restdata;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;

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
}
