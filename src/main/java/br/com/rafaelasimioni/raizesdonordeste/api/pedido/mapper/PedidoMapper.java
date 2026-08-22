package br.com.rafaelasimioni.raizesdonordeste.api.pedido.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoItemResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.pedido.dto.PedidoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.Pedido;
import br.com.rafaelasimioni.raizesdonordeste.domain.pedido.PedidoItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {

        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(pedido.getId());

        dto.setUsuarioId(
                pedido.getUsuario().getId()
        );

        dto.setUnidadeId(
                pedido.getUnidade().getId()
        );

        dto.setNomeUnidade(
                pedido.getUnidade().getNome()
        );

        dto.setCanalPedido(
                pedido.getCanalPedido()
        );

        dto.setStatus(
                pedido.getStatus()
        );

        dto.setValorTotal(
                pedido.getValorTotal()
        );

        dto.setDataCriacao(
                pedido.getDataCriacao()
        );

        dto.setDataAtualizacao(
                pedido.getDataAtualizacao()
        );

        List<PedidoItemResponseDTO> itens = pedido.getItens()
                .stream()
                .map(this::toItemResponseDTO)
                .toList();

        dto.setItens(itens);

        return dto;
    }

    private PedidoItemResponseDTO toItemResponseDTO(
            PedidoItem item
    ) {

        PedidoItemResponseDTO dto =
                new PedidoItemResponseDTO();

        dto.setId(item.getId());

        dto.setUnidadeProdutoId(
                item.getUnidadeProduto().getId()
        );

        dto.setNomeProduto(
                item.getUnidadeProduto()
                        .getProduto()
                        .getNome()
        );

        dto.setQuantidade(
                item.getQuantidade()
        );

        dto.setPrecoUnitario(
                item.getPrecoUnitario()
        );

        dto.setSubtotal(
                item.getPrecoUnitario()
                        .multiply(
                                BigDecimal.valueOf(item.getQuantidade())
                        )
        );

        return dto;
    }
}