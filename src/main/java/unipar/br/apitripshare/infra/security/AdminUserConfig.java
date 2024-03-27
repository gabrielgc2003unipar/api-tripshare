package unipar.br.apitripshare.infra.security;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unipar.br.apitripshare.entities.Role;
import unipar.br.apitripshare.entities.User;
import unipar.br.apitripshare.repository.RoleRepository;
import unipar.br.apitripshare.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        Optional<User> userAdmin = userRepository.findByUsername("admin");

        if (userAdmin.isPresent()) {
            System.out.println("admin já existe");
        } else {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123"));
            List<Role> roles = List.of(roleAdmin);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}