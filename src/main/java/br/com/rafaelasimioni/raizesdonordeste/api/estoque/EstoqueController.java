package br.com.rafaelasimioni.raizesdonordeste.api.estoque;

import br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto.EstoqueRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto.EstoqueResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.estoque.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> cadastrar(
            @Valid @RequestBody EstoqueRequestDTO requestDTO)
            throws AccessDeniedException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estoqueService.cadastrar(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponseDTO>> listarTodos()
            throws AccessDeniedException {

        return ResponseEntity.ok(
                estoqueService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorId(
            @PathVariable Long id)
            throws AccessDeniedException {

        return ResponseEntity.ok(
                estoqueService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueRequestDTO requestDTO)
            throws AccessDeniedException {

        return ResponseEntity.ok(
                estoqueService.atualizar(id, requestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id)
            throws AccessDeniedException {

        estoqueService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}