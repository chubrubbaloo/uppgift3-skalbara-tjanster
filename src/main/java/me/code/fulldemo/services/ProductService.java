package me.code.fulldemo.services;

import lombok.extern.slf4j.Slf4j;
import me.code.fulldemo.exceptions.ProductAlreadyExistsException;
import me.code.fulldemo.exceptions.ProductNotFoundException;
import me.code.fulldemo.models.Product;
import me.code.fulldemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<Product> getAll() {
        return productRepository.findAll();
    }

    /*

    var service = new ProductService();
    var product = service.createProduct("A", "B", 1.0);

    Assertions.assertEquals(product.getName(), "A");
    Assertions.assertEquals(product.getDescription(), "B");
    Assertions.assertEquals(product.getPrice(), 1.0);

     */

    /*

    var service = new ProductService();
    service.createProduct("A", "B", 1.0);

    Assertions.assertThrows(ProductAlreadyExistsException.class, () -> {
        service.createProduct("A", "B", 1.0);
    });

     */

    public Product createProduct(String name, String description, double price)
            throws ProductAlreadyExistsException
    {
        var existing = productRepository.findByName(name);
        if (existing.isPresent()) {
            log.info("Failed to create product since name '" + name + "' already exists.");
            throw new ProductAlreadyExistsException();
        }

        var product = new Product(name, description, price);
        log.info("Successfully created product with id '" + product.getId() + "'.");
        return productRepository.save(product);
    }

    public Product deleteProduct(UUID id)
            throws ProductNotFoundException
    {
        var optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("Failed to delete product since id '" + id + "' could not be found.");
            throw new ProductNotFoundException();
        }

        var product = optional.get();

        productRepository.delete(product);
        log.info("Successfully deleted product with id '" + product.getId() + "'.");

        return product;
    }

}
