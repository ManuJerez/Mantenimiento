package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {
    ClubDeportivo club;
    Grupo[] grupos;
    Grupo pilates;

    @BeforeEach
    void setup() throws ClubException{
        club = new ClubDeportivo("UMA",1);
        pilates = new Grupo("456B","Pilates",8,5,50.0);
    }

    @Test
    @DisplayName("Crear club deportivo con numero de grupos 0 o negativos")
    void crearClubConCantidadDeGruposNulaONegativa(){
        assertThrows(ClubException.class, () -> {
            new ClubDeportivo("UMA", 0);
        });
    }

    @Test
    @DisplayName("Anyadir actividad con datos")
    void correctAnyadirActividad() throws ClubException{
        String [] datos = {"123A","Kizomba","10","10","25.0"};
        club.anyadirActividad(datos);

        assertTrue(club.toString().contains("Kizomba"));
    }

    @Test
    @DisplayName("Anyadir actividad con datos incorrectos")
    void incorrectAnyadirActividad() throws ClubException {
        String[] datos = {"A", "Kizomba", "a", "b", "c"};
        assertThrows(ClubException.class, () ->{
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Anyadir actividad con grupo nuevo")
    void correctAnyadirActividadGrupoNuevo() throws ClubException{
        club.anyadirActividad(pilates);

        assertTrue(club.toString().contains(pilates.toString()));
    }

    @Test
    @DisplayName("Anyadir actividad con grupo existente")
    void correctAnyadirActividadGrupoExistente() throws ClubException{
        club.anyadirActividad(pilates);
        //Anyadimos otra actividad al grupo existente de pilates
        club.anyadirActividad(pilates);

        assertTrue(club.toString().contains(pilates.toString()));
    }

    @Test
    @DisplayName("Anyadir actividad en grupo nulo")
    void anyadirActividadEnGrupoNulo() throws ClubException{
        Grupo g = null;
        assertThrows(ClubException.class, () -> {
           club.anyadirActividad(g);
        });
    }

    @Test
    @DisplayName("Comprobar plazas libres")
    void comprobarPlazasLibres() throws ClubException{
        club.anyadirActividad(pilates);

        int plazas = club.plazasLibres("Pilates");

        assertEquals(plazas, pilates.plazasLibres());
    }

    @Test
    @DisplayName("Matricular menos personas que plazas disponibles")
    void correctMatricularMenosPlazasQueDisponibles() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = 1;
        int plazasLibres = club.plazasLibres("Pilates");
        
        club.matricular("Pilates", npersonas);

        assertEquals(club.plazasLibres("Pilates"), plazasLibres-npersonas);
    }

    @Test
    @DisplayName("Matricular mismas personas que plazas disponibles")
    void correctMatricularTodasPlazas() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = club.plazasLibres("Pilates");

        club.matricular("Pilates", npersonas);

        assertEquals(club.plazasLibres("Pilates"), 0);
    }

    @Test
    @DisplayName("Matricular mas personas que plazas disponibles")
    void correctMatricularMasPlazasQueDisponibles() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = club.plazasLibres("Pilates");

        assertThrows(ClubException.class, () -> {
           club.matricular("Pilates", npersonas+1); 
        });

    }


    @Test
    @DisplayName("Calcular ingresos")
    void correctCalculodeIngresos() throws ClubException{
        club.anyadirActividad(pilates);
        assertTrue(club.ingresos() > 0);
    }
}
