package com.vdt.authservice.config;

//@Configuration
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@RequiredArgsConstructor
public class ApplicationInitConfig {

//  PasswordEncoder passwordEncoder;
//
//  @Bean
//  ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository) {
//    return args -> {
//      if (!userRepository.existsByEmail("admin@gmail.com")) {
//        var userRole = roleRepository.save(
//            Role.builder().name(PredefinedRole.USER.name()).permissions(
//                    Set.of(Permission.USER_CREATE, Permission.USER_READ, Permission.USER_UPDATE, Permission.USER_DELETE))
//                .build());
//
//        var adminRole = roleRepository.save(
//            Role.builder().name(PredefinedRole.ADMIN.name()).permissions(
//                    Set.of(Permission.ADMIN_READ, Permission.ADMIN_CREATE, Permission.ADMIN_UPDATE , Permission.ADMIN_DELETE))
//                .build());
//
//        User user = new User();
//        user.setEmail("admin@gmail.com");
//        user.setPassword(passwordEncoder.encode("admin@123"));
//        user.setRoles(Set.of(userRole, adminRole));
//
//        userRepository.save(user);
//      }
//    };
//  }
}
