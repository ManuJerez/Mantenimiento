/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
 */

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
    @DisplayName("No crear grupo con numero de plazas incorrecto")
    void noCrearGrupoNumPlazasIncorrecto() throws ClubException{
        assertThrows(ClubException.class, () ->{
            new Grupo("146", "Zumba", 0, 4, 5);
        });
    }

    @Test
    @DisplayName("No crear grupo con numero de matriculados incorrecto")
    void noCrearGrupoNumMatriculadosIncorrecto() throws ClubException{
        assertThrows(ClubException.class, () ->{
            new Grupo("146", "Zumba", 8, -3, 5);
        });
    }

    @Test
    @DisplayName("No crear grupo con tarifa incorrecta")
    void noCrearGrupoTarifaIncorrecta() throws ClubException{
        assertThrows(ClubException.class, () ->{
            new Grupo("146", "Zumba", 8, 4, -2);
        });
    }

    @Test
    @DisplayName("No crear grupo con menos plazas que matriculados")
    void noCrearGrupoMenosPlazasQueMatriculados() throws ClubException{
        assertThrows(ClubException.class, () ->{
            new Grupo("643", "Zumba", 8, 10, 34);
        });
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
    @DisplayName("No actualizar plazas con numero de plazas negativo o cero")
    void noActualizarPlazasCantidadIncorrecta() throws ClubException{
        assertThrows(ClubException.class, () ->{
            grupo.actualizarPlazas(-5);
        });
    }

    @Test
    @DisplayName("No actualizar plazas con numero de plazas menor que numero de matriculados")
    void noActualizarPlazasCantidadMenorQueMatriculados() throws ClubException{
        assertThrows(ClubException.class, () -> {
            grupo.actualizarPlazas(4);
        });
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
    @DisplayName("No matricular si no hay plazas libres")
    void noMatricularSinPlazasLibres() throws ClubException{
        int num = grupo.plazasLibres() + 2;
        assertThrows(ClubException.class, () -> {
           grupo.matricular(num);
        });
    }

    @Test
    @DisplayName("No matricular cantidad negativa")
    void noMatricularCantidadNegativa() throws ClubException {
        assertThrows(ClubException.class, () -> {
            grupo.matricular(-2);
        });
    }

    @Test
    @DisplayName("No matricular cantidad nula")
    void noMatricularCantidadNula() throws ClubException {
        assertThrows(ClubException.class, () -> {
           grupo.matricular(0);
        });
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
