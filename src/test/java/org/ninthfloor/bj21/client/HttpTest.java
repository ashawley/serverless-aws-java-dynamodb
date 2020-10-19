package org.ninthfloor.bj21.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.junit.Test;

import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HttpTest
{
    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<Object> httpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @SuppressWarnings("unchecked")
    @Before
    public void init()
    {
        httpClient = mock(HttpClient.class); // new HttpClientMock();
        httpResponse = (HttpResponse<Object>) mock(HttpResponse.class);
        httpHeaders = mock(HttpHeaders.class);
    }

    @Test
    public void testSend() throws IOException, InterruptedException
    {
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("dummy");
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.headers()).thenReturn(httpHeaders);
        when(httpHeaders.firstValue("Content-Type")).thenReturn(Optional.of("text/html"));
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://openjdk.java.net/"))
            .build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Optional<String> contentType = httpResponse.headers().firstValue("Content-Type");
        assertEquals("text/html", contentType.get());
        assertEquals("dummy", response.body());
    }

    @Test
    public void testSendAsync() throws IOException, InterruptedException, ExecutionException
    {
        when(httpClient.sendAsync(any(), any())).thenReturn(CompletableFuture.completedFuture(httpResponse));
        when(httpResponse.body()).thenReturn("dummy");
        when(httpResponse.headers()).thenReturn(httpHeaders);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpHeaders.firstValue("Content-Type")).thenReturn(Optional.of("text/html"));
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://openjdk.java.net/"))
            .build();
        HttpResponse<String> httpResponse = httpClient.sendAsync(request, BodyHandlers.ofString()).get();
        assertEquals(200, httpResponse.statusCode());
        Optional<String> contentType = httpResponse.headers().firstValue("Content-Type");
        assertEquals("text/html", contentType.get());
        assertEquals("dummy", httpResponse.body());
        assertEquals("dummy", httpResponse.body());
    }
}
