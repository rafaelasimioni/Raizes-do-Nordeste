package br.com.rafaelasimioni.raizesdonordeste.api.produto;

import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.produto.ProdutoService;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.CategoriaProduto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(
            @Valid @RequestBody ProdutoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorCategoria(
            @PathVariable CategoriaProduto categoria) {

        return ResponseEntity.ok(
                produtoService.listarPorCategoria(categoria)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        return ResponseEntity.ok(
                produtoService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}