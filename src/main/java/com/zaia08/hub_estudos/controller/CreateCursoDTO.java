package com.zaia08.hub_estudos.controller;

import java.time.LocalDate;

public record CreateCursoDTO(String nome_curso, int horas_desejadas, int horas_atuais, LocalDate meta_de_conclusao, boolean resumo_semanal) {}
