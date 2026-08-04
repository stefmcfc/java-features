package uk.co.stefirby.java.features.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.stefirby.java.features.concurrency.ThreadInfoExample;
import uk.co.stefirby.java.features.concurrency.ThreadInfoExample.ThreadReport;

/**
 * Java 21 / Spring Boot 4.1: thin REST layer exposing the stage-9 thread
 * report — no feature logic lives here, the handler delegates straight to
 * the {@code concurrency} static method. With
 * {@code spring.threads.virtual.enabled=true} the returned report shows the
 * request itself being served on a virtual thread: Loom's one-property
 * production payoff in Boot 4.
 */
@RestController
@RequestMapping("/api/concurrency")
public class ConcurrencyController {

    @GetMapping("/thread-info")
    public ThreadReport threadInfo() {
        return ThreadInfoExample.currentThreadReport();
    }
}
