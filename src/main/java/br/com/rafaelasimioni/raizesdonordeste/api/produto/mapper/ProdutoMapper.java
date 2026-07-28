package br.com.rafaelasimioni.raizesdonordeste.api.produto.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());

        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setCategoria(produto.getCategoria());
        dto.setAtivo(produto.getAtivo());

        return dto;
    }
}