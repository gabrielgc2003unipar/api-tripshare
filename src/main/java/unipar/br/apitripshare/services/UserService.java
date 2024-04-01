package unipar.br.apitripshare.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unipar.br.apitripshare.dto.users.CreateUserDTO;
import unipar.br.apitripshare.entities.User;
import unipar.br.apitripshare.repository.RoleRepository;
import unipar.br.apitripshare.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateUserDTO createUserDTO) throws ResponseStatusException {
        var basicRole = roleRepository.findByName("BASIC");
        var userFromDb = userRepository.findByUsername(createUserDTO.username());

        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User();
        user.setUsername(createUserDTO.username());
        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        user.setRoles(List.of(basicRole));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
