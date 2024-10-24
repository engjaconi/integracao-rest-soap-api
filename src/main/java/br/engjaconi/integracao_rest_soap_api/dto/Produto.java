package br.engjaconi.integracao_rest_soap_api.dto;

import lombok.Data;

@Data
public class Produto {

    private Long id;
    private String nome;
    private Double preco;

}
