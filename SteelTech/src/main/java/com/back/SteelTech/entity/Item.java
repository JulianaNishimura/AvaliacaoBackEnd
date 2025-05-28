package com.back.SteelTech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoDeAco;
    private String especificacao;
    private Integer quantidade;

    @ManyToMany(mappedBy = "itens")
    private List<Pedido> pedidos = new ArrayList<>();
}
