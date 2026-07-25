package br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizacaoRequestDTO {

    private String nome;
    private String email;
    private String senha;
}