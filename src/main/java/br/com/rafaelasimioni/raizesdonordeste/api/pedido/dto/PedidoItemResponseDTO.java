package br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoItemResponseDTO {

    private Long id;

    private Long unidadeProdutoId;

    private String nomeProduto;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;
}