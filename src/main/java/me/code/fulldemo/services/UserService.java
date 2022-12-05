package me.code.fulldemo.services;


import lombok.extern.slf4j.Slf4j;
import me.code.fulldemo.exceptions.ProductAlreadyExistsException;
import me.code.fulldemo.exceptions.UserAlreadyExistsException;
import me.code.fulldemo.models.Product;
import me.code.fulldemo.models.User;
import me.code.fulldemo.repositories.UserRepository;
import me.code.fulldemo.security.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("A user with username '" + username + "' could not be found."));

        return new UserObject(user);
    }

    public User registerUser(String username, String password, boolean admin)
            throws UserAlreadyExistsException
    {
        var existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            log.info("Failed to register user since name '" + username + "' already exists.");
            throw new UserAlreadyExistsException();
        }

        var user = new User(username, passwordEncoder.encode(password), admin);
        log.info("Successfully registered user with id '" + user.getId() + "'.");
        return userRepository.save(user);

    }

}
