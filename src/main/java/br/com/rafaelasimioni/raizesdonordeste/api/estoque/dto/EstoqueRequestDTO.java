package br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequestDTO {

    @NotNull
    private Long unidadeProdutoId;

    @NotNull
    @PositiveOrZero
    private Integer quantidadeAtual;
}