package uk.co.stefirby.java.features.httpclient;

import java.util.List;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Java 11 shipped the JDK {@link java.net.http.HttpClient}; this example
 * completes the client comparison with Spring Framework 7's declarative HTTP
 * interface support: {@link HttpServiceProxyFactory} turns the annotated
 * {@link TopScorersExchange} interface into a working client backed by a
 * {@code RestClient}. The base URI is a parameter so the example runs against
 * any live instance of this app.
 */
public class HttpExchangeExample {

    /**
     * @param baseUri base URI of a running instance, e.g. {@code http://localhost:8080}
     * @return the top-scorer names served by the stage-4 endpoint
     */
    public static List<String> topScorers(String baseUri) {
        RestClientAdapter adapter = RestClientAdapter.create(RestClient.create(baseUri));
        TopScorersExchange client = HttpServiceProxyFactory.builderFor(adapter)
                .build()
                .createClient(TopScorersExchange.class);
        return client.topScorers();
    }

    public static void main(String[] args) {
        String baseUri = args.length > 0 ? args[0] : "http://localhost:8080";
        System.out.println(topScorers(baseUri));
    }
}
