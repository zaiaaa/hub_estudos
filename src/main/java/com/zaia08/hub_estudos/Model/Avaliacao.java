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

    @Column(name = "horas_assistidas")
    private Short horasAssistidas; // varchar

    @Column(name = "resumo", columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "fk_id_curso")
    private Long fkIdCurso;

    @Column(name = "resumo_corrigido", columnDefinition = "TEXT")
    private String resumoCorrigido;

    @Column(name = "sugestao_proximo_estudo", columnDefinition = "TEXT")
    private String sugestaoProximoEstudo;

    @Transient
    private String curso;

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

    public Short getHorasAssistidas() {
        return horasAssistidas;
    }

    public void setHorasAssistidas(Short horasAssistidas) {
        this.horasAssistidas = horasAssistidas;
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
}
