package com.back.SteelTech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoSaidaDTO {
    private Long id;
    private String status;
    private LocalDateTime data;
    private String clienteCpf;
    private List<Long> Iditens;
}
