package com.antonybaasan.receiptkeeper.restdata.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class MainConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public FirebaseApp firebaseApp() {

        Resource resource = resourceLoader.getResource("classpath:firebase-adminsdk.json");

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .setDatabaseUrl("https://angularattack2108.firebaseio.com/").build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
