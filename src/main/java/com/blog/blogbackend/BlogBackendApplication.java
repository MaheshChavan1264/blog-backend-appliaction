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
        System.out.println(this.passwordEncoder.encode("abcd1234"));
        try {
            Role role = new Role();
            role.setId(NORMAL_USER);
            role.setName("NORMAL_USER");

            Role role1 = new Role();
            role1.setId(ADMIN_USER);
            role1.setName("ADMIN_USER");

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
