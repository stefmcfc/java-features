package uk.co.stefirby.java.features.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Java 11: {@link HttpClient#sendAsync} — the non-blocking counterpart to
 * {@link HttpClientGetExample#get}. It returns a {@link CompletableFuture}
 * immediately and completes it once the response arrives, so the calling
 * thread is free to keep working (or dispatch further requests) rather
 * than sit blocked in {@code send()}. The base URI is a parameter so the
 * example runs against any live instance of this app.
 */
public class HttpClientSendAsyncExample {

    /**
     * @param baseUri base URI of a running instance, e.g. {@code http://localhost:8080}
     * @param path    the path to GET, e.g. {@code /api/streams/top-scorers}
     * @return a future that completes with the response status and body
     *         once it arrives, without blocking the calling thread
     */
    public static CompletableFuture<HttpClientGetExample.Result> getAsync(String baseUri, String path) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUri + path)).GET().build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> new HttpClientGetExample.Result(response.statusCode(), response.body()));
    }

    public static void main(String[] args) throws Exception {
        String baseUri = args.length > 0 ? args[0] : "http://localhost:8080";
        CompletableFuture<HttpClientGetExample.Result> future = getAsync(baseUri, "/api/streams/top-scorers");
        System.out.println("Request dispatched; calling thread is free while it completes.");
        HttpClientGetExample.Result result = future.get();
        System.out.println("Status: " + result.statusCode());
        System.out.println("Body:   " + result.body());
    }
}
