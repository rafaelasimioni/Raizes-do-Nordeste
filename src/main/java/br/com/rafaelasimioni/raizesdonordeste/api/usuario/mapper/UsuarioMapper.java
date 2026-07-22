package br.com.rafaelasimioni.raizesdonordeste.api.usuario.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Usuario toEntity(UsuarioRequestDTO request);

    UsuarioResponseDTO toResponse(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)

    void atualizarEntidade(
            UsuarioRequestDTO request,
            @MappingTarget Usuario usuario
    );
}