package br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UnidadeProdutoResponseDTO {

    private Long id;

    private Long produtoId;
    private String nomeProduto;

    private Long unidadeId;
    private String nomeUnidade;

    private BigDecimal preco;

    private Boolean disponivel;
}