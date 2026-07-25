package br.com.rafaelasimioni.raizesdonordeste.application.usuario;

import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioAtualizacaoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.mapper.UsuarioMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.PerfilUsuario;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.Usuario;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;


    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO request){

        if (usuarioRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(request);

        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
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

    public UsuarioResponseDTO atualizar(
            Long id,
            UsuarioAtualizacaoRequestDTO request,
            Authentication authentication
    ) {
        Usuario usuarioLogado = usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado.")
                );

        Usuario usuarioExistente = buscarEntidadePorId(id);

        boolean admin = usuarioLogado.getPerfil() == PerfilUsuario.ADMIN;
        boolean proprioUsuario = usuarioLogado.getId().equals(id);

        if (!admin && !proprioUsuario) {
            throw new AccessDeniedException(
                    "Você não pode alterar outro usuário."
            );
        }

        usuarioMapper.atualizarEntidade(request, usuarioExistente);

        usuarioExistente.setSenha(
                passwordEncoder.encode(request.getSenha())
        );

        Usuario usuarioAtualizado =
                usuarioRepository.save(usuarioExistente);

        return usuarioMapper.toResponse(usuarioAtualizado);
    }

    public void deletar(Long id, Authentication authentication) {

        Usuario usuarioLogado = usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado.")
                );

        boolean admin = usuarioLogado.getPerfil() == PerfilUsuario.ADMIN;
        boolean proprioUsuario = usuarioLogado.getId().equals(id);

        if (!admin && !proprioUsuario) {
            throw new AccessDeniedException(
                    "Você não pode deletar outro usuário."
            );
        }

        Usuario usuarioExistente = buscarEntidadePorId(id);

        usuarioRepository.delete(usuarioExistente);
    }
}
