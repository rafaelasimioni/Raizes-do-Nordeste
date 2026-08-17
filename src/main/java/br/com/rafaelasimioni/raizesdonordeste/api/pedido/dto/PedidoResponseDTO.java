package br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto;

import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.CanalPedido;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {

    private Long id;

    private Long usuarioId;

    private Long unidadeId;

    private String nomeUnidade;

    private CanalPedido canalPedido;

    private StatusPedido status;

    private BigDecimal valorTotal;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private List<PedidoItemResponseDTO> itens;
}