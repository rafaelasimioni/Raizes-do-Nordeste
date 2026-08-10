package br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UnidadeProdutoRequestDTO {

    @NotNull
    private Long produtoId;

    @NotNull
    private Long unidadeId;

    @DecimalMin("0.01")
    private BigDecimal preco;

    @NotNull
    private Boolean disponivel;
}