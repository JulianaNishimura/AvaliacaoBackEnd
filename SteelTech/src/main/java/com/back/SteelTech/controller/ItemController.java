package com.back.SteelTech.controller;

import com.back.SteelTech.dto.ItemEntradaDTO;
import com.back.SteelTech.dto.ItemSaidaDTO;
import com.back.SteelTech.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping
    public ResponseEntity<List<ItemSaidaDTO>> listarItens(){
        return itemService.listarTodosOsItens();
    }

    @GetMapping("/get/tipo/{tipo}")
    public ResponseEntity<ItemSaidaDTO> listarItensPorTipo(@PathVariable String tipo){
        return itemService.listarTodosOsItensPorTipo(tipo);
    }

    @GetMapping("/get/especificacao/{especificacao}")
    public ResponseEntity<List<ItemSaidaDTO>> listarItensPorEspecificacao(@PathVariable String especificacao){
        return itemService.listarTodosOsItensPorEspecificacao(especificacao);
    }

    @GetMapping("/get/quantidade/{quantidade}")
    public ResponseEntity<List<ItemSaidaDTO>> listarItensPorQuantidade(@PathVariable Integer quantidade){
        return itemService.listarTodosOsItensPorQuantidade(quantidade);
    }

    @PostMapping
    public ResponseEntity<String> postarItens(@RequestBody ItemEntradaDTO itemEntradaDTO){
        return itemService.cadastrarItem(itemEntradaDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarItem(@RequestParam Long id,@RequestBody ItemEntradaDTO itemEntradaDTO){
        return itemService.atualizarItem(id,itemEntradaDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarItem(@PathVariable Long id){
        return itemService.deletarItem(id);
    }
}
