package ke.unify.springbootwebflux.controller;

import ke.unify.springbootwebflux.dto.ProductDTO;
import ke.unify.springbootwebflux.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private  final ProductService productService;

    @PostMapping
    public Mono<ProductDTO> saveProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        return productService.saveProduct(productDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> updateProduct(@RequestBody Mono<ProductDTO> productDTOMono, @PathVariable String id) {
        return productService.updateProduct(productDTOMono, id);
    }

    @GetMapping
    public Flux<ProductDTO> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping("/find-by-range")
    public Flux<ProductDTO> getProductsBetweenRange(@RequestParam double min, @RequestParam double max) {
        return productService.findByPriceBetween(min, max);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteById(id);
    }
}
