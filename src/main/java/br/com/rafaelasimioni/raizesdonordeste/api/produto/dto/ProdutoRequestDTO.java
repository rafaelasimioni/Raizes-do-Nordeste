package br.com.rafaelasimioni.raizesdonordeste.api.produto.dto;

import br.com.rafaelasimioni.raizesdonordeste.domain.produto.CategoriaProduto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 500)
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal preco;

    @NotNull
    private CategoriaProduto categoria;
}