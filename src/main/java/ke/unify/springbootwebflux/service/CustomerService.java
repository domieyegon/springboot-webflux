package ke.unify.springbootwebflux.service;

import ke.unify.springbootwebflux.dao.CustomerDao;
import ke.unify.springbootwebflux.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Flux<Customer> findAll() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = customerDao.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Total time taken for execution: " + (end - start)+ " millis");
        return customers;
    }
}
