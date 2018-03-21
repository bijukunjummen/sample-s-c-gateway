package gateway.sample;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {
    private final String httpbinUri = "http://httpbin.org:80";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r ->
                        r.path("/get/**")
                                .uri(httpbinUri))
                .route(r ->
                        r.host("*.header.org")
                                .filters(f -> f.addRequestHeader("DEMO-HEADER", "Demo-value"))
                                .uri(httpbinUri)
                )
                .route(r ->
                        r.host("*.rewrite.org")
                                .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                                .uri(httpbinUri)
                )
                .route(r ->
                        r.host("*.setpath.org").and().path("/foo/{segment}")
                                .filters(f -> f.setPath("/{segment}"))
                                .uri(httpbinUri)
                )
                .route(r ->
                        r.host("*.hystrix.org")
                                .filters(f -> f.hystrix("somecommand"))
                                .uri(httpbinUri)
                )
                .route(r ->
                        r.host("*.throttle.org")
                                .filters(f -> f.filter(ThrottleGatewayFilter.newBuilder()
                                        .withCapacity(1)
                                        .withRefillTokens(1)
                                        .withRefillPeriod(10)
                                        .withRefillUnit(TimeUnit.SECONDS).build()))
                                .uri(httpbinUri)
                )
                .build();
    }

}



