package unipar.br.apitripshare.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.CreateUserDTO;
import unipar.br.apitripshare.entities.User;
import unipar.br.apitripshare.repository.RoleRepository;
import unipar.br.apitripshare.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var basicRole = roleRepository.findByName("BASIC");
        var userFromDb = userRepository.findByUsername(createUserDTO.username());

        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User();
        user.setUsername(createUserDTO.username());
        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        user.setRoles(List.of(basicRole));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
