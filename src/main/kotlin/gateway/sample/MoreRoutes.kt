package gateway.sample

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MoreRoutes {

    @Bean
    fun kotlinBasedRoutes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes {
                route {
                    host("*.kotlin.org")
                    filters { 
                        addRequestHeader("kotlin-header", "kotlin-header-value")
                        addResponseHeader("kotlin-response-header", "kotlin-response-header-value")
                    }
                    uri("http://httpbin.org:80")
                }
            }
}