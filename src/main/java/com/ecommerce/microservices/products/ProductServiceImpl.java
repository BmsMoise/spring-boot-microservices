package com.ecommerce.microservices.products;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto convertEntityToDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override

    public ProductEntity convertDtoToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }

    @Override

    public ProductDto saveProduct(ProductDto productDto) {
        ProductEntity productEntity = convertDtoToEntity(productDto);
        ProductEntity productEntity2 = productRepository.save(productEntity);
        return convertEntityToDto(productEntity2);
    }

    @Override
    public List<ProductDto> list() {
        return productRepository.findAll().stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override

    public ProductDto updateProduct(Long id, ProductDto productDto) throws IOException {
        ProductEntity productEntity = productRepository.findById(id).get();
        try {
            if (Objects.nonNull(productDto.getDesignation()) && !"".equalsIgnoreCase(productDto.getDesignation())) {
                productEntity.setDesignation(productDto.getDesignation());
            }
            if (Objects.nonNull(productDto.getPrice())) {
                productEntity.setPrice(productDto.getPrice());
            }

            // if(Objects.nonNull(productDto.getId_categorie_auto())){productEntity.setId_categorie_auto(productDto.getId_categorie_auto());;}
            return convertEntityToDto(productRepository.save(productEntity));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("erreur lors de la modification");
        }

    }

    @Override

    public void deleteProduct(Long id) throws IOException {
        try {
            productRepository.findById(id).get();
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("erreur lors de la suppression");
        }
    }

    @Override
    public ProductDto findById(Long id) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(id);
            return convertEntityToDto(productEntity.get());
        } catch (Exception e) {
            throw new DataIntegrityViolationException("erreur lors de la suppression");
        }
    }
}
