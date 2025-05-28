package com.back.SteelTech.dto;

import com.back.SteelTech.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteSaidaDTO {
    private String cpf;
    private String nome;
    private String endereco;
    private String contatoEmail;
    private List<Long> Idpedidos;
}
