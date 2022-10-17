package com.ecommerce.microservices.products;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public ProductDto convertEntityToDto(ProductEntity productEntity);

    public ProductEntity convertDtoToEntity(ProductDto productDto);

    public ProductDto saveProduct(ProductDto productDto);

    public List<ProductDto> list();

    public ProductDto updateProduct(Long id, ProductDto productDto) throws IOException;

    public void deleteProduct(Long id) throws IOException;

    public ProductDto findById(Long id);

}
