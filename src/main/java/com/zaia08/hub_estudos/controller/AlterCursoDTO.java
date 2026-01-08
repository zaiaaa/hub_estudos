package com.zaia08.hub_estudos.controller;

import java.time.LocalDate;

public record AlterCursoDTO(String nome_curso, Integer horas_desejadas, Integer horas_atuais, LocalDate meta_de_conclusao, Boolean resumo_semanal) {}
