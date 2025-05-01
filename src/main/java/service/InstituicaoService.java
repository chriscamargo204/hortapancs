package com.pancs.hortapancs.service;

import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public List<Instituicao> listarInstituicoes() {
        return instituicaoRepository.findAll();
    }

    public Instituicao buscarPorId(Long id) {
        return instituicaoRepository.findById(id).orElse(null);
    }

    public Instituicao salvar(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public void excluir(Long id) {
        instituicaoRepository.deleteById(id);
    }
}
