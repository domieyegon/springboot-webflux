package ke.unify.springbootwebflux.repository;

import ke.unify.springbootwebflux.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
