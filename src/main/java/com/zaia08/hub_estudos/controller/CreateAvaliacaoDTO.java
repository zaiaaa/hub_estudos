package com.zaia08.hub_estudos.controller;

import java.util.List;

public record CreateAvaliacaoDTO(Long fk_id_curso, Short horas_assistidas, String resumo, String curso) {
}
