package ke.unify.springbootwebflux;

import ke.unify.springbootwebflux.controller.ProductController;
import ke.unify.springbootwebflux.dto.ProductDTO;
import ke.unify.springbootwebflux.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringbootWebfluxApplicationTests {

    @Autowired
    private WebTestClient client;

    @MockitoBean
    private ProductService productService;

    @Test
    public void saveProductTest() {
        Mono<ProductDTO> productDTOMono = Mono.just(
                ProductDTO.builder()
                        .id("123")
                        .name("Mobile")
                        .qty(2)
                        .price(20000)
                .build());

        when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

        client.post().uri("/api/products")
                .body(Mono.just(productDTOMono), ProductDTO.class)
        .exchange()
        .expectStatus().isOk();
    }

    @Test
    public void getAllProductsTest() {
        Flux<ProductDTO> productDTOFlux = Flux.just(new ProductDTO("123", "Mobile", 20000, 20000), new ProductDTO("456", "TV", 30000, 30000));

        when(productService.findAll()).thenReturn(productDTOFlux);

        Flux<ProductDTO> responseBody = client.get().uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDTO("123", "Mobile", 20000, 20000))
                .expectNext(new ProductDTO("456", "TV", 30000, 30000))
                .verifyComplete();
    }

    @Test
    public void getProductByIdTest() {

        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("123", "Mobile", 20000, 20000));

        when(productService.findById(any())).thenReturn(productDTOMono);

        Flux<ProductDTO> responseBody = client.get().uri("/api/products/123")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("Mobile"))
                .verifyComplete();
    }

    @Test
    public void updateProductTest() {
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("123", "Mobile", 20000, 70000));

        when(productService.updateProduct(any(Mono.class), any())).thenReturn(productDTOMono);

        client.put().uri("/api/products/123")
                .body(Mono.just(productDTOMono), ProductDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void deleteProductTest() {

        given(productService.deleteById(any())).willReturn(Mono.empty());
        client.delete().uri("/api/products/123")
                .exchange()
                .expectStatus().isOk();
    }

}
