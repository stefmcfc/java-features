package uk.co.stefirby.java.features.concurrency;

/**
 * Java 21: {@link Thread#isVirtual()} shipped alongside virtual threads
 * (JEP 444) and reports whether the calling thread is virtual. The report
 * this class produces backs the {@code /api/concurrency/thread-info}
 * endpoint, demonstrating that with
 * {@code spring.threads.virtual.enabled=true} Spring Boot serves HTTP
 * requests on virtual threads.
 */
public class ThreadInfoExample {

    /**
     * A snapshot of the thread the report was taken on.
     *
     * @param threadName the executing thread's name
     * @param virtual    whether the executing thread is a virtual thread
     */
    public record ThreadReport(String threadName, boolean virtual) {
    }

    /**
     * @return the calling thread's name and virtual-ness
     */
    public static ThreadReport currentThreadReport() {
        Thread current = Thread.currentThread();
        return new ThreadReport(current.getName(), current.isVirtual());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread:    " + currentThreadReport());
        Thread.ofVirtual().name("demo-virtual")
                .start(() -> System.out.println("virtual thread: " + currentThreadReport()))
                .join();
    }
}
