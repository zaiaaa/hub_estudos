package com.zaia08.hub_estudos.repositories;

import com.zaia08.hub_estudos.Model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Deleta todas as avaliações que pertencem a um curso específico
    void deleteByFkIdCurso(Long fkIdCurso);
}
