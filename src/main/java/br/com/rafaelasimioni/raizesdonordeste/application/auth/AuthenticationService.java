package br.com.rafaelasimioni.raizesdonordeste.application.auth;

import br.com.rafaelasimioni.raizesdonordeste.api.auth.dto.LoginRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.auth.dto.LoginResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponseDTO autenticar(LoginRequestDTO request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        UserDetails usuario = (UserDetails) authentication.getPrincipal();

        String token = tokenService.gerarToken(usuario);

        return new LoginResponseDTO(token);
    }
}