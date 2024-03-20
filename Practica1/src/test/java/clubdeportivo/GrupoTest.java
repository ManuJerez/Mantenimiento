package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest {

    Grupo grupo;

    @BeforeEach
    void inicializarGrupo() throws ClubException {
        grupo = new Grupo ("456B", "Pilates", 8, 5, 50.0);
    }

    @Test
    @DisplayName("Get codigo")
    void getCodigo()
    {
        String codigo;

        codigo = grupo.getCodigo();

        assertEquals(codigo,"456B");
    }

    @Test
    @DisplayName("Get actividad")
    void getActividad()
    {
        String actividad;

        actividad = grupo.getActividad();

        assertEquals(actividad,"Pilates");
    }

    @Test
    @DisplayName("Get plazas")
    void getPlazas()
    {
        int plazas;

        plazas = grupo.getPlazas();

        assertEquals(plazas,8);
    }

    @Test
    @DisplayName("Get matriculados")
    void getMatriculados()
    {
        int matriculados;

        matriculados = grupo.getMatriculados();

        assertEquals(matriculados,5);
    }

    @Test
    @DisplayName("Get tarifa")
    void getTarifa()
    {
        double tarifa;

        tarifa = grupo.getTarifa();

        assertEquals(tarifa,50.0);
    }

    @Test
    @DisplayName("Get plazas libres")
    void getPlazasLibres()
    {
        int libres;

        libres = grupo.plazasLibres();

        assertEquals(libres,3);
    }

    @Test
    @DisplayName("Actualizar plazas")
    void actualizarPlazas() throws ClubException {
        int plazas;

        grupo.actualizarPlazas(10);
        plazas = grupo.getPlazas();

        assertEquals(10,plazas);
    }

    @Test
    @DisplayName("Matricular")
    void matricular() throws ClubException {
        int matriculados;

        grupo.matricular(3);
        matriculados = grupo.getMatriculados();

        assertEquals(8, matriculados);
    }

    @Test
    @DisplayName("To String")
    void stringRes()
    {
        String res;

        res = grupo.toString();

        assertEquals("(456B - Pilates - 50.0 euros - P:8 - M:5)",res);
    }

    @Test
    @DisplayName("Equals iguales")
    void equalsIgual() throws ClubException {
        Grupo grupo2 = new Grupo ("456B", "Pilates", 8, 5, 50.0);
        boolean iguales;

        iguales = grupo.equals(grupo2);

        assertTrue(iguales);
    }

    @Test
    @DisplayName("Equals desiguales")
    void equalsDesigual() throws ClubException {
        Grupo grupo2 = new Grupo ("800A", "Pilates", 8, 5, 50.0);
        boolean iguales;

        iguales = grupo.equals(grupo2);

        assertFalse(iguales);
    }

    @Test
    @DisplayName("Hash iguales")
    void hashIguales() throws ClubException {
        Grupo grupo2 = new Grupo ("456B", "Pilates", 8, 5, 50.0);

        assertEquals(grupo.hashCode(),grupo2.hashCode());
    }

    @Test
    @DisplayName("Hash desiguales")
    void hashDesiguales() throws ClubException {
        Grupo grupo2 = new Grupo ("800A", "Pilates", 8, 5, 50.0);

        assertNotEquals(grupo.hashCode(),grupo2.hashCode());
    }



}
