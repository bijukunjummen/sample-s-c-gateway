package gateway.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Sample throttling filter.
 * See https://github.com/bbeck/token-bucket
 */
public class ThrottleGatewayFilter implements GatewayFilter {
    private static final Log log = LogFactory.getLog(ThrottleGatewayFilter.class);

    private final int capacity;
    private final int refillTokens;
    private final int refillPeriod;
    private final TimeUnit refillUnit;
    private final TokenBucket tokenBucket;

    private ThrottleGatewayFilter(Builder builder) {
        capacity = builder.capacity;
        refillTokens = builder.refillTokens;
        refillPeriod = builder.refillPeriod;
        refillUnit = builder.refillUnit;
        tokenBucket = TokenBuckets.builder().withCapacity(capacity)
                .withFixedIntervalRefillStrategy(refillTokens, refillPeriod, refillUnit)
                .build();

    }
    
    public static Log getLog() {
        return log;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public int getCapacity() {
        return capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public int getRefillPeriod() {
        return refillPeriod;
    }

    public TimeUnit getRefillUnit() {
        return refillUnit;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("TokenBucket capacity: " + tokenBucket.getCapacity());
        boolean consumed = tokenBucket.tryConsume();
        if (consumed) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }


    public static final class Builder {
        private int capacity;
        private int refillTokens;
        private int refillPeriod;
        private TimeUnit refillUnit;

        private Builder() {
        }

        public Builder withCapacity(int val) {
            capacity = val;
            return this;
        }

        public Builder withRefillTokens(int val) {
            refillTokens = val;
            return this;
        }

        public Builder withRefillPeriod(int val) {
            refillPeriod = val;
            return this;
        }

        public Builder withRefillUnit(TimeUnit val) {
            refillUnit = val;
            return this;
        }

        public ThrottleGatewayFilter build() {
            return new ThrottleGatewayFilter(this);
        }
    }
}
