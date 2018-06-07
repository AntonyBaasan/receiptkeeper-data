package com.antonybaasan.receiptkeeper.restdata.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Configuration
public class MainConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${firebase.secretfile.class-path}")
    private String firebaseSecretfileClassPath;

    @Value("${firebase.secretfile.file-path}")
    private String firebaseSecretfileFilePath;

    @Value("${firebase.database.url}")
    private String firebaseDatabaseUrl;

    @Bean
    public FirebaseApp firebaseApp() {

        Resource resource = getResource();

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

    private Resource getResource() {

        Resource resource = resourceLoader.getResource("classpath:" + this.firebaseSecretfileClassPath);
        if (resource.exists()) {
            return resource;
        }

        this.createFribaseFileIfNotExists(this.firebaseSecretfileFilePath);
        return resourceLoader.getResource("file:" + this.firebaseSecretfileFilePath);
    }

    private void createFribaseFileIfNotExists(String fileName) {
//        String fileName = "C:/firebase-adminsdk2.json";

        deleteFile(fileName);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("website", "www.websparrow.org2222");

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
