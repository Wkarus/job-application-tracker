package com.icaro.jobtracker;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
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
    private final UsuarioRepository usuarioRepository;

    public CandidaturaController(CandidaturaRepository repository,
                                 UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    // POST /candidaturas -> cria.
    // @Valid executa as validações (@NotBlank) antes de entrar no método.
    // @ResponseStatus(CREATED) responde 201 em vez de 200, que é o correto para "criado".
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidatura criar(@Valid @RequestBody Candidatura candidatura,
                             Authentication authentication) {
        Usuario  usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,"Usuario nao encontrado"));
        candidatura.setId(null);
        candidatura.setUsuario(usuario);
        return repository.save(candidatura);
    }

    // GET /candidaturas ou GET /candidaturas?status=ENTREVISTA
    // @RequestParam pega o parâmetro da URL. required = false: ele é opcional.
    @GetMapping
    public List<Candidatura> listar(@RequestParam(required = false) Status status, Authentication authentication) {
        if (status != null) {
            return repository.findByStatusAndUsuarioEmail(status,authentication.getName());
        }
        return repository.findByUsuarioEmail(authentication.getName());
    }

    // GET /candidaturas/1 -> busca uma candidatura pelo id.
    // @PathVariable pega o {id} que está no caminho da URL.
    // Se não existir, responde 404 (não encontrado).
    @GetMapping("/{id}")
    public Candidatura buscar(@PathVariable Long id,Authentication authentication) {
        return repository.findByIdAndUsuarioEmail(id, authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Candidatura não encontrada"));
    }

    // PUT /candidaturas/1 -> atualiza uma candidatura existente.
    // Primeiro busca a atual (404 se não existir), depois troca os campos e salva.
    @PutMapping("/{id}")
    public Candidatura atualizar(@PathVariable Long id,Authentication authentication,
                                 @Valid @RequestBody Candidatura dados) {
        Candidatura candidatura = buscar(id,authentication);
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
    public void apagar(@PathVariable Long id,Authentication authentication) {
        Candidatura candidatura = buscar(id, authentication);

        repository.delete(candidatura);   }
}