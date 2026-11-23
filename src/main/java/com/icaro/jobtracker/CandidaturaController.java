package com.icaro.jobtracker;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController: esta classe recebe requisições HTTP e devolve respostas em JSON.
// @RequestMapping("/candidaturas"): todas as rotas daqui começam com /candidaturas.
@RestController
@RequestMapping("/candidaturas")
public class CandidaturaController {

    // O controller precisa do repository para falar com o banco.
    private final CandidaturaRepository repository;

    // Injeção de dependência: o Spring cria o repository e entrega pronto neste construtor.
    // Você não usa "new CandidaturaRepository()".
    public CandidaturaController(CandidaturaRepository repository) {
        this.repository = repository;
    }

    // POST /candidaturas -> cria uma candidatura nova.
    // op criar@RequestBody pega o JSON enviado na requisição e transforma num objeto Candidatura.
    // O save(candidatura) grava no banco e devolve a candidatura salva, já com o id gerado.
    @PostMapping
    public Candidatura criar(@RequestBody Candidatura candidatura) {
        return repository.save(candidatura);
    }

    // GET /candidaturas -> devolve todas as candidaturas cadastradas.
    @GetMapping
    public List<Candidatura> listar() {
        return repository.findAll();
    }
}