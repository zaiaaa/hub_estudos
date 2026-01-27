package com.zaia08.hub_estudos.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; // Corresponde ao int8 (BigInt)

    @Column(name = "nota")
    private Integer nota; // Corresponde ao int4 (Integer)

    @Column(name = "assunto")
    private String assunto; // Corresponde ao varchar

    @Column(name = "tempo_assistido")
    private Short tempoAssistido; // varchar

    @Column(name = "resumo", columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "fk_id_curso")
    private Long fkIdCurso;

    @Column(name = "pontos_fortes", columnDefinition = "TEXT")
    private String pontosFortes;

    @Column(name = "pontos_a_melhorar", columnDefinition = "TEXT")
    private String pontosMelhorar;

    @Column(name = "resumo_corrigido", columnDefinition = "TEXT")
    private String resumoCorrigido;

    @Column(name = "sugestao_proximo_estudo", columnDefinition = "TEXT")
    private String sugestaoProximoEstudo;

    @Transient
    private String curso;

    @Transient
    private List<String> conteudos_estudados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Short getTempoAssistido() {
        return tempoAssistido;
    }

    public void setTempoAssistido(Short tempoAssistido) {
        this.tempoAssistido = tempoAssistido;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Long getFkIdCurso() {
        return fkIdCurso;
    }

    public void setFkIdCurso(Long fkIdCurso) {
        this.fkIdCurso = fkIdCurso;
    }

    public String getPontosFortes() {
        return pontosFortes;
    }

    public void setPontosFortes(String pontosFortes) {
        this.pontosFortes = pontosFortes;
    }

    public String getPontosMelhorar() {
        return pontosMelhorar;
    }

    public void setPontosMelhorar(String pontosMelhorar) {
        this.pontosMelhorar = pontosMelhorar;
    }

    public String getResumoCorrigido() {
        return resumoCorrigido;
    }

    public void setResumoCorrigido(String resumoCorrigido) {
        this.resumoCorrigido = resumoCorrigido;
    }

    public String getSugestaoProximoEstudo() {
        return sugestaoProximoEstudo;
    }

    public void setSugestaoProximoEstudo(String sugestaoProximoEstudo) {
        this.sugestaoProximoEstudo = sugestaoProximoEstudo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<String> getConteudos_estudados() {
        return conteudos_estudados;
    }

    public void setConteudos_estudados(List<String> conteudos_estudados) {
        this.conteudos_estudados = conteudos_estudados;
    }
}
