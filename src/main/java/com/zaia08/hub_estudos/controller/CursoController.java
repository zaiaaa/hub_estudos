package com.zaia08.hub_estudos.controller;

import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.repositories.CursoRepository;
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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    CursoRepository repository;

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getCurso(){
        List<Curso> listCurso = cursoService.getCursos();
        return ResponseEntity.status(HttpStatus.OK).body(listCurso);
    }

    @PostMapping
    public ResponseEntity<Curso> addCurso(@RequestBody CreateCursoDTO createCursoDTO){
        var cursoId = cursoService.createCurso(createCursoDTO);
        return ResponseEntity.created(URI.create("curso/" + cursoId)).build();
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Curso> alterCurso(
            @PathVariable int id,
            @RequestBody AlterCursoDTO dto
    ){

        Curso curso = cursoService.alterCurso(dto, id);
        return ResponseEntity.ok(curso);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Curso> deleteCurso(@PathVariable int id){
        boolean deleted = cursoService.deleteCurso(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
