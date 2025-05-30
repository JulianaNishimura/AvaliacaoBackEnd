package com.back.SteelTech.repository;

import com.back.SteelTech.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findBycontatoEmail(String email);

    List<Cliente> findAllByNome(String nome);
}
