package com.zaia08.hub_estudos.service;

import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.controller.AlterCursoDTO;
import com.zaia08.hub_estudos.controller.CreateCursoDTO;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public long createCurso(CreateCursoDTO createCursoDTO){
        //DTO -> Entity; Gravar entity.

        var entity = new Curso();
        entity.setNomeCurso(createCursoDTO.nome_curso());
        entity.setHorasDesejadas(createCursoDTO.horas_desejadas());
        entity.setHorasAtuais(createCursoDTO.horas_atuais());
        entity.setMetaDeConclusao(createCursoDTO.meta_de_conclusao());
        entity.setResumoSemanal(createCursoDTO.resumo_semanal());
        var cursoSaved = cursoRepository.save(entity);

        return cursoSaved.getId();
    }

    public List<Curso> getCursos(){
        return cursoRepository.findAll();
    }

    public Curso alterCurso(AlterCursoDTO alterCursoDTO, int id){

        Curso entity = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));


        entity.setNomeCurso(alterCursoDTO.nome_curso());
        entity.setHorasDesejadas(alterCursoDTO.horas_desejadas());
        entity.setHorasAtuais(alterCursoDTO.horas_atuais());
        entity.setMetaDeConclusao(alterCursoDTO.meta_de_conclusao());
        entity.setResumoSemanal(alterCursoDTO.resumo_semanal());
        return cursoRepository.save(entity);



    }

}
