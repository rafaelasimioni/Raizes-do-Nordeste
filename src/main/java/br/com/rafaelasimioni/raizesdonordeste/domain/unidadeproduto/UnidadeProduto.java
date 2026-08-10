package br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto;

import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "unidade_produto",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"produto_id", "unidade_id"})
        }
)
@Getter
@Setter
public class UnidadeProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean disponivel;
}