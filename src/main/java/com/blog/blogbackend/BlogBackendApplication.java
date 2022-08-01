package com.blog.blogbackend;

import com.blog.blogbackend.entities.Role;
import com.blog.blogbackend.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.blog.blogbackend.config.AppConstants.ADMIN_USER;
import static com.blog.blogbackend.config.AppConstants.NORMAL_USER;

@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = new User();
//        user.setName("James H");
//        user.setAbout("Adding from application");
//        user.setEmail("james@mail.com");
//        user.setPassword(this.passwordEncoder.encode("abcd1234"));
//        Set<Role> roles1 = new HashSet<>();
//        Role role2 = new Role();
//        role2.setId(ADMIN_USER);
//        role2.setName("ROLE_ADMIN");
//        System.out.println("Role: " + role2.toString());
//        user.getRoles().add(role2);
//        System.out.println("User" + user.toString());
//        System.out.println("User Role" + user.getRoles().toString());
//        this.userRepository.save(user);

        try {
            Role role = new Role();
            role.setId(ADMIN_USER);
            role.setName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setId(NORMAL_USER);
            role1.setName("ROLE_NORMAL");

            List<Role> roles = new ArrayList<>();
            roles.add(role);
            roles.add(role1);
            List<Role> result = this.roleRepository.saveAll(roles);
            result.forEach(r -> System.out.println(r.getName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
