package com.zaia08.hub_estudos.service;

import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.controller.AlterCursoDTO;
import com.zaia08.hub_estudos.controller.CreateCursoDTO;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso createCurso(CreateCursoDTO createCursoDTO){
        //DTO -> Entity; Gravar entity.

        var entity = new Curso();
        entity.setNomeCurso(createCursoDTO.nome_curso());
        entity.setHorasDesejadas(createCursoDTO.horas_desejadas());
        entity.setHorasAtuais(createCursoDTO.horas_atuais());
        entity.setMetaDeConclusao(createCursoDTO.meta_de_conclusao());
        entity.setResumoSemanal(createCursoDTO.resumo_semanal());
        entity.setPrioridade(createCursoDTO.prioridade());

        return cursoRepository.save(entity);
    }

    public List<Curso> getCursos(){
        return cursoRepository.findAll();
    }

    public Curso alterCurso(AlterCursoDTO alterCursoDTO, Long id){

        Curso entity = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));


        if (alterCursoDTO.nome_curso() != null) {
            entity.setNomeCurso(alterCursoDTO.nome_curso());
        }

        if (alterCursoDTO.horas_desejadas() != null) {
            entity.setHorasDesejadas(alterCursoDTO.horas_desejadas());
        }

        if (alterCursoDTO.horas_atuais() != null) {

            entity.setHorasAtuais(BigDecimal.valueOf(alterCursoDTO.horas_atuais())
                    .setScale(2, RoundingMode.HALF_UP).
                    floatValue());
        }

        if (alterCursoDTO.meta_de_conclusao() != null) {
            entity.setMetaDeConclusao(alterCursoDTO.meta_de_conclusao());
        }

        if (alterCursoDTO.resumo_semanal() != null) {
            entity.setResumoSemanal(alterCursoDTO.resumo_semanal());
        }

        return cursoRepository.save(entity);

    }

    public boolean deleteCurso(Long id){
        if(!cursoRepository.existsById(id)){
            return false;
        }

        cursoRepository.deleteById(id);
        return true;
    }

}
