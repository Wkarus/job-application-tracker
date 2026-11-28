package com.icaro.jobtracker;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

// @Entity diz ao JPA: "esta classe representa uma tabela do banco de dados".
// Cada objeto Candidatura vira uma linha da tabela, e cada atributo vira uma coluna.
@Entity

public class Candidatura {


    // @Id marca este campo como a chave primária (o identificador único da linha).
    // @GeneratedValue faz o banco criar o número sozinho (1, 2, 3...), então você não precisa informar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Atributos simples: viram colunas de texto na tabela.
    @NotBlank(message = "A empresa é obrigatória")
    private String empresa;

    @NotBlank(message = "A Vaga é obrigatória")
    private String vaga;

    private String link;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
// @Enumerated(EnumType.STRING) guarda o status como texto ("ENVIADA") no banco,
    // em vez de um número. Assim o dado continua legível, mesmo se a ordem do enum mudar.

    // Todo novo cadastro começa como ENVIADA.
    @Enumerated(EnumType.STRING)
    private Status status = Status.ENVIADA;

    // LocalDate guarda só a data (sem hora). Por padrão, é a data de hoje.
    private LocalDate dataEnvio = LocalDate.now();

    // Getters e setters: métodos para ler (get) e alterar (set) os atributos.
    // O Spring e o JPA usam esses métodos para preencher o objeto com os dados que chegam
    // na requisição e para transformar o objeto em JSON na resposta.
    // O id não tem setter porque quem define é o banco.
    public Long getId() { return id; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getVaga() { return vaga; }
    public void setVaga(String vaga) { this.vaga = vaga; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate dataEnvio) { this.dataEnvio = dataEnvio; }
}