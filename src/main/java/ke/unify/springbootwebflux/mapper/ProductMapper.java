package ke.unify.springbootwebflux.mapper;

import ke.unify.springbootwebflux.dto.ProductDTO;
import ke.unify.springbootwebflux.entity.Product;
import org.springframework.beans.BeanUtils;

public class ProductMapper {

    public static ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .qty(product.getQty())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
