package ru.nsu.fit.services;

import ru.nsu.fit.services.browser.AdminClientWrapper;
import ru.nsu.fit.services.log.Logger;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpClientUtils {
    private static final String restPath = "http://localhost:8080/rest";

    public static Response getCustomer(String login) {
        Logger.info(String.format("Trying to get info about customer with login %s", login));

        WebTarget webTarget = AdminClientWrapper.getConfiguredClient()
                .target(restPath)
                .path("customers")
                .queryParam("login", login);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Logger.debug("Try to make GET...");
        Response response = invocationBuilder.get();
        logResponse(response);

        return response;
    }

    private static void logResponse(Response response) {
        Logger.info("Status: " + response.getStatus());
        Logger.info("Response: " + response.readEntity(String.class));
    }
}
