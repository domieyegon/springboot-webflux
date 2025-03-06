package ke.unify.springbootwebflux.handler;

import ke.unify.springbootwebflux.dao.CustomerDao;
import ke.unify.springbootwebflux.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerHandler {

    private final CustomerDao customerDao;

    public CustomerHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        log.info("Get save customer");
        Mono<String> result = customerMono.map(dto-> dto.id()+":"+dto.name());
        return ServerResponse.ok().body(result, Customer.class);
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        log.info("Get customers: {}", request);
        Flux<Customer> customers = customerDao.getCustomers();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customers, Customer.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        int customerId =  Integer.parseInt(request.pathVariable("id"));
        log.info("Get customer by id: {}", customerId);

        Mono<Customer> result = customerDao.getCustomers().filter(customer -> customer.id() == customerId).next();
        return ServerResponse.ok().body(result, Customer.class);
    }

    }
