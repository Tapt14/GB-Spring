package com.example.springboothw4.services;

import com.example.springboothw4.entities.Product;
import com.example.springboothw4.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
public class RestProductService {
    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Product createOrUpdate(Product user) {
        return  repository.save(user);
    }

    @Transactional
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public List<Product> findAll () {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
