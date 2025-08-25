package com.vdt.authservice.config;

import com.vdt.authservice.constant.Permission;
import com.vdt.authservice.constant.PredefinedRole;
import com.vdt.authservice.entity.Role;
import com.vdt.authservice.entity.User;
import com.vdt.authservice.repository.RoleRepository;
import com.vdt.authservice.repository.UserRepository;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {

  PasswordEncoder passwordEncoder;

  @Bean
  ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository) {
    return args -> {
      if (!userRepository.existsByEmail("admin@gmail.com")) {
        var userRole = roleRepository.save(
            Role.builder().name(PredefinedRole.USER.name()).permissions(
                    Set.of(Permission.CREATE, Permission.READ, Permission.UPDATE, Permission.DELETE))
                .build());

        var adminRole = roleRepository.save(
            Role.builder().name(PredefinedRole.ADMIN.name()).permissions(
                    Set.of( Permission.READ, Permission.UPDATE, Permission.DELETE))
                .build());

        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin@123"));
        user.setRoles(Set.of(userRole, adminRole));

        userRepository.save(user);
      }
    };
  }
}
