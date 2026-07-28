package br.com.rafaelasimioni.raizesdonordeste.api.unidade;

import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.unidade.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @PostMapping
    public ResponseEntity<UnidadeResponseDTO> cadastrar(
            @Valid @RequestBody UnidadeRequestDTO requestDTO
    ) throws AccessDeniedException {

        UnidadeResponseDTO unidade = unidadeService.cadastrar(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(unidade);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponseDTO>> listarAtivas() {

        return ResponseEntity.ok(unidadeService.listarAtivas());
    }

    @GetMapping("/todas")
    public ResponseEntity<List<UnidadeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(unidadeService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UnidadeRequestDTO requestDTO
    ) throws AccessDeniedException {

        UnidadeResponseDTO unidade =
                unidadeService.atualizar(id, requestDTO);

        return ResponseEntity.ok(unidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) throws AccessDeniedException {

        unidadeService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}