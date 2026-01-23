package com.zaia08.hub_estudos.controller;

import java.time.LocalDate;

public record CreateCursoDTO(String nome_curso, Integer horas_desejadas, Float horas_atuais, LocalDate meta_de_conclusao, Boolean resumo_semanal, String prioridade) {}
