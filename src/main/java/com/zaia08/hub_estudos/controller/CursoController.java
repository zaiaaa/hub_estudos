package com.zaia08.hub_estudos.controller;

import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    CursoRepository repository;


    @GetMapping
    public ResponseEntity getCurso(){
        List<Curso> listCurso = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listCurso);
    }

}
