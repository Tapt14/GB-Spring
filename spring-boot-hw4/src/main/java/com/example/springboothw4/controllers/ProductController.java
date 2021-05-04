package com.example.springboothw4.controllers;


import com.example.springboothw4.entities.Product;
import com.example.springboothw4.services.ProductService;
import com.example.springboothw4.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String indexPage(Model model,
                            @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                            @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                            @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                            @RequestParam(name = "page", required = false) Optional<Integer> page,
                            @RequestParam(name = "size", required = false) Optional<Integer> size) {
        model.addAttribute("products", productService.getByParams(titleFilter, min, max, page, size));
        return "product_views/index";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Long id,
                              Model model) {
        model.addAttribute("product", productService.getById(id).orElseThrow(NotFoundException::new));
        return "product_views/product_form";
    }

    @PostMapping("/product_update")
    public String updateProduct(Product product) {
        productService.addOrUpdate(product);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute(new Product());
        return "product_views/product_form";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable(value = "id") Long id) {
      productService.remove(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("product_views/not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
