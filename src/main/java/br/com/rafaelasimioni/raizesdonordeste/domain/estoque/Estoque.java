package br.com.rafaelasimioni.raizesdonordeste.domain.estoque;

import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "unidade_produto_id", nullable = false, unique = true)
    private UnidadeProduto unidadeProduto;

    @Column(nullable = false)
    private Integer quantidadeAtual;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}