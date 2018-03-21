package gateway.sample;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

@Ignore
public class NonBlockingIoTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NonBlockingIoTest.class);

    @Test
    public void testSampleCall() throws InterruptedException {
        WebClient webClient = WebClient
                .builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(request -> {
                    LOGGER.info("Step 1");
                    return Mono.just(request);
                }))
                .baseUrl("http://localhost:8080/").build();

        Mono<String> response1 = webClient.post()
                .uri("/messages")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromObject("{\n" +
                        "  \"id\": \"1\",\n" +
                        "  \"payload\":\"sample 1\",\n" +
                        "  \"delay\": \"10000\"\n" +
                        "}"))
                .exchange()
                .flatMap(resp -> {
                    LOGGER.info("Step 2:" + resp.statusCode());
                    return resp.bodyToMono(String.class);
                });

        Mono<String> response2 = webClient.post()
                .uri("/messages")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromObject("{\n" +
                        "  \"id\": \"2\",\n" +
                        "  \"payload\":\"sample 2\",\n" +
                        "  \"delay\": \"10000\"\n" +
                        "}"))
                .exchange()
                .flatMap(resp -> {
                    LOGGER.info("Step 3:" + resp.statusCode());
                    return resp.bodyToMono(String.class);
                });

        CountDownLatch cl = new CountDownLatch(1);

        LOGGER.info("Step 4");
        response1.mergeWith(response2).subscribe(resp -> LOGGER.info(resp), t -> t.printStackTrace(), () -> cl.countDown());
        LOGGER.info("Step 5");

        cl.await();
    }
}
