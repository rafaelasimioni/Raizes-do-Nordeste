package br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoItemRequestDTO {

    @NotNull
    private Long unidadeProdutoId;

    @NotNull
    @Positive
    private Integer quantidade;
}