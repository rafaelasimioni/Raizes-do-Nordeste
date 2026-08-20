package br.com.rafaelasimioni.raizesdonordeste.api.pagamento;

import br.com.rafaelasimioni.raizesdonordeste.api.pagamento.dto.PagamentoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.pagamento.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("/mock/{pedidoId}")
    public ResponseEntity<PagamentoResponseDTO> processarPagamento(
            @PathVariable Long pedidoId,
            @RequestParam boolean aprovado
    ) {

        return ResponseEntity.ok(
                pagamentoService.processarPagamento(
                        pedidoId,
                        aprovado
                )
        );
    }
}