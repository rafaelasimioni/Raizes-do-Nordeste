package br.com.rafaelasimioni.raizesdonordeste.infrastructure.pedido;

import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    List<PedidoItem> findByPedidoId(Long pedidoId);
}