package com.schindig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by Agronis on 12/16/15.
 */
@Configuration
public class AppConfig {

//    @Bean
////    @Profile("heroku")
//    public DataSource postgresDataSource() {
//        String databaseUrl = System.getenv("DATABASE_URL");
//        if (databaseUrl==null) {
//            databaseUrl = "postgres://ehkcldttqajcnr:wuTOmypaVaMyl86f8bpaaXwkGx@ec2-54-83-10-210.compute-1.amazonaws.com:5432/dck8nftm2bukr4";
//        }
//        URI dbUri;
//        try {
//            dbUri = new URI(databaseUrl);
//
//        }
//        catch (URISyntaxException e) {
//            e.getMessage();
//            e.printStackTrace();
//            return null;
//        }
//
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
//                + dbUri.getPort() + dbUri.getPath();
//
//        org.apache.tomcat.jdbc.pool.DataSource dataSource
//                = new org.apache.tomcat.jdbc.pool.DataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl(dbUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setTestOnBorrow(true);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setTestOnReturn(true);
//        dataSource.setValidationQuery("SELECT 1");
//        return dataSource;
//    }


    @Bean
    public JavaMailSenderImpl javaMailSenderImpl(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("schindig.app@gmail.com");
        mailSender.setPassword("getjiggywithit");
        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.debug", "true");
        return mailSender;
    }




}
