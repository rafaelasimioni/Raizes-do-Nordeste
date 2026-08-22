package br.com.rafaelasimioni.raizesdonordeste.application.estoque;

import br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto.EstoqueRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.estoque.dto.EstoqueResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.estoque.mapper.EstoqueMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.estoque.Estoque;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.estoque.EstoqueRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidadeproduto.UnidadeProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final UnidadeProdutoRepository unidadeProdutoRepository;
    private final EstoqueMapper estoqueMapper;

    public EstoqueResponseDTO cadastrar(
            EstoqueRequestDTO dto
    ) {
        validarAdmin();

        UnidadeProduto unidadeProduto =
                buscarUnidadeProduto(dto.getUnidadeProdutoId());

        if (estoqueRepository
                .findByUnidadeProdutoId(dto.getUnidadeProdutoId())
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este produto já possui estoque cadastrado nesta unidade"
            );
        }
        Estoque estoque = new Estoque();

        estoque.setUnidadeProduto(unidadeProduto);
        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setDataAtualizacao(LocalDateTime.now());

        Estoque salvo = estoqueRepository.save(estoque);

        return estoqueMapper.toResponseDTO(salvo);
    }

    public List<EstoqueResponseDTO> listarTodos() {

        validarAdmin();

        return estoqueRepository.findAll()
                .stream()
                .map(estoqueMapper::toResponseDTO)
                .toList();
    }

    public EstoqueResponseDTO buscarPorId(Long id) {

        validarAdmin();

        Estoque estoque = buscarEstoque(id);

        return estoqueMapper.toResponseDTO(estoque);
    }

    public EstoqueResponseDTO atualizar(
            Long id,
            EstoqueRequestDTO dto
    ) {
        validarAdmin();

        Estoque estoque = buscarEstoque(id);

        UnidadeProduto unidadeProduto =
                buscarUnidadeProduto(dto.getUnidadeProdutoId());

        estoqueRepository
                .findByUnidadeProdutoId(dto.getUnidadeProdutoId())
                .filter(registro ->
                        !registro.getId().equals(id))
                .ifPresent(registro -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Este produto já possui estoque cadastrado nesta unidade"
                    );
                });

        estoque.setUnidadeProduto(unidadeProduto);
        estoque.setQuantidadeAtual(dto.getQuantidadeAtual());
        estoque.setDataAtualizacao(LocalDateTime.now());

        Estoque atualizado =
                estoqueRepository.save(estoque);

        return estoqueMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {

        validarAdmin();

        Estoque estoque = buscarEstoque(id);

        estoqueRepository.delete(estoque);
    }

    private Estoque buscarEstoque(Long id) {

        return estoqueRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Estoque não encontrado"
                        )
                );
    }

    private UnidadeProduto buscarUnidadeProduto(Long id) {

        return unidadeProdutoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Produto da unidade não encontrado"
                        )
                );
    }

    private void validarAdmin() {

        boolean admin = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals("ROLE_ADMIN")
                );

        if (!admin) {
            throw new AccessDeniedException(
                    "Apenas administradores podem realizar esta operação"
            );
        }
    }
}