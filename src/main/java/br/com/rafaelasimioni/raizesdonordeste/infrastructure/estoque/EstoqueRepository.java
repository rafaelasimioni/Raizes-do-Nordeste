package br.com.rafaelasimioni.raizesdonordeste.infrastructure.estoque;

import br.com.rafaelasimioni.raizesdonordeste.domain.estoque.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByUnidadeProdutoId(Long unidadeProdutoId);
}