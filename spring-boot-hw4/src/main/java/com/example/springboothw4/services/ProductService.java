package com.example.springboothw4.services;


import com.example.springboothw4.entities.Product;
import com.example.springboothw4.repositories.ProductRepository;
import com.example.springboothw4.repositories.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
        return this;
    }

    @Transactional
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void addOrUpdate(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Page<Product> getByParams(Optional<String> nameFilter,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max,
                                     Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> sortField,
                                     Optional<String> sortOrder) {

        Specification<Product> specification = Specification.where(null);
        if (nameFilter.isPresent()) {
            specification = specification.and(ProductSpecification.titleLike(nameFilter.get()));
        }

        if (min.isPresent()) {
            specification = specification.and(ProductSpecification.greatOrEqual(min.get()));
        }

        if (max.isPresent()) {
            specification = specification.and(ProductSpecification.lessOrEqual(max.get()));
        }

        return productRepository.findAll(specification,
                PageRequest.of(page.orElse(1) - 1, size.orElse(5)));
    }

}
