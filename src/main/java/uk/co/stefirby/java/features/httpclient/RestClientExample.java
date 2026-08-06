package uk.co.stefirby.java.features.httpclient;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

/**
 * Java 11 shipped the JDK {@link java.net.http.HttpClient}; this is the
 * Spring Framework 7 counterpart — the fluent, synchronous {@code RestClient}
 * (the Boot 4-era replacement for {@code RestTemplate}), fetching the same
 * stage-4 payload with message conversion handled for free. The base URI is
 * a parameter so the example runs against any live instance of this app.
 */
public class RestClientExample {

    /**
     * @param baseUri base URI of a running instance, e.g. {@code http://localhost:8080}
     * @return the top-scorer names served by the stage-4 endpoint
     */
    public static List<String> topScorers(String baseUri) {
        return RestClient.create(baseUri)
                .get()
                .uri("/api/streams/top-scorers")
                .retrieve()
                .body(new ParameterizedTypeReference<List<String>>() {
                });
    }

    public static void main(String[] args) {
        String baseUri = args.length > 0 ? args[0] : "http://localhost:8080";
        System.out.println(topScorers(baseUri));
    }
}
