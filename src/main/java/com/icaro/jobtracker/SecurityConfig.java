package com.icaro.jobtracker;

// imports: Alt + Enter nos vermelhos

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration: avisa ao Spring que esta classe ensina a montar peças (beans).
@Configuration
public class SecurityConfig {

    // @Bean: "Spring, o que este método devolver, guarde e entregue a quem pedir".
    // BCrypt é o algoritmo que embaralha a senha (hash). Ele é lento de propósito,
    // para dificultar ataques de tentativa e erro.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();     // qual classe? começa com BCrypt...
    }

    // Regras de segurança: quem pode acessar o quê.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF desligado: numa API sem formulário nem cookie, a proteção só atrapalha.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // o cadastro precisa ficar aberto, senão ninguém consegue criar conta
                        .requestMatchers("/auth/register","/error").permitAll()
                        // todo o resto exige login
                        .anyRequest().authenticated())
                // login com usuário e senha em cada requisição
                .httpBasic(Customizer.withDefaults());
        return http.build();    // fecha a montagem
    }
}