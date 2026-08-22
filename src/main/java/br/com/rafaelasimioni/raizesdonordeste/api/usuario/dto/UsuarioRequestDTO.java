package br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto;

import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.PerfilUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private PerfilUsuario perfil;
}
