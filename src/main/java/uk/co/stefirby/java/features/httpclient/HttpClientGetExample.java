package uk.co.stefirby.java.features.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Java 11: {@link java.net.http.HttpClient} — the built-in HTTP/1.1 + HTTP/2
 * client that replaced {@code HttpURLConnection} for outbound calls. Issues a
 * synchronous GET and surfaces the response status and body. The base URI is
 * a parameter so the example runs against any live instance of this app (its
 * stage-4 endpoints make a handy zero-dependency target), not a hardcoded
 * {@code localhost}.
 */
public class HttpClientGetExample {

    /**
     * The two response facts the examples assert on: status code and body.
     *
     * @param statusCode the HTTP status code of the response
     * @param body       the response body as a string
     */
    public record Result(int statusCode, String body) {
    }

    /**
     * @param baseUri base URI of a running instance, e.g. {@code http://localhost:8080}
     * @param path    the path to GET, e.g. {@code /api/streams/top-scorers}
     * @return the response status and body
     */
    public static Result get(String baseUri, String path) throws IOException, InterruptedException {
        // HttpClient became AutoCloseable in Java 21, hence try-with-resources.
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUri + path)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new Result(response.statusCode(), response.body());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String baseUri = args.length > 0 ? args[0] : "http://localhost:8080";
        Result result = get(baseUri, "/api/streams/top-scorers");
        System.out.println("Status: " + result.statusCode());
        System.out.println("Body:   " + result.body());
    }
}
