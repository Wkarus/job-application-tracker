package com.icaro.jobtracker;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.jspecify.annotations.Nullable;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha; // guarda o HASH, nunca a senha pura


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

        public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



}


