package br.com.rafaelasimioni.raizesdonordeste.api.unidade.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import org.springframework.stereotype.Component;

@Component
public class UnidadeMapper {

    public Unidade toEntity(UnidadeRequestDTO dto) {
        Unidade unidade = new Unidade();

        unidade.setNome(dto.getNome());
        unidade.setCidade(dto.getCidade());
        unidade.setEstado(dto.getEstado());
        unidade.setEndereco(dto.getEndereco());
        unidade.setTelefone(dto.getTelefone());

        return unidade;
    }

    public UnidadeResponseDTO toResponseDTO(Unidade unidade) {
        UnidadeResponseDTO dto = new UnidadeResponseDTO();

        dto.setId(unidade.getId());
        dto.setNome(unidade.getNome());
        dto.setCidade(unidade.getCidade());
        dto.setEstado(unidade.getEstado());
        dto.setEndereco(unidade.getEndereco());
        dto.setTelefone(unidade.getTelefone());
        dto.setAtivo(unidade.getAtivo());

        return dto;
    }

    public void atualizarEntidade(
            UnidadeRequestDTO dto,
            Unidade unidade
    ) {
        unidade.setNome(dto.getNome());
        unidade.setCidade(dto.getCidade());
        unidade.setEstado(dto.getEstado());
        unidade.setEndereco(dto.getEndereco());
        unidade.setTelefone(dto.getTelefone());
    }
}