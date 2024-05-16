package com.uma.example.springuma.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.containsString;


public class MedicoControllerMockMvcIT extends AbstractIntegration {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Medico medico1;

    /* 
    @BeforeEach
    void init(){
        medico1 = new Medico();
        medico1.setId(1);
        medico1.setDni("345");
        medico1.setNombre("Manuel");
        medico1.setEspecialidad("Traumatologia");
    }
*/
    @Test
    void createAndGetMedico_shouldGetMedico() throws Exception {
        Medico medico = new Medico();
        medico.setId(1);
        medico.setDni("345");
        medico.setNombre("Manuel");
        medico.setEspecialidad("Traumatologia");

        this.mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(get("/medico/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(medico.getId()));
    }

    @Test
    void updateMedico_shouldUpdateMedico() throws Exception {
        medico1.setNombre("Roberto");

        this.mockMvc.perform(put("/medico")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico1)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/medico/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre").value("Roberto"));
    }

    @Test
    void deleteMedico_shouldDeleteMedico() throws Exception {
        mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico1)))
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(delete("/medico/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/medico/1"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}
