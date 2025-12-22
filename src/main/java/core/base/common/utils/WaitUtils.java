package core.base.common.utils;


import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.concurrent.Callable;

public class WaitUtils {
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final Duration POLL_INTERVAL = Duration.ofMillis(200);

    public static void retry(Runnable action, int attempts) {
        Throwable last = null;

        for (int i = 0; i < attempts; i++) {
            try {
                new FluentWait<>(new Object())
                        .withTimeout(TIMEOUT)
                        .pollingEvery(POLL_INTERVAL)
                        .until(x -> {
                            action.run();
                            return true;
                        });
                return;
            } catch (Throwable t) {
                last = t;
            }
        }

        throw new RuntimeException("Retry failed after " + attempts + " attempts", last);
    }

    public static <T> T retry(Callable<T> action, int attempts) {
        Throwable last = null;

        for (int i = 0; i < attempts; i++) {
            try {
                return new FluentWait<>(new Object())
                        .withTimeout(TIMEOUT)
                        .pollingEvery(POLL_INTERVAL)
                        .until(x -> {
                            try {
                                return action.call();
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (Throwable t) {
                last = t;
            }
        }

        throw new RuntimeException("Retry failed after " + attempts + " attempts", last);
    }

}

