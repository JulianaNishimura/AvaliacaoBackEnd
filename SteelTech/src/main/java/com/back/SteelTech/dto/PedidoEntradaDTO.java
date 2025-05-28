package com.back.SteelTech.dto;

import com.back.SteelTech.entity.Cliente;
import com.back.SteelTech.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntradaDTO {
    private String status;
    private LocalDateTime data;

    private String clienteCpf;

    private List<Long> Iditens;
}
