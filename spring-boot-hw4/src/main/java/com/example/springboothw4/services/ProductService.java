package com.example.springboothw4.services;


import com.example.springboothw4.entities.Product;
import com.example.springboothw4.repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public void setProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProduct() {
        return productDao.findAllProduct();
    }

    public Product getById(Long id) {
        return productDao.findById(id);
    }

	public void remove(Long id) {
		productDao.deleteProduct(id);
	}

    public void add(Product product) {
        productDao.addProduct(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }
}
