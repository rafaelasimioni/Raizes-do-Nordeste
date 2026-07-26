package br.com.rafaelasimioni.raizesdonordeste.application.unidade;

import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidade.dto.UnidadeResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidade.mapper.UnidadeMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidade.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final UnidadeMapper unidadeMapper;

    public UnidadeResponseDTO cadastrar(UnidadeRequestDTO requestDTO){

        boolean admin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!admin) {
            throw new AccessDeniedException("Apenas administradores podem cadastrar unidades.");
        }

        Unidade unidade = unidadeMapper.toEntity(requestDTO);

        unidade.setAtivo(true);

        Unidade unidadeSalva = unidadeRepository.save(unidade);

        return unidadeMapper.toResponseDTO(unidadeSalva);

    }

    public List<UnidadeResponseDTO>listarAtivas(){
        return unidadeRepository.findByAtivoTrue()
                .stream()
                .map(unidadeMapper::toResponseDTO)
                .toList();
    }

    public UnidadeResponseDTO buscarPorId(Long id) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada.")
                );

        if (!unidade.getAtivo()) {
            throw new RuntimeException("Unidade não encontrada.");
        }

        return unidadeMapper.toResponseDTO(unidade);
    }

    public UnidadeResponseDTO atualizar(Long id, UnidadeRequestDTO requestDTO)
            throws AccessDeniedException {

        boolean admin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!admin) {
            throw new AccessDeniedException(
                    "Apenas administradores podem atualizar unidades."
            );
        }

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada.")
                );

        unidadeMapper.atualizarEntidade(requestDTO, unidade);

        Unidade unidadeAtualizada = unidadeRepository.save(unidade);

        return unidadeMapper.toResponseDTO(unidadeAtualizada);
    }

    public void deletar(Long id) throws AccessDeniedException {

        boolean admin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!admin) {
            throw new AccessDeniedException(
                    "Apenas administradores podem excluir unidades."
            );
        }

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada.")
                );

        unidade.setAtivo(false);

        unidadeRepository.save(unidade);
    }
}

