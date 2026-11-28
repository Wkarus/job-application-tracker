package com.icaro.jobtracker;

// imports: Alt + Enter nos vermelhos

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// @RestController: diz ao Spring que esta classe responde requisições HTTP e
// devolve dados (JSON), em vez de páginas HTML.
@RestController
// @RequestMapping: prefixo de endereço para todos os métodos desta classe.
// Fica separado do /candidaturas porque cuida de outro assunto (usuários).
@RequestMapping("/auth")
public class AuthController {

    // Campos "final": são definidos uma vez no construtor e não mudam depois.
    // O repositório acessa a tabela usuarios.
    private final UsuarioRepository repository;
    // O PasswordEncoder embaralha a senha (hash). Usamos para NUNCA guardar a
    // senha pura: se o banco vazar, as senhas continuam protegidas.
    private final PasswordEncoder encoder;

    // Construtor: o Spring entrega os dois campos prontos (injeção de dependência).
    // Por isso não precisamos fazer "new" nem configurar nada à mão.
    public AuthController(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // record: classe curta só para carregar os dados que chegam na requisição.
    // Não usamos a entidade Usuario aqui porque a senha que chega é a senha PURA,
    // e na entidade o campo "senha" guarda o HASH. Misturar os dois seria confuso.
    // @NotBlank: o campo não pode ser vazio. @Email: precisa ter formato de e-mail.
    // @Size(min = 8): exige senha com pelo menos 8 caracteres.
    public record RegisterRequest(
            @NotBlank @Email String email,
            @NotBlank @Size(min = 8) String senha) {}

    // @PostMapping: este método atende POST. Usamos POST porque estamos CRIANDO algo.
    @PostMapping("/register")
    // Responde com o status que significa "criado" (em vez do 200 padrão).
    @ResponseStatus(HttpStatus.CREATED)
    // @Valid: executa as validações do record antes de entrar no método.
    // @RequestBody: transforma o JSON recebido no objeto RegisterRequest.
    // void: não devolvemos nada, o status já avisa que deu certo. Devolver o
    // usuário seria arriscado, porque mostraria o hash da senha.
    public void register(@Valid @RequestBody RegisterRequest req) {
        if (repository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }


        Usuario usuario = new Usuario();
        usuario.setEmail(req.email());
        usuario.setSenha(encoder.encode(req.senha()));

        repository.save(usuario);
    }
        // 1. Se o e-mail já existir, lance erro 409 (CONFLICT).
        //    Por que: o e-mail é o login, não pode haver dois iguais. Checamos
        //    antes de salvar para dar uma mensagem clara, em vez de deixar o
        //    banco estourar um erro feio (por causa do unique = true).

        // 2. Crie um Usuario e preencha e-mail e senha.
        //    A senha passa por encoder.encode(...) para virar hash antes de ir
        //    para o objeto. É aqui que a senha pura deixa de existir.

        // 3. Salve com repository.save(...), que grava na tabela usuarios.
    }

