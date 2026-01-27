package com.zaia08.hub_estudos.service;

import com.zaia08.hub_estudos.Model.Avaliacao;
import com.zaia08.hub_estudos.controller.AlterAvaliacaoDTO;
import com.zaia08.hub_estudos.controller.CreateAvaliacaoDTO;
import com.zaia08.hub_estudos.repositories.AvaliacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestTemplate restTemplate;
    private final String WEBHOOK_URL = "https://zaia.app.n8n.cloud/webhook-test/c79e0634-96c4-4c97-89d4-59f66719f538";
    //URL DE TESTE.
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestTemplate restTemplate){
        this.avaliacaoRepository = avaliacaoRepository;
        this.restTemplate = restTemplate;
    }
    @Transactional //Garante que tudo ou nada seja salvo
    public Avaliacao createAvaliacao(CreateAvaliacaoDTO createAvaliacaoDTO){
        //DTO -> Entity; Gravar entity.
        var entityBD = new Avaliacao();
        entityBD.setAssunto(createAvaliacaoDTO.assunto());
        entityBD.setFkIdCurso(createAvaliacaoDTO.fk_id_curso());
        entityBD.setResumo(createAvaliacaoDTO.resumo());
        entityBD.setTempoAssistido(createAvaliacaoDTO.tempo_assistido());

        var savedEntity =  avaliacaoRepository.saveAndFlush(entityBD);

        var entityAI = new Avaliacao();
        entityAI.setId(savedEntity.getId());
        entityAI.setAssunto(savedEntity.getAssunto());
        entityAI.setResumo(savedEntity.getResumo());
        entityAI.setTempoAssistido(savedEntity.getTempoAssistido());
        entityAI.setCurso(savedEntity.getCurso());
        entityAI.setConteudos_estudados(savedEntity.getConteudos_estudados());

        correcao(entityAI);

        return savedEntity;
    }

    public Avaliacao updateAvaliacao(AlterAvaliacaoDTO alterAvaliacaoDTO, int id){
        //DTO -> Entity; Gravar entity.
        Avaliacao entityByAI = avaliacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));
        entityByAI.setNota(alterAvaliacaoDTO.nota());
        entityByAI.setPontosFortes(alterAvaliacaoDTO.pontos_fortes());
        entityByAI.setPontosMelhorar(alterAvaliacaoDTO.pontos_a_melhorar());
        entityByAI.setResumoCorrigido(alterAvaliacaoDTO.resumo_corrigido());
        entityByAI.setSugestaoProximoEstudo(alterAvaliacaoDTO.sugestao_proximo_estudo());

        return avaliacaoRepository.save(entityByAI);
    }


    private void correcao(Avaliacao avaliacao){
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(WEBHOOK_URL, avaliacao, String.class);
            System.out.println("Resposta do n8n: " + response.getBody());
        }catch (Exception e){
            System.err.println("O n8n falhou ou demorou demais: " + e.getMessage());
            throw new RuntimeException("Falha na integração com n8n. Avaliação não será salva.");
        }
    }

}
