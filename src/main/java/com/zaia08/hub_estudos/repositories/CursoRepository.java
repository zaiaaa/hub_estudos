package com.zaia08.hub_estudos.repositories;

import com.zaia08.hub_estudos.Model.Curso;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
