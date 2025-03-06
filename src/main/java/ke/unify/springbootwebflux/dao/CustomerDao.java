package ke.unify.springbootwebflux.dao;

import ke.unify.springbootwebflux.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class CustomerDao {

    public Flux<Customer> getCustomers() {
        return Flux.range(1,50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count: "+ i))
                .map(i-> new Customer(i, "customer"+i));
    }
}
