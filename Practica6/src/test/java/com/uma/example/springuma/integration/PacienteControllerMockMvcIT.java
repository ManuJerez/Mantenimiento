package com.uma.example.springuma.integration;

import com.uma.example.springuma.integration.base.AbstractIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.MedicoService;
import com.uma.example.springuma.model.Paciente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*; 
import static org.hamcrest.Matchers.hasSize;


public class PacienteControllerMockMvcIT extends AbstractIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicoService medicoService;

    
    Paciente paciente;
    Medico medico;
    Medico medico2;

    @BeforeEach
    void setUp(){
        medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Medico1");
        medico.setDni("1");
        medico.setEspecialidad("Urologo");

        medico2 = new Medico();
        medico2.setId(2L);
        medico2.setNombre("Medico2");
        medico2.setDni("2");
        medico2.setEspecialidad("Radiologo");

        this.medicoService.addMedico(medico);
        this.medicoService.addMedico(medico2);
    }

    @Test
    @DisplayName("Save a patient and get with index it is correct from PacienteController")
    void savePacienteAndGetWithIndex() throws Exception {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);
        
       // crea un paciente
       this.mockMvc.perform(post("/paciente")
       .contentType("application/json")
       .content(objectMapper.writeValueAsString(paciente)))
       .andExpect(status().isCreated())
       .andExpect(status().is2xxSuccessful());

       // obtiene el paciente
       this.mockMvc.perform(get("/paciente/1"))
       .andDo(print())
       .andExpect(status().is2xxSuccessful())
       .andExpect(content().contentType("application/json"))
       .andExpect(jsonPath("$").exists())
       .andExpect(jsonPath("$").value(paciente)); 
    }

    @Test
    @DisplayName("Save a patience and get with the patience list of the doctor it is correct from PacienteController")
    void savePacienteAndGetWithThePatienceListOfTheDoctor() throws Exception {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);
        
       // crea un paciente
       this.mockMvc.perform(post("/paciente")
       .contentType("application/json")
       .content(objectMapper.writeValueAsString(paciente)))
       .andExpect(status().isCreated())
       .andExpect(status().is2xxSuccessful());

       // obtiene el listado de pacientes del medico
       this.mockMvc.perform(get("/paciente/medico/1"))
       .andDo(print())
       .andExpect(status().is2xxSuccessful())
       .andExpect(content().contentType("application/json"))
       .andExpect(jsonPath("$", hasSize(1)))
       .andExpect(jsonPath("$[0]").value(paciente)); 
    }

    @Test
    @DisplayName("Save a patience and update it is correct from PacienteController")
    void savePatienceAndUpdate() throws JsonProcessingException, Exception
    {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);

        // crea un paciente
       this.mockMvc.perform(post("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());


        paciente.setEdad(22);
        paciente.setCita("Radiologo");

        // Actualiza el paciente
        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().is2xxSuccessful());

        // obtiene el paciente
       this.mockMvc.perform(get("/paciente/1"))
           .andDo(print())
           .andExpect(status().is2xxSuccessful())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$").value(paciente));
    }

    @Test
    @DisplayName("Save a patience and update including doctor it is correct from PacienteController")
    void savePatienceAndUpdateIncludingDoctor() throws JsonProcessingException, Exception
    {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);

        // crea un paciente
       this.mockMvc.perform(post("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());


        paciente.setEdad(22);
        paciente.setCita("Radiologo");
        paciente.setMedico(this.medico2);

        // Actualiza el paciente
        this.mockMvc.perform(put("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().is2xxSuccessful());

        // obtiene el paciente
       this.mockMvc.perform(get("/paciente/medico/2"))
           .andDo(print())
           .andExpect(status().is2xxSuccessful())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0]").value(paciente));
    }

    @Test
    @DisplayName("Save a patience and delete it is correct from PacienteController")
    void savePatienceAndDelete() throws JsonProcessingException, Exception
    {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);

        // crea un paciente
       this.mockMvc.perform(post("/paciente")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(paciente)))
            .andExpect(status().isCreated())
            .andExpect(status().is2xxSuccessful());

        // elimina el paciente
        this.mockMvc.perform(delete("/paciente/1"))
            .andExpect(status().is2xxSuccessful());

        // verificamos que no existe el paciente
        this.mockMvc.perform(get("/paciente/medico/1"))
           .andDo(print())
           .andExpect(status().is2xxSuccessful())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$", hasSize(0)));
    }
}
