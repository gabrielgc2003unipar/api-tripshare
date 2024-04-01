package unipar.br.apitripshare.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.users.CreateUserDTO;
import unipar.br.apitripshare.entities.User;
import unipar.br.apitripshare.services.UserService;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.createUser(createUserDTO);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        var users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
}
