package com.icaro.jobtracker;

// Um enum é uma lista fechada de valores possíveis.
// Aqui, os estados em que uma candidatura pode estar.
// Usar enum evita erros de digitação (como "envida" em vez de "ENVIADA").
public enum Status {
    ENVIADA,     // você mandou o currículo
    ENTREVISTA,  // a empresa chamou para conversar
    RECUSADA,    // a empresa disse não
    OFERTA       // você recebeu uma proposta
}