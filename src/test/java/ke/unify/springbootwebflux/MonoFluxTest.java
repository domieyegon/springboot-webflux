package ke.unify.springbootwebflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<String> mono = Mono.just("Hello").log();
        mono.subscribe(System.out::println);
    }

    @Test
    public void testFlux() {
        Flux<String> flux = Flux.just("Hello", "World")
                .concatWithValues("Java", "Spring", "WebFlux")
                .log();
        flux.subscribe(System.out::println);
    }
}
