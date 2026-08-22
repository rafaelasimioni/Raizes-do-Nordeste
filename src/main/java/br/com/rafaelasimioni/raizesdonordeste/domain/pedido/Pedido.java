package br.com.rafaelasimioni.raizesdonordeste.domain.pedido;

import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.Pagamento;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.Usuario;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CanalPedido canalPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PedidoItem> itens = new ArrayList<>();

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;
}