package com.icaro.jobtracker;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    // 1º: o campo, onde a classe guarda o repositório
    private final UsuarioRepository repository;

    // 2º: o construtor, é como o Spring entrega o repositório pronto
    public UsuarioDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // 3º: o método do contrato, chamado pelo Spring no login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // busca pelo e-mail; se a caixa (Optional) estiver vazia, lança o erro
        Usuario usuario = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // monta o "crachá" que o Spring entende
        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}