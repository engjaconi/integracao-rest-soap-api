package br.engjaconi.integracao_rest_soap_api.client;

import br.engjaconi.integracao_rest_soap_api.dto.Produto;
import jakarta.xml.soap.*;
import org.springframework.stereotype.Service;

@Service
public class SoapClient {

    private final String soapEndpoint = "http://localhost:8585/rest/lg-api/1.0.0/pets";
    private final String soapAction = "SOAP_ACTION";
    private final String username = "SEU_USUARIO";
    private final String password = "SUA_SENHA";

    public String enviarProduto(Produto produto) {
        try {
            // Criação da mensagem SOAP
            SOAPMessage soapMessage = createSOAPRequest(produto);

            // Envio da requisição SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpoint);

            // Processamento da resposta SOAP
            return soapResponse.getSOAPBody().getTextContent();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar produto via SOAP", e);
        }
    }

    private SOAPMessage createSOAPRequest(Produto produto) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Construção do envelope SOAP
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("urn", "http://example.com/produto");

        // Adiciona cabeçalho para autenticação
        SOAPHeader soapHeader = envelope.getHeader();
        SOAPElement security = soapHeader.addChildElement("Security", "wsse", "http://schemas.xmlsoap.org/ws/2002/07/secext");
        SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
        SOAPElement usernameElement = usernameToken.addChildElement("Username", "wsse");
        usernameElement.addTextNode(username);
        SOAPElement passwordElement = usernameToken.addChildElement("Password", "wsse");
        passwordElement.addTextNode(password);

        // Construção do corpo SOAP
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("Produto", "urn");
        soapBodyElem.addChildElement("id", "urn").addTextNode(produto.getId().toString());
        soapBodyElem.addChildElement("nome", "urn").addTextNode(produto.getNome());
        soapBodyElem.addChildElement("preco", "urn").addTextNode(produto.getPreco().toString());

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        return soapMessage;
    }

}