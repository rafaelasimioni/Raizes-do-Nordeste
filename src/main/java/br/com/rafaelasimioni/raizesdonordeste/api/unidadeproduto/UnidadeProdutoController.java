package br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto;

import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto.UnidadeProdutoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto.UnidadeProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.unidadeproduto.UnidadeProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades-produtos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UnidadeProdutoController {

    private final UnidadeProdutoService unidadeProdutoService;

    // ADMIN

    @PostMapping
    public ResponseEntity<UnidadeProdutoResponseDTO> cadastrar(
            @Valid @RequestBody UnidadeProdutoRequestDTO requestDTO)
            throws AccessDeniedException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unidadeProdutoService.cadastrar(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                unidadeProdutoService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeProdutoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                unidadeProdutoService.buscarPorId(id)
        );
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<UnidadeProdutoResponseDTO>> listarPorUnidade(
            @PathVariable Long unidadeId) {

        return ResponseEntity.ok(
                unidadeProdutoService.listarPorUnidade(unidadeId)
        );
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<UnidadeProdutoResponseDTO>> listarPorProduto(
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                unidadeProdutoService.listarPorProduto(produtoId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UnidadeProdutoRequestDTO requestDTO)
            throws AccessDeniedException {

        return ResponseEntity.ok(
                unidadeProdutoService.atualizar(id, requestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id)
            throws AccessDeniedException {

        unidadeProdutoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // CLIENTE

    @GetMapping("/cliente/disponiveis")
    public ResponseEntity<List<UnidadeProdutoResponseDTO>> listarDisponiveis() {

        return ResponseEntity.ok(
                unidadeProdutoService.listarDisponiveisParaCliente()
        );
    }
}