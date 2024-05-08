package com.example.ApiRest.repositories;

import com.example.ApiRest.entities.Role;
import com.example.ApiRest.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DirtiesContext
    public void findByEmailUsingExistingEmail() {
        // Arrange
        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john@example.com")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // Act
        Optional<User> foundUserOptional = userRepository.findByEmail("john@example.com");

        // Assert
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals("John", foundUser.getFirstname());
        assertEquals("Doe", foundUser.getLastname());
        assertEquals("john@example.com", foundUser.getEmail());
        assertEquals("password", foundUser.getPassword());
        assertEquals(Role.USER, foundUser.getRole());
    }

    @Test
    @DirtiesContext
    public void findByEmailUsingNonExistingEmail() {
        // Act
        Optional<User> foundUserOptional = userRepository.findByEmail("nonexisting@example.com");

        // Assert
        assertFalse(foundUserOptional.isPresent());
    }
}
