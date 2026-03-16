package com.zaia08.hub_estudos.controller;


import com.zaia08.hub_estudos.Model.Avaliacao;
import com.zaia08.hub_estudos.Model.Curso;
import com.zaia08.hub_estudos.repositories.AvaliacaoRepository;
import com.zaia08.hub_estudos.service.AvaliacaoService;
import com.zaia08.hub_estudos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/avaliacao")

public class AvaliacaoController {
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService){this.avaliacaoService = avaliacaoService; }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> getAvaliacao(){
        List<Avaliacao> avaliacoes = avaliacaoService.getAvaliacao();
        return ResponseEntity.status(HttpStatus.OK).body(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<String> addAvaliacao(@RequestBody CreateAvaliacaoDTO createAvaliacaoDTO){

        String avaliacaoSalva = avaliacaoService.createAvaliacao(createAvaliacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSalva);
        //aqui retornamos a resposta da AI em JSON.

    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> alterAvaliacao(
            @PathVariable int id,
            @RequestBody AlterAvaliacaoDTO dto
    ){

        Avaliacao avaliacao = avaliacaoService.updateAvaliacao(dto, id);
        return ResponseEntity.ok(avaliacao);

    }

}
