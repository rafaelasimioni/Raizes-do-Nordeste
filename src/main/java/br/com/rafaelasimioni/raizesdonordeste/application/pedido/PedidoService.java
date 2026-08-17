package br.com.rafaelasimioni.raizesdonordeste.application.pedido;

import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoItemRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pedido.mapper.PedidoMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.estoque.Estoque;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.Pedido;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.PedidoItem;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.StatusPedido;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidade.Unidade;
import br.com.rafaelasimioni.raizesdonordeste.domain.unidadeproduto.UnidadeProduto;
import br.com.rafaelasimioni.raizesdonordeste.domain.usuario.Usuario;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.estoque.EstoqueRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.pedido.PedidoRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidade.UnidadeRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.unidadeproduto.UnidadeProdutoRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final UnidadeProdutoRepository unidadeProdutoRepository;
    private final EstoqueRepository estoqueRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoResponseDTO cadastrar(
            PedidoRequestDTO dto
    ) {

        Usuario usuario = buscarUsuarioAutenticado();

        Unidade unidade = buscarUnidade(dto.getUnidadeId());

        if (!unidade.getAtivo()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível realizar pedido em uma unidade inativa"
            );
        }

        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(dto.getCanalPedido());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        LocalDateTime agora = LocalDateTime.now();

        pedido.setDataCriacao(agora);
        pedido.setDataAtualizacao(agora);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (PedidoItemRequestDTO itemDTO : dto.getItens()) {

            UnidadeProduto unidadeProduto =
                    buscarUnidadeProduto(
                            itemDTO.getUnidadeProdutoId()
                    );

            validarUnidadeDoProduto(
                    unidadeProduto,
                    unidade
            );

            if (!unidadeProduto.getDisponivel()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O produto não está disponível nesta unidade"
                );
            }

            Estoque estoque =
                    buscarEstoque(unidadeProduto.getId());

            if (estoque.getQuantidadeAtual()
                    < itemDTO.getQuantidade()) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Estoque insuficiente para o produto: "
                                + unidadeProduto.getProduto().getNome()
                );
            }

            PedidoItem item = new PedidoItem();

            item.setPedido(pedido);
            item.setUnidadeProduto(unidadeProduto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(unidadeProduto.getPreco());

            BigDecimal subtotal =
                    unidadeProduto.getPreco()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemDTO.getQuantidade()
                                    )
                            );

            valorTotal = valorTotal.add(subtotal);

            pedido.getItens().add(item);
        }

        pedido.setValorTotal(valorTotal);

        Pedido salvo = pedidoRepository.save(pedido);

        return pedidoMapper.toResponseDTO(salvo);
    }

    public PedidoResponseDTO buscarPorId(Long id) {

        Pedido pedido = buscarPedido(id);

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        boolean admin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals("ROLE_ADMIN")
                );

        if (!admin) {

            String email = authentication.getName();

            if (!pedido.getUsuario().getEmail().equals(email)) {
                throw new AccessDeniedException(
                        "Você não tem permissão para acessar este pedido"
                );
            }
        }

        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {

        validarAdmin();

        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    public List<PedidoResponseDTO> listarPorUsuario(
            Long usuarioId
    ) {

        validarAdmin();

        buscarUsuario(usuarioId);

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    public List<PedidoResponseDTO> listarPorUnidade(
            Long unidadeId
    ) {

        validarAdmin();

        buscarUnidade(unidadeId);

        return pedidoRepository.findByUnidadeId(unidadeId)
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    private Usuario buscarUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado"
                        )
                );
    }

    private Pedido buscarPedido(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pedido não encontrado"
                        )
                );
    }

    private Usuario buscarUsuario(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado"
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

    private UnidadeProduto buscarUnidadeProduto(Long id) {

        return unidadeProdutoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Produto da unidade não encontrado"
                        )
                );
    }

    private Estoque buscarEstoque(Long unidadeProdutoId) {

        return estoqueRepository
                .findByUnidadeProdutoId(unidadeProdutoId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Estoque não encontrado para o produto"
                        )
                );
    }

    private void validarUnidadeDoProduto(
            UnidadeProduto unidadeProduto,
            Unidade unidade
    ) {

        if (!unidadeProduto.getUnidade()
                .getId()
                .equals(unidade.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Todos os produtos do pedido devem pertencer à mesma unidade"
            );
        }
    }

    private void validarAdmin() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        boolean admin = authentication.getAuthorities()
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