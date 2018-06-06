//package com.antonybaasan.receiptkeeper.restdata.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomRestConfig extends RepositoryRestConfigurerAdapter {
//    @Value("${base.path}")
//    private String basePath;
//
//    @Override
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        super.configureRepositoryRestConfiguration(config);
//        config.setBasePath(this.basePath);
//    }
//}
