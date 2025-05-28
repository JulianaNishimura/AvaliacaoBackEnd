package com.back.SteelTech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntradaDTO {
    private String cpf;
    private String nome;
    private String endereco;
    private String contatoEmail;
}
