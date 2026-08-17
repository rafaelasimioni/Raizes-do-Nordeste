package br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto;

import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.CanalPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @NotNull
    private Long unidadeId;

    @NotNull
    private CanalPedido canalPedido;

    @NotEmpty
    @Valid
    private List<PedidoItemRequestDTO> itens;
}