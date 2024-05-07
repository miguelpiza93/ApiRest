package com.example.ApiRest.repositories;

import com.example.ApiRest.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() {
        User user = User.builder()
                .firstname("Juan")
                .lastname("Rodriguez")
                .email("juan@rodriguez.org")
                .password("hunter2")
                .build();
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void findByEmail() {
        String email = "juan@rodriguez.org";
        // when
        User found = userRepository.findByEmail(email).orElseThrow();
        // then
        assertThat(found.getEmail()).isEqualTo(email);
    }
}