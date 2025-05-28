package com.back.SteelTech.service;

import com.back.SteelTech.dto.ClienteEntradaDTO;
import com.back.SteelTech.dto.ClienteSaidaDTO;
import com.back.SteelTech.entity.Cliente;
import com.back.SteelTech.entity.Pedido;
import com.back.SteelTech.repository.ClienteRepository;
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
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ResponseEntity<List<ClienteSaidaDTO>> listarTodosOsClientes() {
        List<ClienteSaidaDTO> clientes = clienteRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    public ResponseEntity<List<ClienteSaidaDTO>> listarTodosOsClientesPorNome(String nome) {
        List<ClienteSaidaDTO> clientes = clienteRepository.findAllByNome(nome)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    public ResponseEntity<ClienteSaidaDTO> listarClientesPorCpf(String cpf) {
        return ResponseEntity.ok(toDto(clienteRepository.findById(cpf).get()));
    }

    public ResponseEntity<ClienteSaidaDTO> listarClientesPorEmail(String email) {
        return ResponseEntity.ok(toDto(clienteRepository.findBycontatoEmail(email).get()));
    }

    public ResponseEntity<String> cadastrarCliente(ClienteEntradaDTO clienteEntradaDTO) {
        if(clienteRepository.existsById(clienteEntradaDTO.getCpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cpf já cadastrado");
        }

        clienteRepository.save(toEntity(clienteEntradaDTO));
        return ResponseEntity.ok("Cliente cadastrado com sucesso");
    }

    public ResponseEntity<String> atualizarCliente(String cpf, ClienteEntradaDTO clienteEntradaDTO) {
        Cliente clienteParaSerAtualizado = clienteRepository.findById(cpf)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));

        clienteParaSerAtualizado.setNome(clienteEntradaDTO.getNome());
        clienteParaSerAtualizado.setEndereco(clienteEntradaDTO.getEndereco());
        clienteParaSerAtualizado.setContatoEmail(clienteEntradaDTO.getContatoEmail());
        clienteRepository.save(clienteParaSerAtualizado);
        return ResponseEntity.ok("Cliente atualizado com sucesso");
    }

    public ResponseEntity<String> deletarCliente(String cpf) {
        if(!clienteRepository.existsById(cpf)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado");
        }

        clienteRepository.deleteById(cpf);
        return ResponseEntity.ok("Cliente deletado com sucesso");
    }

    private ClienteSaidaDTO toDto(Cliente cliente) {
        List<Long> pedidosIds = Optional.ofNullable(cliente.getPedidos())
                .orElse(Collections.emptyList())
                .stream()
                .map(Pedido::getId)
                .collect(Collectors.toList());

        return new ClienteSaidaDTO(
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getEndereco(),
                cliente.getNome(),
                pedidosIds
        );
    }

    private Cliente toEntity(ClienteEntradaDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setCpf(dto.getCpf());
        cliente.setNome(dto.getNome());
        cliente.setEndereco(dto.getEndereco());
        cliente.setContatoEmail(dto.getContatoEmail());
        return cliente;
    }
}
