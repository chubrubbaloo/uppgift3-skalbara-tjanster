package me.code.fulldemo.repositories;

import me.code.fulldemo.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {

    Optional<Product> findByName(String name);

}
