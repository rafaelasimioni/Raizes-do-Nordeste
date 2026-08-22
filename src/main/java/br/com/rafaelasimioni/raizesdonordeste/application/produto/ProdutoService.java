package br.com.rafaelasimioni.raizesdonordeste.application.produto;

import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.produto.dto.ProdutoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.produto.mapper.ProdutoMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.CategoriaProduto;
import br.com.rafaelasimioni.raizesdonordeste.domain.produto.Produto;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Transactional
    public ProdutoResponseDTO cadastrar(ProdutoRequestDTO dto) {
        verificarAdmin();

        Produto produto = produtoMapper.toEntity(dto);
        produto.setAtivo(true);

        Produto produtoSalvo = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    public List<ProdutoResponseDTO> listarPorCategoria(
            CategoriaProduto categoria) {

        return produtoRepository
                .findByCategoriaAndAtivoTrue(categoria)
                .stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto não encontrado"));

        if (!produto.getAtivo()) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        return produtoMapper.toResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(
            Long id,
            ProdutoRequestDTO dto) {

        verificarAdmin();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());

        Produto produtoAtualizado = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        verificarAdmin();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto não encontrado"));

        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    private void verificarAdmin() {
        boolean admin = org.springframework.security.core.context
                .SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!admin) {
            throw new AccessDeniedException(
                    "Apenas administradores podem realizar esta operação");
        }
    }
}