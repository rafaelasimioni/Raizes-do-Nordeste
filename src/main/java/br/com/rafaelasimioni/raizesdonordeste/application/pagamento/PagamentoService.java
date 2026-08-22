package br.com.rafaelasimioni.raizesdonordeste.application.pagamento;

import br.com.rafaelasimioni.raizesdonordeste.api.pagamento.dto.PagamentoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pagamento.mapper.PagamentoMapper;
import br.com.rafaelasimioni.raizesdonordeste.domain.estoque.Estoque;
import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.Pagamento;
import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.StatusPagamento;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.Pedido;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.PedidoItem;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.StatusPedido;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.estoque.EstoqueRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.pagamento.PagamentoRepository;
import br.com.rafaelasimioni.raizesdonordeste.infrastructure.pedido.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;
    private final PagamentoMapper pagamentoMapper;

    @Transactional
    public PagamentoResponseDTO processarPagamento(
            Long pedidoId,
            boolean aprovado
    ) {

        Pedido pedido = buscarPedido(pedidoId);

        validarDonoDoPedido(pedido);

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Este pedido não está aguardando pagamento"
            );
        }

        if (pagamentoRepository.findByPedidoId(pedidoId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este pedido já possui um pagamento"
            );
        }

        LocalDateTime agora = LocalDateTime.now();

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setDataSolicitacao(agora);
        pagamento.setDataResposta(agora);

        if (aprovado) {

            pagamento.setStatus(StatusPagamento.APROVADO);

            baixarEstoque(pedido);

            pedido.setStatus(StatusPedido.EM_PREPARO);

        } else {

            pagamento.setStatus(StatusPagamento.RECUSADO);

            pedido.setStatus(StatusPedido.CANCELADO);
        }

        pedido.setDataAtualizacao(agora);

        pedidoRepository.save(pedido);

        Pagamento salvo = pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDTO(salvo);
    }

    private void baixarEstoque(Pedido pedido) {

        for (PedidoItem item : pedido.getItens()) {

            Estoque estoque = estoqueRepository
                    .findByUnidadeProdutoId(
                            item.getUnidadeProduto().getId()
                    )
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Estoque não encontrado para o produto: "
                                            + item.getUnidadeProduto()
                                            .getProduto()
                                            .getNome()
                            )
                    );

            if (estoque.getQuantidadeAtual()
                    < item.getQuantidade()) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Estoque insuficiente para o produto: "
                                + item.getUnidadeProduto()
                                .getProduto()
                                .getNome()
                );
            }

            estoque.setQuantidadeAtual(
                    estoque.getQuantidadeAtual()
                            - item.getQuantidade()
            );

            estoque.setDataAtualizacao(
                    LocalDateTime.now()
            );

            estoqueRepository.save(estoque);
        }
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

    private void validarDonoDoPedido(Pedido pedido) {

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

        if (admin) {
            return;
        }

        String email = authentication.getName();

        if (!pedido.getUsuario().getEmail().equals(email)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para realizar o pagamento deste pedido"
            );
        }
    }
}