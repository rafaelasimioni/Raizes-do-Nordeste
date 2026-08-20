package br.com.rafaelasimioni.raizesdonordeste.api.pedido;

import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.pedido.PedidoService;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.StatusPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrar(
            @Valid @RequestBody PedidoRequestDTO requestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoService.cadastrar(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(
                pedidoService.listarTodos()
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId) {

        return ResponseEntity.ok(
                pedidoService.listarPorUsuario(usuarioId)
        );
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorUnidade(
            @PathVariable Long unidadeId) {

        return ResponseEntity.ok(
                pedidoService.listarPorUnidade(unidadeId)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status
    ) {

        return ResponseEntity.ok(
                pedidoService.atualizarStatus(id, status)
        );
    }
}