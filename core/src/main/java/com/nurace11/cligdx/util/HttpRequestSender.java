package com.nurace11.cligdx.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class HttpRequestSender {
    HttpRequest httpRequest;
    HttpClient httpClient;
    HttpRequest.Builder httpRequestBuilder;

    static public final String DEFAULT_API_URI = "http://localhost:8080/api/v1/drones";

    private final String base64AuthorizationHeader;

    public HttpRequestSender() {


        byte[] bytes = Base64.getEncoder().encode("admin:admin".getBytes());
        StringBuilder base64StringBuilder = new StringBuilder();
        for (byte b : bytes ) {
            base64StringBuilder.append((char) b);
        }

        base64AuthorizationHeader = base64StringBuilder.toString();

        httpRequestBuilder = HttpRequest.newBuilder();

        httpClient = HttpClient.newHttpClient();

    }

    public String sendHttpRequest(String uri) {
        String res = "";
        if (uri.length() == 0) {
            res = DEFAULT_API_URI;
        } else {
            res = uri;
        }

        try {
            httpRequest = httpRequestBuilder
                .uri(URI.create(res))
                .header("Authorization", base64AuthorizationHeader)
                .build();
            HttpResponse<String> getResponse = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(getResponse);
            System.out.println("body: " + getResponse.body());
            return getResponse.body();
        } catch (IOException e) {
            System.out.println(e);
            return e.toString();
        } catch (InterruptedException e) {
            System.out.println(e);
            return e.toString();
        } catch (Exception e) {
            System.out.println(e);
            return e.toString();
        }

    }
}
