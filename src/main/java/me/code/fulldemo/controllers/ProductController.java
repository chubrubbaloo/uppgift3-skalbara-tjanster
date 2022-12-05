package me.code.fulldemo.controllers;

import lombok.extern.slf4j.Slf4j;
import me.code.fulldemo.dtos.ProductCreation;
import me.code.fulldemo.exceptions.ProductAlreadyExistsException;
import me.code.fulldemo.exceptions.ProductNotFoundException;
import me.code.fulldemo.models.Product;
import me.code.fulldemo.repositories.ProductRepository;
import me.code.fulldemo.security.UserObject;
import me.code.fulldemo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public Collection<Product> getAll() {
        return productService.getAll();
    }

    @PutMapping("/")
    public Product createProduct(@RequestBody ProductCreation creation)
            throws ProductAlreadyExistsException
    {
        return productService.createProduct(
                creation.getName(),
                creation.getDescription(),
                creation.getPrice());
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable String id)
            throws ProductNotFoundException
    {
        return productService.deleteProduct(UUID.fromString(id));
    }

}
