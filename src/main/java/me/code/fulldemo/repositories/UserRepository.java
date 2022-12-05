package me.code.fulldemo.repositories;

import com.mongodb.client.MongoCollection;
import me.code.fulldemo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
