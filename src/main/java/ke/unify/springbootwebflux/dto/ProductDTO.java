package ke.unify.springbootwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {
    private String id;
    private String name;
    private int qty;
    private double price;
}
