package com.uma.example.springuma.integration;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InformeControllerWebTestClientIT {
    @LocalServerPort
    private Integer port;
    private WebTestClient client;
    private Informe informe;
    private Paciente paciente;
    private Medico medico;
    private Imagen imagen;

    @PostConstruct
    public void init(){
        client = WebTestClient.bindToServer().baseUrl("http://localhost:"+port)
                .responseTimeout(Duration.ofMillis(50000)).build();
    }

    @BeforeEach
    void setup()
    {
        medico = new Medico();
        medico.setId(1);
        medico.setNombre("Medico1");
        medico.setDni("1");
        medico.setEspecialidad("Urologo");

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Paciente1");
        paciente.setDni("1");
        paciente.setEdad(20);
        paciente.setCita("Urologo");
        paciente.setMedico(this.medico);

        imagen = new Imagen();
        imagen.setId(1);
        imagen.setPaciente(paciente);

        // Crea un medico
        client.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated();

        // Crea un paciente
        client.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated();
    }


    @Test
    public void createInforme_ShouldCreateInforme(){

        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder.part("paciente", paciente);

            client.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        informe = new Informe();
        informe.setId(1);
        informe.setPrediccion("No Cancer");
        informe.setContenido("Contenido informe");
        informe.setImagen(imagen);

        client.post().uri("/informe")
                .body(Mono.just(informe), Informe.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();
    }

}















