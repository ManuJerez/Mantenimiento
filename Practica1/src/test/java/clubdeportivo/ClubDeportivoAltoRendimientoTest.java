/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
 */

package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest {

    private ClubDeportivoAltoRendimiento clubAR;

    @BeforeEach
    void inicializarClubSinTam() throws ClubException {
        clubAR = new ClubDeportivoAltoRendimiento("ClubAR", 10, 25.0);
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento con maximo negativo")
    void crearClubMaxNeg() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", -5, 25);
        });
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento en el incremento negativo")
    void crearClubIncNeg() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", 10, -5);
        });
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento en el maximo negativo con tam")
    void crearClubNegativoConTamMaxNeg() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", 8, -5, 3);
        });
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento en el incremento negativo con tam")
    void crearClubNegativoConTamIncNeg() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", 8, 8, -5);
        });
    }

    /*
    @Test
    @DisplayName("Crear el club de alto rendimiento con tam")
    void crearClubTam() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("ClubAR",10, 10, 25.0);

        assertTrue(club instanceof ClubDeportivoAltoRendimiento);
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento")
    void crearClub() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("ClubAR", 10, 25.0);

        assertTrue(club instanceof ClubDeportivoAltoRendimiento);
    }*/




    @Test
    @DisplayName("Añadir actividades con un array de datos menor que 5")
    void numDatosInsuficiente() throws ClubException {
        String[] datos = {"1","2","3"};
        assertThrows(ClubException.class, () -> {
            clubAR.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Anyadir Actividad menor que maximo")
    void anyadirActividadMenor() throws ClubException {

        String[] datos = new String[]{"456B", "Pilates", "8", "5", "50.0"};
        Grupo g = new Grupo("456B", "Pilates", 8, 5, 50.0);

        clubAR.anyadirActividad(datos);

        assertTrue(clubAR.toString().contains(g.toString()));
    }

    @Test
    @DisplayName("Anyadir Actividad mayor que maximo")
    void anyadirActividadMayor() throws ClubException {

        String[] datos = new String[]{"456B", "Pilates", "12", "5", "50.0"};
        Grupo g = new Grupo("456B", "Pilates", 10, 5, 50.0);

        clubAR.anyadirActividad(datos);

        assertTrue(clubAR.toString().contains(g.toString()));
    }

    @Test
    @DisplayName("Anyadir una actividad con un formato incorrecto")
    void anyadirActividadFormatoIncorrecto() throws ClubException {
        String[] datos = {"a","b","c","d","e"};
        assertThrows(ClubException.class, () -> {
            clubAR.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Calculo de ingresos correcto")
    void calcularIngresos() throws ClubException {
        double cantidad = 0;
        String[] datos = new String[]{"456B", "Pilates", "12", "5", "50.0"};
        clubAR.anyadirActividad(datos);

        cantidad = clubAR.ingresos();

        assertEquals(cantidad, 312.5);
    }



}
