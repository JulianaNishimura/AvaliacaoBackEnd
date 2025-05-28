package com.back.SteelTech.controller;

import com.back.SteelTech.dto.ClienteEntradaDTO;
import com.back.SteelTech.dto.ClienteSaidaDTO;
import com.back.SteelTech.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    @GetMapping
    public ResponseEntity<List<ClienteSaidaDTO>> listarClientes(){
        return clienteService.listarTodosOsClientes();
    }

    @GetMapping("/get/nome")
    public ResponseEntity<List<ClienteSaidaDTO>> listarClientesPorNome(@PathVariable String nome){
        return clienteService.listarTodosOsClientesPorNome(nome);
    }

    @GetMapping("/get/cpf")
    public ResponseEntity<ClienteSaidaDTO> listarClientePorCpf(@PathVariable String cpf){
        return clienteService.listarClientesPorCpf(cpf);
    }

    @GetMapping("/get/email")
    public ResponseEntity<ClienteSaidaDTO> listarClientesPorEmail(@PathVariable String email){
        return clienteService.listarClientesPorEmail(email);
    }

    @PostMapping
    public ResponseEntity<String> postarClientes(@RequestBody ClienteEntradaDTO clienteEntradaDTO){
        return clienteService.cadastrarCliente(clienteEntradaDTO);
    }

    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<String> atualizarClientes(@RequestParam String cpf, @RequestBody ClienteEntradaDTO clienteEntradaDTO){
        return clienteService.atualizarCliente(cpf,clienteEntradaDTO);
    }

    @DeleteMapping("/deletar/{cpf}")
    public ResponseEntity<String> deletarClientes(@PathVariable String cpf){
        return clienteService.deletarCliente(cpf);
    }
}
