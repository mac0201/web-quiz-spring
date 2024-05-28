package engine.service;

import engine.exceptions.CustomExceptions;
import engine.model.User;
import engine.model.dto.UserRegistrationDTO;
import engine.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Provides business logic operations for managing users. It interacts with the UserRepository.
 *  to perform CRUD operations.
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger appLogger;

    /**
     * Retrieves a user with specified email.
     * @param email Email of the user
     * @return User object retrieved from the database.
     */
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    /**
     * Creates a new user.
     * @param dto UserRegistrationDTO object containing required information to create user account
     */
    public void register(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) // check if email already exists
            throw new CustomExceptions.EmailAlreadyExistsException();
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // encode password and set
        userRepository.save(user); // save in repository
        appLogger.info("User registered: {}", dto.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUser(email);
    }

}
