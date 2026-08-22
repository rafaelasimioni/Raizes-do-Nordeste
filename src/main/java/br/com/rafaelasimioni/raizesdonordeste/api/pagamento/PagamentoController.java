package br.com.rafaelasimioni.raizesdonordeste.api.pagamento;

import br.com.rafaelasimioni.raizesdonordeste.api.pagamento.dto.PagamentoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.pagamento.PagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@SecurityRequirement(name = "bearerAuth")
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