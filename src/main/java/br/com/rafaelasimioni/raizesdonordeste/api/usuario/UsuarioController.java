package br.com.rafaelasimioni.raizesdonordeste.api.usuario;

import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioAtualizacaoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
            @RequestBody UsuarioRequestDTO request
    ) {
        UsuarioResponseDTO usuario = usuarioService.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UsuarioResponseDTO> buscarId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(usuarioService.buscarId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioAtualizacaoRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                usuarioService.atualizar(id, request, authentication)
        );
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        usuarioService.deletar(id, authentication);

        return ResponseEntity.noContent().build();
    }
}