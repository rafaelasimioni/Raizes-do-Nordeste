package br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidadeproduto;

import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnidadeProdutoRepository
        extends JpaRepository<UnidadeProduto, Long> {

    List<UnidadeProduto> findByUnidadeId(Long unidadeId);

    List<UnidadeProduto> findByProdutoId(Long produtoId);

    Optional<UnidadeProduto> findByProdutoAndUnidade(
            Produto produto,
            Unidade unidade
    );

    List<UnidadeProduto> findByDisponivelTrueAndUnidadeAtivoTrue();

    List<UnidadeProduto>
    findByUnidadeIdAndDisponivelTrueAndUnidadeAtivoTrue(
            Long unidadeId
    );

    List<UnidadeProduto>
    findByProdutoIdAndDisponivelTrueAndUnidadeAtivoTrue(
            Long produtoId
    );
}