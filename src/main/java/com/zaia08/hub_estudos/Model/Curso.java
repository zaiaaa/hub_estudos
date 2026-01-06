package com.zaia08.hub_estudos.Model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_curso", nullable = false)
    private String nomeCurso;

    @Column(name = "horas_desejadas")
    private Integer horasDesejadas;

    @Column(name = "horas_atuais")
    private Integer horasAtuais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getHorasDesejadas() {
        return horasDesejadas;
    }

    public void setHorasDesejadas(Integer horasDesejadas) {
        this.horasDesejadas = horasDesejadas;
    }

    public Integer getHorasAtuais() {
        return horasAtuais;
    }

    public void setHorasAtuais(Integer horasAtuais) {
        this.horasAtuais = horasAtuais;
    }

    public LocalDate getMetaDeConclusao() {
        return metaDeConclusao;
    }

    public void setMetaDeConclusao(LocalDate metaDeConclusao) {
        this.metaDeConclusao = metaDeConclusao;
    }

    public Boolean getResumoSemanal() {
        return resumoSemanal;
    }

    public void setResumoSemanal(Boolean resumoSemanal) {
        this.resumoSemanal = resumoSemanal;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Column(name = "meta_de_conclusao")
    private LocalDate metaDeConclusao;

    @Column(name = "resumo_semanal")
    private Boolean resumoSemanal;

    @Column(name = "usuario_id")
    private Long usuarioId;
}

