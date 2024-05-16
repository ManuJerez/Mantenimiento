package com.uma.example.springuma.integration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.BodyInserters;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {

    @LocalServerPort
    private Integer port;
    
    private WebTestClient testClient;

    private Paciente paciente;

    private Medico medico;

    @PostConstruct
    public void init()
    {
        testClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
        .responseTimeout(Duration.ofMillis(300000)).build();
    }

    @BeforeEach
    public void setup() {
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

        // Crea un medico
        testClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated();

        // Crea un paciente
        testClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Upload an image with a patience and a doctor and download from image controller")
    void uploadAndDownloadImageTest() {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtenemos la imagen descargandola
        testClient.get().uri("/imagen/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class);
    } 

    
    @Test
    @DisplayName("Create a healthy image, patience and doctor and get a prediction with no cancer from image controller")
    void createHealthyImgAndPredict()
    {
        // Crea una imagen (imagen1)
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtenemos la predicción

        testClient.get().uri("/imagen/predict/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .consumeWith(response -> {
                String resultadoObtenido = response.getResponseBody();
                assertTrue(resultadoObtenido.contains("Not cancer (label 0)"));
            });
    }


    @Test
    @DisplayName("Create a not healthy image, patience and doctor and get a prediction with cancer from image controller")
    void createNoHealthyImgAndPredict()
    {
        // Crea una imagen (imagen1)
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/no_healthty.png").toFile()));
            builder.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtenemos la predicción

        testClient.get().uri("/imagen/predict/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .consumeWith(response -> {
                String resultadoObtenido = response.getResponseBody();
                assertTrue(resultadoObtenido.contains("Cancer (label 1)"));
            });
    }


    @Test
    @DisplayName("Upload an image and get it with patience index from image controller")
    void uploadAndGetItWithPatienceId() {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtenemos la imagen con el indice del paciente
        testClient.get().uri("/imagen/paciente/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class);
    }

    @Test
    @DisplayName("Create and upload 2 images and assign them to a patient and get it from patience index form image controller")
    void upload2ImagesAndGetItFromPatienceIndex()
    {
        // Crea una imagen (imagen1)
        try {
            MultipartBodyBuilder builder1 = new MultipartBodyBuilder();
            builder1.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder1.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder1.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crea la otra imagen (imagen2)
        try {
            MultipartBodyBuilder builder2 = new MultipartBodyBuilder();
            builder2.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder2.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder2.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FluxExchangeResult<Imagen> result = testClient.get().uri("/imagen/paciente/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class);

        assertTrue(result.getResponseBody().collectList().block().size() == 2);
    }

    
    @Test   
    @DisplayName("Delete an image and get zero images from patience id from image controller")
    void deleteImageAndGetZeroImagesFromPatienceId() {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("image", new FileSystemResource(Paths.get("src/test/resources/healthy.png").toFile()));
            builder.part("paciente", paciente);

            testClient.post().uri("/imagen")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Borramos la imagen
        testClient.delete().uri("/imagen/1")
            .exchange()
            .expectStatus().isNoContent();

        // Obtenemos las imagenes y vemos que hay cero 
        
        FluxExchangeResult<Imagen> result = testClient.get().uri("/imagen/paciente/1")
            .exchange()
            .expectStatus().isOk()
            .returnResult(Imagen.class);

        assertTrue(result.getResponseBody().collectList().block().size() == 0);
    }
}
            
            



