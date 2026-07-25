package br.com.rafaelasimioni.raizesdonordeste.api.auth;

import br.com.rafaelasimioni.raizesdonordeste.api.auth.dto.LoginRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.auth.dto.LoginResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.autenticar(request));
    }
}