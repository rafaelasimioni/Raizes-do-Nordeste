package br.com.rafaelasimioni.raizesdonordeste.infrastructure.pagamento;

import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByPedidoId(Long pedidoId);
}