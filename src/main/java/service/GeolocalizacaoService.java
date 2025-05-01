
package com.pancs.hortapancs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeolocalizacaoService {

    private static final Logger logger = LoggerFactory.getLogger(GeolocalizacaoService.class);
    private final RestTemplate restTemplate;

    public GeolocalizacaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para buscar o endereço a partir do CEP usando a API ViaCEP
    public String obterEnderecoPorCEP(String cep) {
        try {
            // Consulta ViaCEP
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            String response = restTemplate.getForObject(url, String.class);

            // Parseando a resposta JSON para obter o logradouro
            JsonNode rootNode = new ObjectMapper().readTree(response);
            if (rootNode.has("logradouro")) {
                String logradouro = rootNode.path("logradouro").asText();
                String bairro = rootNode.path("bairro").asText();
                String cidade = rootNode.path("localidade").asText();
                String estado = rootNode.path("uf").asText();

                String enderecoCompleto = logradouro + ", " + bairro + ", " + cidade + " - " + estado;
                logger.info("Endereço encontrado: {}", enderecoCompleto);
                return enderecoCompleto;
            } else {
                logger.error("Não foi possível obter o endereço para o CEP: {}", cep);
            }
        } catch (Exception e) {
            logger.error("Erro ao obter endereço: {}", e.getMessage(), e);
        }
        return null;
    }
}
