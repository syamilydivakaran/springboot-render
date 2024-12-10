package com.syamily.book.controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvRunner implements CommandLineRunner {

    private final Dotenv dotenv;

    public EnvRunner(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Override
    public void run(String... args) throws Exception {
        String mongoUri = dotenv.get("MONGO_DB_URI");
        String mongoDbName = dotenv.get("MONGO_DB_NAME");
        String frontendUrl = dotenv.get("FRONDEND_URL");

        if (mongoUri == null || mongoDbName == null || frontendUrl == null) {
            System.err.println("One or more environment variables are missing.");
            if (mongoUri == null) System.err.println("MONGO_DB_URI not found.");
            if (mongoDbName == null) System.err.println("MONGO_DB_NAME not found.");
            if (frontendUrl == null) System.err.println("FRONDEND_URL not found.");
        } else {
            System.out.println("MongoDB URI: " + mongoUri);
            System.out.println("MongoDB Database Name: " + mongoDbName);
            System.out.println("Frontend URL: " + frontendUrl);
        }
    }


}
