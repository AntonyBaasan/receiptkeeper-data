package com.antonybaasan.receiptkeeper.restdata.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class MainConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${firebase.secretfile.path}")
    private String firebaseSecretfilePath;

    @Value("${firebase.database.url}")
    private String firebaseDatabaseUrl;

    @Bean
    public FirebaseApp firebaseApp() {

        Resource resource = resourceLoader.getResource(this.firebaseSecretfilePath);

        FirebaseOptions options;
        try {
            options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .setDatabaseUrl(this.firebaseDatabaseUrl).build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Bean
//    public CorsConfigurationSource corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:4200");
//        config.addAllowedHeader("*");
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//    }
}
