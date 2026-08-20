package br.com.rafaelasimioni.raizesdonordeste.api.pagamento.mapper;

import br.com.rafaelasimioni.raizesdonordeste.api.pagamento.dto.PagamentoResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.domain.pagamento.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {

        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());

        dto.setPedidoId(
                pagamento.getPedido().getId()
        );

        dto.setStatus(
                pagamento.getStatus()
        );

        dto.setValor(
                pagamento.getValor()
        );

        dto.setDataSolicitacao(
                pagamento.getDataSolicitacao()
        );

        dto.setDataResposta(
                pagamento.getDataResposta()
        );

        return dto;
    }
}