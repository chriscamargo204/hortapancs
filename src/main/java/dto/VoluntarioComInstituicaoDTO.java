package com.pancs.hortapancs.dto;

import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.model.Voluntario;

public class VoluntarioComInstituicaoDTO {
    private Voluntario voluntario;
    private Instituicao instituicao;

    public VoluntarioComInstituicaoDTO(Voluntario voluntario, Instituicao instituicao) {
        this.voluntario = voluntario;
        this.instituicao = instituicao;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}
