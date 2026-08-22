package br.com.rafaelasimioni.raizesdonordeste.api.pagamento.dto;



import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoResponseDTO {

    private Long id;

    private Long pedidoId;

    private StatusPagamento status;

    private BigDecimal valor;

    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataResposta;
}