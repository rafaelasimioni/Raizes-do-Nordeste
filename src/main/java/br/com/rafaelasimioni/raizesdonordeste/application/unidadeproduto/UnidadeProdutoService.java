package br.com.rafaelasimioni.raizesdonordeste.application.unidadeproduto;

import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto.UnidadeProdutoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.dto.UnidadeProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.unidadeproduto.mapper.UnidadeProdutoMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.produto.ProdutoRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidade.UnidadeRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidadeproduto.UnidadeProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeProdutoService {

    private final UnidadeProdutoRepository unidadeProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final UnidadeProdutoMapper unidadeProdutoMapper;

    public UnidadeProdutoResponseDTO cadastrar(
            UnidadeProdutoRequestDTO dto
    ) {
        validarAdmin();

        Produto produto = buscarProduto(dto.getProdutoId());
        Unidade unidade = buscarUnidade(dto.getUnidadeId());

        boolean jaCadastrado = unidadeProdutoRepository
                .findByProdutoAndUnidade(produto, unidade)
                .isPresent();

        if (jaCadastrado) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este produto já está cadastrado nesta unidade"
            );
        }

        UnidadeProduto unidadeProduto = new UnidadeProduto();

        unidadeProduto.setProduto(produto);
        unidadeProduto.setUnidade(unidade);
        unidadeProduto.setDisponivel(dto.getDisponivel());

        if (dto.getPreco() != null) {
            unidadeProduto.setPreco(dto.getPreco());
        } else {
            unidadeProduto.setPreco(produto.getPreco());
        }

        UnidadeProduto salvo =
                unidadeProdutoRepository.save(unidadeProduto);

        return unidadeProdutoMapper.toResponseDTO(salvo);
    }

    public List<UnidadeProdutoResponseDTO> listarTodos() {
        return unidadeProdutoRepository.findAll()
                .stream()
                .map(unidadeProdutoMapper::toResponseDTO)
                .toList();
    }

    public List<UnidadeProdutoResponseDTO> listarPorUnidade(
            Long unidadeId
    ) {
        buscarUnidade(unidadeId);

        return unidadeProdutoRepository.findByUnidadeId(unidadeId)
                .stream()
                .map(unidadeProdutoMapper::toResponseDTO)
                .toList();
    }

    public List<UnidadeProdutoResponseDTO> listarPorProduto(
            Long produtoId
    ) {
        buscarProduto(produtoId);

        return unidadeProdutoRepository.findByProdutoId(produtoId)
                .stream()
                .map(unidadeProdutoMapper::toResponseDTO)
                .toList();
    }

    public List<UnidadeProdutoResponseDTO> listarDisponiveisParaCliente() {
        return unidadeProdutoRepository
                .findByDisponivelTrueAndUnidadeAtivoTrue()
                .stream()
                .map(unidadeProdutoMapper::toResponseDTO)
                .toList();
    }

    public UnidadeProdutoResponseDTO buscarPorId(Long id) {
        UnidadeProduto unidadeProduto =
                buscarUnidadeProduto(id);

        return unidadeProdutoMapper.toResponseDTO(unidadeProduto);
    }

    public UnidadeProdutoResponseDTO atualizar(
            Long id,
            UnidadeProdutoRequestDTO dto
    ) {
        validarAdmin();

        UnidadeProduto unidadeProduto =
                buscarUnidadeProduto(id);

        Produto produto = buscarProduto(dto.getProdutoId());
        Unidade unidade = buscarUnidade(dto.getUnidadeId());

        unidadeProdutoRepository
                .findByProdutoAndUnidade(produto, unidade)
                .filter(registro ->
                        !registro.getId().equals(id))
                .ifPresent(registro -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Este produto já está cadastrado nesta unidade"
                    );
                });

        unidadeProduto.setProduto(produto);
        unidadeProduto.setUnidade(unidade);
        unidadeProduto.setDisponivel(dto.getDisponivel());

        if (dto.getPreco() != null) {
            unidadeProduto.setPreco(dto.getPreco());
        } else {
            unidadeProduto.setPreco(produto.getPreco());
        }

        UnidadeProduto atualizado =
                unidadeProdutoRepository.save(unidadeProduto);

        return unidadeProdutoMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        validarAdmin();

        UnidadeProduto unidadeProduto =
                buscarUnidadeProduto(id);

        unidadeProdutoRepository.delete(unidadeProduto);
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

    private Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Produto não encontrado"
                        )
                );
    }

    private Unidade buscarUnidade(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Unidade não encontrada"
                        )
                );
    }

    private void validarAdmin() {
        boolean admin = SecurityContextHolder.getContext()
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