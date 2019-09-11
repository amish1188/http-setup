package no.kristiania.httpsetup;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import  static org.junit.jupiter.api.Assertions.assertEquals;
public class HttpClientTest {

    @Test
    void shouldReadStatus() throws IOException {
        httpClient client = new httpClient("urlecho.appspot.com", "/echo?body=1234567");
        httpClient.HttpClientResponse httpClientResponse = client.executeRequest();
        assertEquals(200, httpClientResponse.getStatusCode() );
    }

    @Test
    void shouldReadStatusCode() throws IOException {
        httpClient client = new httpClient("urlecho.appspot.com", "/echo");
        httpClient.HttpClientResponse httpClientResponse = client.executeRequest();
        assertEquals(200, httpClientResponse.getStatusCode());
    }

    @Test
    void shouldReadFailureStatusCode() throws IOException {
        httpClient client = new httpClient("urlecho.appspot.com", "/echo?status=401");
        httpClient.HttpClientResponse httpClientResponse = client.executeRequest();
        assertEquals(401, httpClientResponse.getStatusCode());
    }
}



