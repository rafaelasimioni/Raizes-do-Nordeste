package br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto.UnidadeProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import org.springframework.stereotype.Component;

@Component
public class UnidadeProdutoMapper {

    public UnidadeProdutoResponseDTO toResponseDTO(UnidadeProduto unidadeProduto) {

        UnidadeProdutoResponseDTO dto = new UnidadeProdutoResponseDTO();

        dto.setId(unidadeProduto.getId());

        dto.setProdutoId(unidadeProduto.getProduto().getId());
        dto.setNomeProduto(unidadeProduto.getProduto().getNome());

        dto.setUnidadeId(unidadeProduto.getUnidade().getId());
        dto.setNomeUnidade(unidadeProduto.getUnidade().getNome());

        dto.setPreco(unidadeProduto.getPreco());
        dto.setDisponivel(unidadeProduto.getDisponivel());

        return dto;
    }
}