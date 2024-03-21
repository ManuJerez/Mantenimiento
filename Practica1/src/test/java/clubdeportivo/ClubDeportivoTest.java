/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
 */

package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void noCrearClubConCantidadDeGruposNulaONegativa(){
        assertThrows(ClubException.class, () -> {
            new ClubDeportivo("UMA", 0);
        });
    }

    @Test
    @DisplayName("Anyadir actividad con datos")
    void anyadirActividadConDatos() throws ClubException{
        String [] datos = {"123A","Kizomba","10","10","25.0"};
        club.anyadirActividad(datos);

        assertTrue(club.toString().contains("Kizomba"));
    }

    @Test
    @DisplayName("Anyadir actividad con datos incorrectos")
    void noAnyadirActividadConDatosIncorrectos() throws ClubException {
        String[] datos = {"A", "Kizomba", "a", "b", "c"};
        assertThrows(ClubException.class, () ->{
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Anyadir actividad con grupo nuevo")
    void anyadirActividadGrupoNuevo() throws ClubException{
        club.anyadirActividad(pilates);

        assertTrue(club.toString().contains(pilates.toString()));
    }

    @Test
    @DisplayName("Anyadir actividad con grupo existente")
    void anyadirActividadGrupoExistente() throws ClubException{
        club.anyadirActividad(pilates); //Anyadimos un grupo al club
        Grupo spinning = new Grupo("789A","Spinning",10,2,30.0);
        club.anyadirActividad(spinning); //Anyadimos otro grupo al club

        //Anyadimos otra actividad al grupo existente de spinning
        club.anyadirActividad(spinning);

        assertTrue(club.toString().contains(spinning.toString()));
    }

    @Test
    @DisplayName("Anyadir actividad en grupo nulo")
    void anyadirActividadGrupoNulo() throws ClubException{
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
    void matricularMenosPlazasQueDisponibles() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = 1;
        int plazasLibres = club.plazasLibres("Pilates");
        
        club.matricular("Pilates", npersonas);

        assertEquals(club.plazasLibres("Pilates"), plazasLibres-npersonas);
    }

    @Test
    @DisplayName("Matricular mismas personas que plazas disponibles")
    void matricularTodasPlazas() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = club.plazasLibres("Pilates");

        club.matricular("Pilates", npersonas);

        assertEquals(club.plazasLibres("Pilates"), 0);
    }

    @Test
    @DisplayName("Matricular mas personas que plazas disponibles")
    void noMatricularMasPlazasQueDisponibles() throws ClubException{
        club.anyadirActividad(pilates);
        int npersonas = club.plazasLibres("Pilates");

        assertThrows(ClubException.class, () -> {
           club.matricular("Pilates", npersonas+1); 
        });

    }

    @Test
    @DisplayName("Calcular ingresos")
    void calcularIngresos() throws ClubException{
        club.anyadirActividad(pilates);
        assertTrue(club.ingresos() > 0);
    }
}
