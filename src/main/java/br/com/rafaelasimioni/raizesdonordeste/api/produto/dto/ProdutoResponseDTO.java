package br.com.rafaelasimioni.raizesdonordeste.api.produto.dto;


import br.com.rafaelasimioni.raizesdonordeste.domain.produto.CategoriaProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResponseDTO {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private CategoriaProduto categoria;

    private Boolean ativo;
}