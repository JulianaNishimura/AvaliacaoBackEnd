package com.back.SteelTech.controller;

import com.back.SteelTech.dto.ClienteSaidaDTO;
import com.back.SteelTech.dto.PedidoEntradaDTO;
import com.back.SteelTech.dto.PedidoSaidaDTO;
import com.back.SteelTech.service.ClienteService;
import com.back.SteelTech.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    @GetMapping
    public ResponseEntity<List<PedidoSaidaDTO>> listarPedidos(){
        return pedidoService.listarTodosOsPedidos();
    }

    @GetMapping("/get/cpfDoCliente")
    public ResponseEntity<?> listarPedidosPorCpf(@PathVariable String cpfDoCliente){
        return pedidoService.listarPedidosPorCliente(cpfDoCliente);
    }

    @GetMapping("/get/data")
    public ResponseEntity<List<PedidoSaidaDTO>> listarPedidosPorData(@PathVariable LocalDateTime data){
        return pedidoService.listarPedidosPelaData(data);
    }

    @GetMapping("/get/status")
    public ResponseEntity<List<PedidoSaidaDTO>> listarPedidosPorStatus(@PathVariable String status){
        return pedidoService.listarPedidosPeloStatus(status);
    }

    @PostMapping
    public ResponseEntity<String> postarPedido(@RequestBody PedidoEntradaDTO pedidoEntradaDTO){
        return pedidoService.cadastrarPedido(pedidoEntradaDTO);
    }

    @PutMapping("/atualizar/{idDoPedido}")
    public ResponseEntity<String> atualizarPedido(@RequestParam Long idDoPedido, @RequestBody PedidoEntradaDTO pedidoEntradaDTO){
        return pedidoService.atualizarPedido(idDoPedido,pedidoEntradaDTO);    }

    @DeleteMapping("/deletar/{idDoPedido}")
    public ResponseEntity<String> deletarPedido(@PathVariable Long idDoPedido){
        return pedidoService.deletarOPedido(idDoPedido);
    }
}
