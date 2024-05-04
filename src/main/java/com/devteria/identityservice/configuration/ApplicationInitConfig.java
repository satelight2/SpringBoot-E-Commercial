package com.devteria.identityservice.configuration;

import com.devteria.identityservice.entity.RolesEntity;
import com.devteria.identityservice.entity.UsersEntity;
import com.devteria.identityservice.repository.RoleRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
//                HashSet<RolesEntity> roles = new HashSet<>();
//                roles.add(roleRepository.findByRoleName("ADMIN"));

                UsersEntity user = UsersEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .address("Admin Address")
                        .email("admin@gmail.com")
                        .fullname("Admin")
                        .status(true)
//                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
