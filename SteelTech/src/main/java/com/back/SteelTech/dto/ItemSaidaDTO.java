package com.back.SteelTech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSaidaDTO {
    private Long id;
    private String tipoDeAco;
    private String especificacao;
    private Integer quantidade;
}
