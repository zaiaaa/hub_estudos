package com.zaia08.hub_estudos.controller;

import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import com.zaia08.hub_estudos.service.AvaliacaoService;
import com.zaia08.hub_estudos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    CursoRepository repository;

    private final CursoService cursoService;
    private final AvaliacaoService avaliacaoService;

    public CursoController(CursoService cursoService, AvaliacaoService avaliacaoService) {
        this.cursoService = cursoService;
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getCurso(){
        List<Curso> listCurso = cursoService.getCursos();
        return ResponseEntity.status(HttpStatus.OK).body(listCurso);
    }

    @PostMapping
    public ResponseEntity<Curso> addCurso(@RequestBody CreateCursoDTO createCursoDTO){
        var cursoSalvo = cursoService.createCurso(createCursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Curso> alterCurso(
            @PathVariable Long id,
            @RequestBody AlterCursoDTO dto
    ){

        Curso curso = cursoService.alterCurso(dto, id);
        return ResponseEntity.ok(curso);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Curso> deleteCurso(@PathVariable Long id){
        boolean deletedAvaliacao = avaliacaoService.deleteAvaliacao(id);
        boolean deletedCurso = cursoService.deleteCurso(id);

        if(deletedCurso && deletedAvaliacao){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
