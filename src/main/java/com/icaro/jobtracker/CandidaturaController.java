package com.icaro.jobtracker;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/candidaturas")
public class CandidaturaController {

    private final CandidaturaRepository repository;

    public CandidaturaController(CandidaturaRepository repository) {
        this.repository = repository;
    }

    // POST /candidaturas -> cria.
    // @Valid executa as validações (@NotBlank) antes de entrar no método.
    // @ResponseStatus(CREATED) responde 201 em vez de 200, que é o correto para "criado".
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidatura criar(@Valid @RequestBody Candidatura candidatura) {
        return repository.save(candidatura);
    }

    // GET /candidaturas ou GET /candidaturas?status=ENTREVISTA
    // @RequestParam pega o parâmetro da URL. required = false: ele é opcional.
    @GetMapping
    public List<Candidatura> listar(@RequestParam(required = false) Status status) {
        if (status != null) {
            return repository.findByStatus(status);
        }
        return repository.findAll();
    }

    // GET /candidaturas/1 -> busca uma candidatura pelo id.
    // @PathVariable pega o {id} que está no caminho da URL.
    // Se não existir, responde 404 (não encontrado).
    @GetMapping("/{id}")
    public Candidatura buscar(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Candidatura não encontrada"));
    }

    // PUT /candidaturas/1 -> atualiza uma candidatura existente.
    // Primeiro busca a atual (404 se não existir), depois troca os campos e salva.
    @PutMapping("/{id}")
    public Candidatura atualizar(@PathVariable Long id,
                                 @Valid @RequestBody Candidatura dados) {
        Candidatura candidatura = buscar(id);
        candidatura.setEmpresa(dados.getEmpresa());
        candidatura.setVaga(dados.getVaga());
        candidatura.setLink(dados.getLink());
        candidatura.setStatus(dados.getStatus());
        candidatura.setDataEnvio(dados.getDataEnvio());
        return repository.save(candidatura);
    }

    // DELETE /candidaturas/1 -> apaga. Responde 204 (sem conteúdo).
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Candidatura não encontrada");
        }
        repository.deleteById(id);
    }
}