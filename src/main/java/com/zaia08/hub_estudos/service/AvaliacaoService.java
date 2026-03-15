package com.zaia08.hub_estudos.service;

import com.zaia08.hub_estudos.Model.Avaliacao;
import com.zaia08.hub_estudos.controller.AlterAvaliacaoDTO;
import com.zaia08.hub_estudos.controller.CreateAvaliacaoDTO;
import com.zaia08.hub_estudos.repositories.AvaliacaoRepository;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final CursoRepository cursoRepository;

    private final RestTemplate restTemplate;
    private final String WEBHOOK_URL = "http://192.168.0.113:5678/webhook/8c02e7ad-0533-40ff-9a62-d63969b2f6a6";
    //URL DE PRODUÇÃO.
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestTemplate restTemplate, CursoRepository cursoRepository){
        this.avaliacaoRepository = avaliacaoRepository;
        this.restTemplate = restTemplate;
        this.cursoRepository = cursoRepository;
    }
    @Transactional //Garante que tudo ou nada seja salvo
    public String createAvaliacao(CreateAvaliacaoDTO createAvaliacaoDTO){
        //DTO -> Entity; Gravar entity.
        var entityBD = new Avaliacao();

        Long idLong = createAvaliacaoDTO.fk_id_curso();
        int idInt = idLong.intValue(); // Forma mais elegante
        // Busca o objeto Curso
        var cursoObjeto = cursoRepository.findById(idInt)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

// Pega o NOME do curso (String) e passa para o set
        entityBD.setCurso(cursoObjeto.getNomeCurso());
        entityBD.setFkIdCurso(idLong);
        entityBD.setResumo(createAvaliacaoDTO.resumo());
        entityBD.setHorasAssistidas(createAvaliacaoDTO.horas_assistidas());

        var savedEntity =  avaliacaoRepository.saveAndFlush(entityBD);

        var correcao = correcao(entityBD);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(correcao);

            // 1. Entra no objeto pai chamado "correcao"
            JsonNode objetoCorrecao = root.path("correcao");

            if (!objetoCorrecao.isMissingNode()) {
                // 2. Pega a nota (dentro de correcao)
                savedEntity.setNota(objetoCorrecao.path("nota").asInt());

                // 3. Pega o texto da correção (o campo interno também se chama "correcao")
                savedEntity.setResumoCorrigido(objetoCorrecao.path("correcao").asText());

                // 4. Pega a sugestão
                savedEntity.setSugestaoProximoEstudo(objetoCorrecao.path("sugestao").asText());

                avaliacaoRepository.save(savedEntity);
                System.out.println("Agora foi! Banco atualizado com os dados da IA.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar resposta da IA: " + e.getMessage());
        }

        // Retorna o JSON para o seu Controller mostrar na tela
        return correcao;

    }

    public Avaliacao updateAvaliacao(AlterAvaliacaoDTO alterAvaliacaoDTO, int id){
        //DTO -> Entity; Gravar entity.
        Avaliacao entityByAI = avaliacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));
        entityByAI.setNota(alterAvaliacaoDTO.nota());
        entityByAI.setResumoCorrigido(alterAvaliacaoDTO.resumo_corrigido());
        entityByAI.setSugestaoProximoEstudo(alterAvaliacaoDTO.sugestao_proximo_estudo());

        return avaliacaoRepository.save(entityByAI);
    }

    public List<Avaliacao> getAvaliacao(){return avaliacaoRepository.findAll(); }

    public boolean deleteAvaliacao(int id){
        if(!avaliacaoRepository.existsById(id)){
            return false;
        }

        avaliacaoRepository.deleteByFkIdCurso(id);
        return true;
    }

    private String correcao(Avaliacao avaliacao){
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(WEBHOOK_URL, avaliacao, String.class);
            return response.getBody();
        }catch (Exception e){
            System.err.println("O n8n falhou ou demorou demais: " + e.getMessage());
            throw new RuntimeException("Falha na integração com n8n. Avaliação não será salva.");
        }
    }


}
