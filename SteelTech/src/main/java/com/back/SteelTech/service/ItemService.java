package com.back.SteelTech.service;

import com.back.SteelTech.dto.*;
import com.back.SteelTech.entity.Cliente;
import com.back.SteelTech.entity.Item;
import com.back.SteelTech.entity.Pedido;
import com.back.SteelTech.repository.ClienteRepository;
import com.back.SteelTech.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ResponseEntity<List<ItemSaidaDTO>> listarTodosOsItens() {
        List<ItemSaidaDTO> itens = itemRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(itens);
    }

    public ResponseEntity<ItemSaidaDTO> listarTodosOsItensPorTipo(String tipo) {
        return ResponseEntity.ok(toDto(itemRepository.findByTipoDeAco(tipo).get()));
    }

    public ResponseEntity<List<ItemSaidaDTO>> listarTodosOsItensPorEspecificacao(String especificacao) {
        List<ItemSaidaDTO> itens = itemRepository.findAllByEspecificacao(especificacao)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(itens);
    }

    public ResponseEntity<List<ItemSaidaDTO>> listarTodosOsItensPorQuantidade(Integer quantidade) {
        List<ItemSaidaDTO> itens = itemRepository.findAllByQuantidade(quantidade)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(itens);
    }

    public ResponseEntity<String> cadastrarItem(ItemEntradaDTO itemEntradaDTO) {
        if(itemRepository.existsByTipoDeAco(itemEntradaDTO.getTipoDeAco())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de aço já cadastrado");
        }

        itemRepository.save(toEntity(itemEntradaDTO));
        return ResponseEntity.ok("Tipo de Aço cadastrado");
    }

    public ResponseEntity<String> atualizarItem(Long id, ItemEntradaDTO itemEntradaDTO) {
        Item itemParaSerAtualizado = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Item não encontrado."));

        itemParaSerAtualizado.setTipoDeAco(itemEntradaDTO.getTipoDeAco());
        itemParaSerAtualizado.setEspecificacao(itemEntradaDTO.getEspecificacao());
        itemParaSerAtualizado.setQuantidade(itemEntradaDTO.getQuantidade());
        itemRepository.save(itemParaSerAtualizado);
        return ResponseEntity.ok("Item atualizado com sucesso");
    }

    public ResponseEntity<String> deletarItem(Long id) {
        if(!itemRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de aço não encontrado");
        }

        itemRepository.deleteById(id);
        return ResponseEntity.ok("Item deletado");
    }

    private ItemSaidaDTO toDto(Item item) {
        return new ItemSaidaDTO(
                item.getId(),
                item.getTipoDeAco(),
                item.getEspecificacao(),
                item.getQuantidade()
        );
    }

    private Item toEntity(ItemEntradaDTO dto) {
        Item item = new Item();
        item.setTipoDeAco(dto.getTipoDeAco());
        item.setEspecificacao(dto.getEspecificacao());
        item.setQuantidade(dto.getQuantidade());
        return item;
    }
}
