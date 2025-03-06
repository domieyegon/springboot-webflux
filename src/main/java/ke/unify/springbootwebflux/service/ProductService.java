package ke.unify.springbootwebflux.service;

import ke.unify.springbootwebflux.dto.ProductDTO;
import ke.unify.springbootwebflux.entity.Product;
import ke.unify.springbootwebflux.mapper.ProductMapper;
import ke.unify.springbootwebflux.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(productDTO -> {
                    log.info("Saving product {}", productDTO);
//                    productDTO.setId(UUID.randomUUID().toString());
                    return ProductMapper.toEntity(productDTO);
                }).flatMap(productRepository::save)
                .map(ProductMapper::toDto);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id) {
        return productRepository.findById(id)
                .flatMap(p -> productDTOMono.map(ProductMapper::toEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(ProductMapper::toDto);
    }

    public Flux<ProductDTO> findAll() {
        log.info("Finding all products");
        return productRepository.findAll().map(ProductMapper::toDto);
    }

    public Mono<ProductDTO> findById(String id) {
        log.info("Finding product by id {}", id);
        return productRepository.findById(id).map(ProductMapper::toDto);
    }

    public Flux<ProductDTO> findByPriceBetween(double min, double max) {
        log.info("Finding product by price between {} and {}", min, max);

        return productRepository.findByPriceBetween(Range.closed(min, max)).map(ProductMapper::toDto);
    }

    public Mono<Void> deleteById(String id) {
        log.info("Deleting product by id {}", id);
        return productRepository.deleteById(id);
    }
}
