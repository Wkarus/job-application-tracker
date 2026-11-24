package com.icaro.jobtracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ExecutionException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest   // sobe o contexto do Spring para o teste (igual à aplicação, mas sem porta de rede).
@AutoConfigureMockMvc //cria o MockMvc para você usar.
public class CandidaturaControllerTest {
    @Autowired
    private MockMvc mockMvc;


    //O throws Exception existe porque o perform pode lançar erro,
    // e o teste só precisa deixar o erro subir (o JUnit marca o teste como falho).


    @Test
    public void listarCandidaturas() throws Exception{
        mockMvc.perform(get("/candidaturas")).andExpect(status().isOk());
    }



    @Test
    public void testeErro() throws Exception{
        mockMvc.perform(get("/candidaturas/9999")).andExpect(status().isNotFound());
    }



    @Test
        public void deveCriarCandidaturaValida() throws Exception{
            mockMvc.perform(post("/candidaturas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {"empresa": "Empresa A",
                            "vaga": "Dev Junior"}
                            """))
                    .andExpect(status().isCreated());

        }

    @Test
    public void naoDeveCriarCandidaturaSemEmpresa() throws Exception{

        mockMvc.perform(post("/candidaturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"empresa": "",
                            "vaga": "Dev Junior"}
                            """))
                .andExpect(status().isBadRequest());

    }



}
