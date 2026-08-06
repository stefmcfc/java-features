package uk.co.stefirby.java.features.httpclient;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;

/**
 * Java 11 shipped the imperative JDK {@link java.net.http.HttpClient}; this
 * is the fully declarative end of the client spectrum — a Spring Framework 7
 * {@code @HttpExchange}-style interface whose implementation is generated at
 * runtime by {@link HttpExchangeExample}. The method maps one-to-one onto the
 * stage-4 endpoint it calls.
 */
public interface TopScorersExchange {

    @GetExchange("/api/streams/top-scorers")
    List<String> topScorers();
}
