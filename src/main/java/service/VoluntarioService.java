package com.pancs.hortapancs.service;

import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.model.Voluntario;
import com.pancs.hortapancs.repository.InstituicaoRepository;
import com.pancs.hortapancs.repository.VoluntarioRepository;
import com.pancs.hortapancs.util.DistanciaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    private static final int PROXIMIDADE_MAXIMA = 10; // Máxima distância (em quilômetros)

    // Método para salvar o voluntário com a instituição mais próxima
    public Voluntario salvarComInstituicaoMaisProxima(Voluntario voluntario) {
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        Instituicao maisProxima = null;
        int menorProximidade = Integer.MAX_VALUE;

        // Loop para calcular a proximidade de todas as instituições
        for (Instituicao inst : instituicoes) {
            // Verifique a proximidade com base no CEP
            int proximidade = DistanciaUtil.calcularProximidadeCep(inst.getCep(), voluntario.getCep());

            // Se a proximidade for menor que a proximidade mínima encontrada
            if (proximidade < menorProximidade) {
                menorProximidade = proximidade;
                maisProxima = inst;
            }
        }

        // Se a proximidade mínima for menor ou igual à proximidade máxima definida
        if (maisProxima != null && menorProximidade <= PROXIMIDADE_MAXIMA) {
            // Se sim, vincula a instituição mais próxima ao voluntário
            voluntario.setInstituicao(maisProxima);
        } else {
            // Caso contrário, não vincula nenhuma instituição automaticamente
            voluntario.setInstituicao(null);
        }

        return voluntarioRepository.save(voluntario); // Salva o voluntário no banco de dados
    }

    // Método para listar todos os voluntários
    public List<Voluntario> listarTodos() {
        return voluntarioRepository.findAll();
    }

    // Método para buscar voluntário por ID
    public Voluntario buscarPorId(Long id) {
        return voluntarioRepository.findById(id).orElse(null);
    }

    // Método para excluir voluntário por ID
    public void excluirPorId(Long id) {
        voluntarioRepository.deleteById(id);
    }

    // Método para mapear distâncias entre o CEP do voluntário e as instituições
    public Map<Long, Double> mapearDistancias(String cepVoluntario) {
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        Map<Long, Double> distancias = new HashMap<>();

        for (Instituicao inst : instituicoes) {
            double distancia = DistanciaUtil.calcularDistancia(cepVoluntario, inst.getCep());
            distancias.put(inst.getId(), distancia);
        }

        return distancias;
    }
}
