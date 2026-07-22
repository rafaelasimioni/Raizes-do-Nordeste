package br.com.rafaelasimioni.raizesdonordeste.application.usuario;

import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.mapper.UsuarioMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.Usuario;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;


    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO request){

        if (usuarioRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setAtivo(true);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioSalvo);
    }

    private Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public UsuarioResponseDTO buscarId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        return usuarioMapper.toResponse(usuario);
    }

   public List<UsuarioResponseDTO>listarTodos(){
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
   }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {

        Usuario usuarioExistente = buscarEntidadePorId(id);

        usuarioMapper.atualizarEntidade(request, usuarioExistente);

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return usuarioMapper.toResponse(usuarioAtualizado);

    }

    public void deletar(Long id) {

        Usuario usuarioExistente = buscarEntidadePorId(id);

        usuarioRepository.delete(usuarioExistente);
    }

}
