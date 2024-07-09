package com.buddy.chat.config;

import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import io.jsonwebtoken.io.IOException;

@Configuration
public class GoogleConfig {
    @Value("${app.google.client_id}")
    private static String CLIENT_ID;

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier() throws GeneralSecurityException, IOException {
        System.out.println("CLIENT_ID " + CLIENT_ID);
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        return new GoogleIdTokenVerifier.Builder(transport,jsonFactory)
                .setAudience(Collections.singletonList("1011383604442-790l3f5c40eksnfek6pp9mfk4scjg17n.apps.googleusercontent.com"))
                .build( );
    }
}