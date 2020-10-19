package org.ninthfloor.bj21.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class Http
{
    public static void main(String[] args) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(java.time.Duration.ofSeconds(20))
            .build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://openjdk.java.net/"))
            .build();
        HttpResponse<String> response =
            client.send(request, BodyHandlers.ofString());
        assert 200 == response.statusCode();
        Optional<String> contentType = response.headers().firstValue("Content-Type");
        assert contentType.get().startsWith("text/html");
        assert response.body().startsWith("<!DOCTYPE html");
    }

    public static void main2(String[] args) throws IOException, InterruptedException, ExecutionException{
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(java.time.Duration.ofSeconds(20))
            .build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://openjdk.java.net/"))
            .build();
        HttpResponse<String> response =
            client.sendAsync(request, BodyHandlers.ofString()).get();
        assert 200 == response.statusCode();
        Optional<String> contentType = response.headers().firstValue("Content-Type");
        assert contentType.get().startsWith("text/html");
        assert response.body().startsWith("<!DOCTYPE html");
    }
}
