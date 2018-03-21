package gateway.sample

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.net.URI

@RunWith(SpringRunner::class)
@SpringBootTest
class MoreRoutesTest {
    
    @Autowired
    lateinit var routeLocatorBuilder: RouteLocatorBuilder
    
    @Test
    fun testKotlinBasedRoutes() {
        val moreRoutes = MoreRoutes()
//        val routeLocator = moreRoutes.additionalRoutes(routeLocatorBuilder)
        
//        StepVerifier
//                .create(routeLocator.routes)
//                .expectNextMatches({r -> 
//                    r.uri.equals(URI.create("http://httpbin.org:80"))
//                })
//                .expectComplete()
//                .verify()
    }
}