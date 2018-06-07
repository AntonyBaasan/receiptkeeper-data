package com.antonybaasan.receiptkeeper.restdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
@PropertySource("classpath:firebase-adminsdk.properties")
public class FirebaseAdminConfig
{
    @Autowired
    public Environment env;

    private String[] keys = new String[]{
            "type",
            "project_id",
            "private_key_id",
            "private_key",
            "client_email",
            "client_id",
            "auth_uri",
            "token_uri",
            "auth_provider_x509_cert_url",
            "client_x509_cert_url"
    };

    @Bean
    public FirebaseAdmin firebaseAdmin(){
        FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
        Arrays.stream(this.keys).forEach(k->{
            firebaseAdmin.values.put(k, env.getProperty(k));
        });
        return firebaseAdmin;
    }
}

