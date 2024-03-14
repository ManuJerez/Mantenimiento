package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClubDeportivoAltoRendimientoTest {

    private ClubDeportivoAltoRendimiento clubAR;

    @BeforeEach
    void inicializarClubSinTam() throws ClubException {
        clubAR = new ClubDeportivoAltoRendimiento("ClubAR", 10, 25.0);
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
    @DisplayName("Calculo de ingresos correcto")
    void calcularIngresos()
    {
        double cantidad = 0;

        cantidad = clubAR.ingresos();

        // assertEquals(cantidad, 0);
    }



}
