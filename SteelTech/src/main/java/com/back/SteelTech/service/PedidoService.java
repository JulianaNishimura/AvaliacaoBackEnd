package com.back.SteelTech.service;

import com.back.SteelTech.dto.*;
import com.back.SteelTech.entity.Cliente;
import com.back.SteelTech.entity.Item;
import com.back.SteelTech.entity.Pedido;
import com.back.SteelTech.repository.ClienteRepository;
import com.back.SteelTech.repository.ItemRepository;
import com.back.SteelTech.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    private final ClienteRepository clienteRepository;

    private final ItemRepository itemRepository;

    public ResponseEntity<List<PedidoSaidaDTO>> listarTodosOsPedidos() {
        List<PedidoSaidaDTO> pedidos = pedidoRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    public ResponseEntity<?> listarPedidosPorCliente(String cpfDoCliente) {
        if(!clienteRepository.existsById(cpfDoCliente)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf não encontrado");
        }

        Cliente cliente = clienteRepository.findById(cpfDoCliente).get();

        List<PedidoSaidaDTO> pedidos = pedidoRepository.findAllByCliente(cliente)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    public ResponseEntity<List<PedidoSaidaDTO>> listarPedidosPelaData(LocalDateTime data) {
        List<PedidoSaidaDTO> pedidos = pedidoRepository.findAllByData(data)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    public ResponseEntity<List<PedidoSaidaDTO>> listarPedidosPeloStatus(String status) {
        List<PedidoSaidaDTO> pedidos = pedidoRepository.findAllByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    public ResponseEntity<String> cadastrarPedido(PedidoEntradaDTO pedidoEntradaDTO) {
        List<Long> ids = pedidoEntradaDTO.getIditens();
        for(int i = 0; i < ids.size(); i++){
            Item item = itemRepository.findById(ids.get(i)).get();
            if(item.getQuantidade() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O item está esgotado");
            } else {
                Integer quantidadeAtualizada = item.getQuantidade() - 1;
                item.setQuantidade(quantidadeAtualizada);
                itemRepository.save(item);
            }
        }
        pedidoRepository.save(toEntity(pedidoEntradaDTO));
        return ResponseEntity.ok("Pedido cadastrado");
    }

    public ResponseEntity<String> atualizarPedido(Long idDoPedido, PedidoEntradaDTO pedidoEntradaDTO) {
        Pedido pedidoAtualizado = pedidoRepository.findById(idDoPedido)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));

        for(Item item : pedidoAtualizado.getItens()){
            Integer quantidadeAtualizada = item.getQuantidade() + 1;
            item.setQuantidade(quantidadeAtualizada);
            itemRepository.save(item);
        }

        pedidoAtualizado.setStatus(pedidoEntradaDTO.getStatus());
        pedidoAtualizado.setData(pedidoEntradaDTO.getData());
        pedidoAtualizado.setCliente(clienteRepository.findById(pedidoEntradaDTO.getClienteCpf()).get());
        pedidoAtualizado.setItens(itemRepository.findAllById(pedidoEntradaDTO.getIditens()));
        pedidoRepository.save(pedidoAtualizado);

        List<Long> ids = pedidoEntradaDTO.getIditens();
        for(int i = 0; i < ids.size(); i++){
            Item item = itemRepository.findById(ids.get(i)).get();
            if(item.getQuantidade() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O item está esgotado");
            } else {
                Integer quantidadeAtualizada = item.getQuantidade() - 1;
                item.setQuantidade(quantidadeAtualizada);
                itemRepository.save(item);
            }
        }

        return ResponseEntity.ok("Pedido atualizado com sucesso");
    }

    public ResponseEntity<String> deletarOPedido(Long idDoPedido) {
        if(!pedidoRepository.existsById(idDoPedido)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedido não encontrado");
        }
        pedidoRepository.deleteById(idDoPedido);
        return ResponseEntity.ok("Pedido deletado");
    }

    private PedidoSaidaDTO toDto(Pedido pedido) {
        List<Long> itensId = Optional.ofNullable(pedido.getItens())
                .orElse(Collections.emptyList())
                .stream()
                .map(Item::getId)
                .collect(Collectors.toList());

        String clienteCpf = pedido.getCliente().getCpf();

        return new PedidoSaidaDTO(
                pedido.getId(),
                pedido.getStatus(),
                pedido.getData(),
                clienteCpf,
                itensId
        );
    }

    private Pedido toEntity(PedidoEntradaDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setStatus(dto.getStatus());
        pedido.setData(dto.getData());
        pedido.setCliente(clienteRepository.findById(dto.getClienteCpf()).get());
        pedido.setItens(itemRepository.findAllById(dto.getIditens()));
        return pedido;
    }
}
