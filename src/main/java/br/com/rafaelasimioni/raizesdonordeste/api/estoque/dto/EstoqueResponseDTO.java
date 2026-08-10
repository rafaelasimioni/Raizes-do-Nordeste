package br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EstoqueResponseDTO {

    private Long id;
    private Long unidadeProdutoId;
    private String produtoNome;
    private String unidadeNome;
    private Integer quantidadeAtual;
    private LocalDateTime dataAtualizacao;
}