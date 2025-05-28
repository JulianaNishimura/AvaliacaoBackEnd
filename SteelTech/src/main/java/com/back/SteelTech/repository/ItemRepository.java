package com.back.SteelTech.repository;

import com.back.SteelTech.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByTipoDeAco(String tipoDeAco);

    Optional<Item> findByTipoDeAco(String tipo);

    Optional<Item> findAllByEspecificacao(String especificacao);

    Optional<Item> findAllByQuantidade(Integer quantidade);
}
