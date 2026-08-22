package br.com.rafaelasimioni.raizesdonordeste.domain.pedido;

import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "unidade_produto_id", nullable = false)
    private UnidadeProduto unidadeProduto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;
}