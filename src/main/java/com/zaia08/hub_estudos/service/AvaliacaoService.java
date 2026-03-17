package com.zaia08.hub_estudos.service;

import org.springframework.beans.factory.annotation.Value;
import com.zaia08.hub_estudos.Model.Avaliacao;
import com.zaia08.hub_estudos.controller.AlterAvaliacaoDTO;
import com.zaia08.hub_estudos.controller.CreateAvaliacaoDTO;
import com.zaia08.hub_estudos.repositories.AvaliacaoRepository;
import com.zaia08.hub_estudos.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final CursoRepository cursoRepository;
    @Value("${WEBHOOK_URL}")
    private String webhookUrl;

    private final RestTemplate restTemplate;
    //URL DE PRODUÇÃO.
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestTemplate restTemplate, CursoRepository cursoRepository){
        this.avaliacaoRepository = avaliacaoRepository;
        this.restTemplate = restTemplate;
        this.cursoRepository = cursoRepository;
    }
    @Transactional //Garante que tudo ou nada seja salvo
    public Avaliacao createAvaliacao(CreateAvaliacaoDTO createAvaliacaoDTO){
        //DTO -> Entity; Gravar entity.
        var entityBD = new Avaliacao();

        Long idLong = createAvaliacaoDTO.fk_id_curso();
        // Busca o objeto Curso
        var cursoObjeto = cursoRepository.findById(idLong)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Pega o NOME do curso (String) e passa para o set
        entityBD.setCurso(cursoObjeto.getNomeCurso());
        entityBD.setFkIdCurso(idLong);
        entityBD.setResumo(createAvaliacaoDTO.resumo());
        entityBD.setHorasAssistidas(createAvaliacaoDTO.horas_assistidas());

        return avaliacaoRepository.saveAndFlush(entityBD);
    }

    @Async
    public void enviarParaN8N(Avaliacao avaliacao) {
        try {
            // Criamos um objeto simples para enviar ao n8n, garantindo que o nome do curso vá junto
            // Já que na entidade ele é @Transient
            restTemplate.postForEntity(webhookUrl, avaliacao, String.class);
            System.out.println("Enviado para o n8n com sucesso (assíncrono) para ID: " + avaliacao.getId());
        } catch (Exception e) {
            System.err.println("Erro ao enviar para o n8n: " + e.getMessage());
        }
    }

    @Transactional
    public void processarCallback(Long id, String jsonResposta) {
        try {
            Avaliacao avaliacao = avaliacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para callback id: " + id));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResposta);

            // 1. Entra no objeto pai chamado "correcao"
            JsonNode objetoCorrecao = root.path("correcao");

            if (!objetoCorrecao.isMissingNode()) {
                // 2. Pega a nota (dentro de correcao)
                avaliacao.setNota(objetoCorrecao.path("nota").asInt());

                // 3. Pega o texto da correção (o campo interno também se chama "correcao")
                avaliacao.setResumoCorrigido(objetoCorrecao.path("correcao").asText());

                // 4. Pega a sugestão
                avaliacao.setSugestaoProximoEstudo(objetoCorrecao.path("sugestao").asText());

                avaliacaoRepository.save(avaliacao);
                System.out.println("Banco atualizado via callback com os dados da IA para id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar callback da IA: " + e.getMessage());
        }
    }

    public Avaliacao updateAvaliacao(AlterAvaliacaoDTO alterAvaliacaoDTO, Long id){
        //DTO -> Entity; Gravar entity.
        Avaliacao entityByAI = avaliacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));
        entityByAI.setNota(alterAvaliacaoDTO.nota());
        entityByAI.setResumoCorrigido(alterAvaliacaoDTO.resumo_corrigido());
        entityByAI.setSugestaoProximoEstudo(alterAvaliacaoDTO.sugestao_proximo_estudo());

        return avaliacaoRepository.save(entityByAI);
    }

    public List<Avaliacao> getAvaliacao(){return avaliacaoRepository.findAll(); }

    public boolean deleteAvaliacao(Long id){
        if(!avaliacaoRepository.existsById(id)){
            return false;
        }

        avaliacaoRepository.deleteByFkIdCurso(id);
        return true;
    }


}
