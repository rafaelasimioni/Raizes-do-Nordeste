package br.com.rafaelasimioni.raizesdonordeste.infrastructure.pedido;

import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findByUnidadeId(Long unidadeId);
}