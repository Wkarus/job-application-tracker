package com.icaro.jobtracker;

import org.springframework.data.jpa.repository.JpaRepository;

// O repository é a camada que conversa com o banco de dados.
// Ao estender JpaRepository<Candidatura, Long>, você ganha de graça os métodos
// save (salvar), findAll (listar tudo), findById (buscar por id) e deleteById (apagar).
// Candidatura = o tipo da entidade, Long = o tipo do id.
// O Spring cria a implementação sozinho em tempo de execução: você não escreve SQL aqui.
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
}