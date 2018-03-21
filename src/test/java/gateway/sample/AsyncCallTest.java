package gateway.sample;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class AsyncCallTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncCallTest.class);
    
    
    @Test
    public void asyncCall() throws InterruptedException {

        CountDownLatch cl = new CountDownLatch(1);
        
        LOGGER.info("Step 1");
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            sleep();
            LOGGER.info("Step 2");
            return "Done";
        });
        
        cf.thenAccept(s -> {
            LOGGER.info("Step 3:  " + s);
            cl.countDown();
        });
        
        cl.await();
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
