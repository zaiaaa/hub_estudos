package com.zaia08.hub_estudos.controller;

import java.util.List;

public record CreateAvaliacaoDTO(Long fk_id_curso, String assunto, Short tempo_assistido, String resumo, String curso, List<String> conteudos_estudados) {
}
