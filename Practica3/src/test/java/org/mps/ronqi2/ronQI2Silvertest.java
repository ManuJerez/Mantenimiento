package org.mps.ronqi2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ronQI2Silvertest {

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

        /*
         * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true),
         * se llama una sola vez al configurar de cada sensor.
         */
        @Test
        @DisplayName("Inicializar correctamente (estaConectadoTrue) llama solo una vez a configurar de cada sensor")
        public void inicializar_WhenConectarIsTrue_OnlyOneTimeConfigureEachSensor(){
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);
            when(dispositivoMock.estaConectado()).thenReturn(true);

            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            boolean res = ronQI2Silver.inicializar();
            assertTrue(res);
            assertTrue(dispositivoMock.estaConectado());

            verify(dispositivoMock).conectarSensorPresion();
            verify(dispositivoMock).configurarSensorSonido();
        }
    }

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
