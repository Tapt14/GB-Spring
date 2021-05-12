package com.example.springboothw4.controllers;

import com.example.springboothw4.entities.Product;
import com.example.springboothw4.services.RestProductService;
import com.example.springboothw4.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//@Tag(name = "User API", description = "API to manipulate user resources")
//@RestController
//@RequestMapping("/api/v1/product")
public class RestProductController {
    private RestProductService service;

    @Autowired
    public void setService(RestProductService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") Long id) {
        return service.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Product> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Product createUser(@RequestBody Product user) {
        service.createOrUpdate(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateUser(@RequestBody Product user) {
        service.createOrUpdate(user);
        return user;
    }

    @DeleteMapping("/{id}/id")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}

