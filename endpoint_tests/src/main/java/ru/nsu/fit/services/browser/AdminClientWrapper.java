package ru.nsu.fit.services.browser;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class AdminClientWrapper {
    public AdminClientWrapper() {
    }

    public static Client getConfiguredClient() {
        ClientConfig clientConfig = new ClientConfig();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "setup");
        clientConfig.register(feature);

        clientConfig.register(JacksonFeature.class);

        return ClientBuilder.newClient(clientConfig);
    }
}