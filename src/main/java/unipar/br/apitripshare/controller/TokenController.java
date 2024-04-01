package unipar.br.apitripshare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unipar.br.apitripshare.dto.users.LoginRequestDTO;
import unipar.br.apitripshare.dto.users.LoginResponseDTO;
import unipar.br.apitripshare.services.TokenService;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            var jwtValue = tokenService.login(loginRequest);
            var expiresIn = 300L;
            return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
