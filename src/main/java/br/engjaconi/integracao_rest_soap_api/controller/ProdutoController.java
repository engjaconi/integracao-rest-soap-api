package br.engjaconi.integracao_rest_soap_api.controller;

import br.engjaconi.integracao_rest_soap_api.client.SoapClient;
import br.engjaconi.integracao_rest_soap_api.dto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private SoapClient soapClient;

    @PostMapping(consumes=APPLICATION_XML_VALUE)
    public ResponseEntity<String> criarProduto(@RequestBody Produto produto) {
        String response = soapClient.enviarProduto(produto);
        return ResponseEntity.ok(response);
    }

}