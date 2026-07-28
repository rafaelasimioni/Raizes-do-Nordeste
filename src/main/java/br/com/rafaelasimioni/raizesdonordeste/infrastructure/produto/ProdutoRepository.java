package br.com.rafaelasimioni.raizesdonordeste.infrastructure.produto;

import br.com.rafaelasimioni.raizesdonordeste.domain.produto.CategoriaProduto;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivoTrue();

    List<Produto> findByCategoriaAndAtivoTrue(CategoriaProduto categoria);

}