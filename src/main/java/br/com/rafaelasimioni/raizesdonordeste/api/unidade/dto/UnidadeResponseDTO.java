package br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeResponseDTO {

    private Long id;
    private String nome;
    private String cidade;
    private String estado;
    private String endereco;
    private String telefone;
    private Boolean ativo;
}