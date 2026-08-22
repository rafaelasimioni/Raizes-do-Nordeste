package br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidade;

import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade,Long> {

    List<Unidade> findByAtivoTrue();

}
