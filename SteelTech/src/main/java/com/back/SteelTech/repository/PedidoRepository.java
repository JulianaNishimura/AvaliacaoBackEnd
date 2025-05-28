package com.back.SteelTech.repository;

import com.back.SteelTech.entity.Cliente;
import com.back.SteelTech.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findAllByCliente(Cliente cliente);

    Optional<Pedido> findAllByData(LocalDateTime data);

    Optional<Pedido> findAllByStatus(String status);
}
