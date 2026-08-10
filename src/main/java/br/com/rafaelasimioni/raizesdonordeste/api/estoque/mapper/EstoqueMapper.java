package br.com.rafaelasimioni.raizesdonordeste.api.estoque.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto.EstoqueResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.estoque.Estoque;
import org.springframework.stereotype.Component;

@Component
public class EstoqueMapper {

    public EstoqueResponseDTO toResponseDTO(Estoque estoque) {

        EstoqueResponseDTO dto = new EstoqueResponseDTO();

        dto.setId(estoque.getId());

        dto.setUnidadeProdutoId(
                estoque.getUnidadeProduto().getId()
        );

        dto.setProdutoNome(
                estoque.getUnidadeProduto()
                        .getProduto()
                        .getNome()
        );

        dto.setUnidadeNome(
                estoque.getUnidadeProduto()
                        .getUnidade()
                        .getNome()
        );

        dto.setQuantidadeAtual(
                estoque.getQuantidadeAtual()
        );

        dto.setDataAtualizacao(
                estoque.getDataAtualizacao()
        );

        return dto;
    }
}