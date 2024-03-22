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
    @DisplayName("Crear el club de alto rendimiento en el rango negativo")
    void crearClubNegativo() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", -5, 0);
        });
    }

    @Test
    @DisplayName("Crear el club de alto rendimiento en el rango negativo con tam")
    void crearClubNegativoConTam() throws ClubException {
        assertThrows(ClubException.class, () -> {
            new ClubDeportivoAltoRendimiento("ClubAR", 8, -5, 0);
        });
    }

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
