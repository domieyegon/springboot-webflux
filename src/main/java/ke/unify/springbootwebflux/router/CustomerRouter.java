package ke.unify.springbootwebflux.router;

import ke.unify.springbootwebflux.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouter {

    @Bean
    public RouterFunction<ServerResponse> customerRoutes(CustomerHandler customerHandler) {
        return RouterFunctions
                .route()
                .POST("/router/customers", customerHandler::saveCustomer)
                .GET("/router/customers", customerHandler::getCustomers)
                .GET("/router/customers/{id}", customerHandler::getCustomerById)
                .build();
    }
}
