package org.mps.ronqi2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mps.dispositivo.Dispositivo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ronQI2SilverTest {

    Dispositivo dispositivoMock;
    RonQI2Silver ronQI2Silver;

    @BeforeEach
    public void setup(){
        dispositivoMock = mock(Dispositivo.class);
        ronQI2Silver = new RonQI2Silver();
    }

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser.
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos,
     * el método inicializar de ronQI2 o sus subclases,
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    @Nested
    @DisplayName("Inicializar")
    public class Inicializar{
        @Test
        @DisplayName("Inicializar con ambos sensores conectados devuelve true")
        public void inicializar_WhenAllSensorsConnected_ReturnTrue(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();

            assertTrue(res);
        }

        @Test
        @DisplayName("Inicializar con sensor presion desconectado devuelve false")
        public void inicializar_WhenPressureSensorDisconnected_ReturnFalse(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();

            assertFalse(res);
        }

        @Test
        @DisplayName("Inicializar con sensor sonido desconectado devuelve false")
        public void inicializar_WhenSoundSensorDisconnected_ReturnFalse(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();

            assertFalse(res);
        }

        @Test
        @DisplayName("Inicializar con sensor presion desconfigurado devuelve false")
        public void inicializar_WhenPressureSensorMisconfigured_ReturnFalse(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(false);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();

            assertFalse(res);
        }

        @Test
        @DisplayName("Inicializar con sensor sonido desconfigurado devuelve false")
        public void inicializar_WhenSoundSensorMisconfigured_ReturnFalse(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(false);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();

            assertFalse(res);
        }

        /*
         * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true),
         * se llama una sola vez al configurar de cada sensor.
         */
        @Test
        @DisplayName("Inicializar correctamente (estaConectado devuelve true) llama solo una vez a configurar de cada sensor")
        public void inicializar_WhenConectarIsTrue_OnlyOneTimeConfigureEachSensor(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);
            when(dispositivoMock.estaConectado()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();
            boolean estaConectado = ronQI2Silver.estaConectado();

            assertTrue(res);
            assertTrue(estaConectado);

            verify(dispositivoMock).configurarSensorPresion();
            verify(dispositivoMock).configurarSensorSonido();
        }
    }

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    @Nested
    @DisplayName("Reconectar dispositivo")
    class ReconectarDispositivo
    {
        @Test
        @DisplayName("Reconectar dispositivo desconectado")
        public void reconectarDispositivoDesconectado()
        {
            when(dispositivoMock.estaConectado()).thenReturn(false);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean reconectado = ronQI2Silver.reconectar();
            assertTrue(reconectado);

            verify(dispositivoMock).conectarSensorPresion();
            verify(dispositivoMock).conectarSensorSonido();
        }

        @Test
        @DisplayName("Reconectar dispositivo conectado")
        public void reconectarDispositivoConectado()
        {
            when(dispositivoMock.estaConectado()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean reconectado = ronQI2Silver.reconectar();
            assertFalse(reconectado);

            verify(dispositivoMock, never()).conectarSensorPresion();
            verify(dispositivoMock, never()).conectarSensorSonido();
        }

        @Test
        @DisplayName("Reconectar dispositivo desconectado, fallo al conectar sensor presion")
        public void reconectarDispositivoDesconectadoFalloPresion()
        {
            when(dispositivoMock.estaConectado()).thenReturn(false);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean reconectado = ronQI2Silver.reconectar();
            assertFalse(reconectado);

            verify(dispositivoMock).conectarSensorPresion();
            verify(dispositivoMock, never()).conectarSensorSonido();
        }

        @Test
        @DisplayName("Reconectar dispositivo desconectado, fallo al conectar sensor sonido")
        public void reconectarDispositivoDesconectadoFalloSonido()
        {
            when(dispositivoMock.estaConectado()).thenReturn(false);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean reconectado = ronQI2Silver.reconectar();
            assertFalse(reconectado);

            verify(dispositivoMock).conectarSensorPresion();
            verify(dispositivoMock).conectarSensorSonido();
        }

        @Test
        @DisplayName("Reconectar dispositivo desconectado, fallo al conectar ambos sensores")
        public void reconectarDispositivoDesconectadoFalloAmbos()
        {
            when(dispositivoMock.estaConectado()).thenReturn(false);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean reconectado = ronQI2Silver.reconectar();
            assertFalse(reconectado);

            verify(dispositivoMock).conectarSensorPresion();
            verify(dispositivoMock, never()).conectarSensorSonido();
        }
    }


    @Nested
    @DisplayName("Evaluar apnea suenyo")
    class EvaluarApneaSuenyo {
        /*
         * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(),
         * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;,
         * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
         */


        /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
         * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
         * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
         */
        @ParameterizedTest
        @ValueSource(ints = {2, 4, 5, 10})
        @DisplayName("Evaluar apnea con diferente numero de lecturas iguales al limite")
        public void evaluateApnea_WithDiffNumOfReadings(int readings){
            when(dispositivoMock.leerSensorPresion()).thenReturn(20.0f);
            when(dispositivoMock.leerSensorSonido()).thenReturn(30.0f);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            for(int i = 0; i < readings; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            boolean res = ronQI2Silver.evaluarApneaSuenyo();

            assertTrue(res);

            verify(dispositivoMock,times(readings)).leerSensorPresion();
            verify(dispositivoMock,times(readings)).leerSensorSonido();
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 4, 5, 10})
        @DisplayName("Evaluar apnea con diferente numero de lecturas por debajo del limite")
        public void evaluateApnea_WithDiffNumOfReadingsUnderThreshold(int readings)
        {
            when(dispositivoMock.leerSensorPresion()).thenReturn(19.0f);
            when(dispositivoMock.leerSensorSonido()).thenReturn(29.0f);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            for(int i = 0; i < readings; i++)
                ronQI2Silver.obtenerNuevaLectura();

            boolean res = ronQI2Silver.evaluarApneaSuenyo();

            assertFalse(res);

            verify(dispositivoMock,times(readings)).leerSensorPresion();
            verify(dispositivoMock,times(readings)).leerSensorSonido();
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 4, 5, 10})
        @DisplayName("Evaluar apnea con diferente numero de lecturas superando la presion pero no el sonido")
        public void evaluateApnea_WithDiffNumOfReadingsSoundUnderThreshold(int readings){
            when(dispositivoMock.leerSensorPresion()).thenReturn(20.0f);
            when(dispositivoMock.leerSensorSonido()).thenReturn(29.0f);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            for(int i = 0; i < readings; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            boolean res = ronQI2Silver.evaluarApneaSuenyo();

            assertFalse(res);

            verify(dispositivoMock,times(readings)).leerSensorPresion();
            verify(dispositivoMock,times(readings)).leerSensorSonido();
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 4, 5, 10})
        @DisplayName("Evaluar apnea con diferente numero de lecturas superando el sonido pero no la presion")
        public void evaluateApnea_WithDiffNumOfReadingsPressureUnderThreshold(int readings){
            when(dispositivoMock.leerSensorPresion()).thenReturn(19.0f);
            when(dispositivoMock.leerSensorSonido()).thenReturn(30.0f);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            for(int i = 0; i < readings; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            boolean res = ronQI2Silver.evaluarApneaSuenyo();

            assertFalse(res);

            verify(dispositivoMock,times(readings)).leerSensorPresion();
            verify(dispositivoMock,times(readings)).leerSensorSonido();
        }
    }
}
