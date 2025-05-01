package com.pancs.hortapancs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.util.DistanciaUtil;

@Service
public class LocalizacaoService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public String obterEnderecoPorCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = VIA_CEP_URL.replace("{cep}", cep);
        try {
            String response = restTemplate.getForObject(url, String.class);
            if (response != null && !response.contains("erro")) {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Instituicao encontrarInstituicaoMaisProxima(String cepVoluntario, List<Instituicao> instituicoes) {
        Instituicao maisProxima = null;
        int menorProximidade = Integer.MAX_VALUE;

        for (Instituicao instituicao : instituicoes) {
            int proximidade = DistanciaUtil.calcularProximidadeCep(instituicao.getCep(), cepVoluntario);

            if (proximidade < menorProximidade) {
                menorProximidade = proximidade;
                maisProxima = instituicao;
            }
        }

        return maisProxima;
    }
}
