package com.pancs.hortapancs.repository;

import com.pancs.hortapancs.model.Voluntario;
import com.pancs.hortapancs.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
    List<Voluntario> findByInstituicao(Instituicao instituicao); // Buscar voluntários pela instituição
}
