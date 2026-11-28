package com.icaro.jobtracker;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// O repository é a camada que conversa com o banco de dados.
// Ao estender JpaRepository<Candidatura, Long>, você ganha de graça os métodos
// save (salvar), findAll (listar tudo), findById (buscar por id) e deleteById (apagar).
// Candidatura = o tipo da entidade, Long = o tipo do id.
// O Spring cria a implementação sozinho em tempo de execução: você não escreve SQL aqui.
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

    // O nome do método vira a consulta: "busque (find) por (By) status".
    // O Spring gera o SQL sozinho: select ... where status = ?
    List<Candidatura> findByStatusAndUsuarioEmail(Status status,String email);

    List<Candidatura> findByUsuarioEmail(String email);

    Optional<Candidatura> findByIdAndUsuarioEmail(Long id,String email);

}