/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
*/

package coladoble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


public class DoubleLinkedListTest {

    DoubleLinkedList<Integer> linkedList;

    @Nested
    @DisplayName("En lista vacia")
    class EmptyDoubleLinkedList{
        @BeforeEach
        void setup(){
            linkedList = new DoubleLinkedList();
        }

        @Test
        @DisplayName("Anyadir nodo al principio lista vacia")
        void anyadirNodoAlPrincipioListaVacia(){
            linkedList.prepend(1);

            assertEquals(1, linkedList.first());
        }


        @Test
        @DisplayName("Anyadir nodo al final lista vacia")
        void anyadirNodoAlFinalListaVacia(){
            linkedList.append(1);

            assertEquals(1, linkedList.last());
        }

        @Test
        @DisplayName("Borrar primer nodo lista vacia")
        void borrarPrimerNodoListaVacia(){
            assertThrows(DoubleLinkedQueueException.class, () -> {
                linkedList.deleteFirst();
            });
        }

        @Test
        @DisplayName("Borrar ultimo nodo lista vacia")
        void borrarUltimoNodoListaVacia(){
            assertThrows(DoubleLinkedQueueException.class, () ->{
                linkedList.deleteLast();
            });
        }

        @Test
        @DisplayName("Obtener primer nodo de la lista")
        void obtenerPrimerNodo(){
            assertThrows(NullPointerException.class, () ->{
                linkedList.first();
            });
        }

        @Test
        @DisplayName("Obtener ultimo nodo de la lista")
        void obtenerUltimoNodo(){
            assertThrows(NullPointerException.class, () ->{
                linkedList.last();
            });
        }

        @Test
        @DisplayName("Obtener tamanyo de la lista")
        void obtenerTamanyo(){
            int tamanyo = linkedList.size();

            assertEquals(0, tamanyo);
        }

        @Test
        @DisplayName("Obtener lanza excepcion")
        void get_EmptyList_ThrowException(){
            assertThrows(DoubleLinkedQueueException.class, () ->{
               linkedList.get(0);
            });
        }

        @Test
        @DisplayName("Contiene devuelve false")
        void contains_EmptyList_IsFalse(){
            assertFalse(linkedList.contains(0));
        }

        @Test
        @DisplayName("Borrar lanza excepcion")
        void remove_EmptyList_ThrowException(){
            assertThrows(DoubleLinkedQueueException.class, () ->{
                linkedList.remove(3);
            });
        }

        @Test
        @DisplayName("Ordenar no produce cambios")
        void sort_WithoutChanges(){
            linkedList.sort(Comparator.naturalOrder());
            assertThrows(NullPointerException.class, () -> {
                linkedList.first();
            });
        }

    }

    @Nested
    @DisplayName("En lista con un elemento")
    class DoubleLinkedListWithOnlyOneElement{
        @BeforeEach
        void setup(){
            linkedList = new DoubleLinkedList<>();
            linkedList.prepend(0);
        }

        @Test
        @DisplayName("Borrar primer nodo lista unitaria")
        void borrarPrimeNodo(){
            linkedList.deleteFirst();
            assertThrows(NullPointerException.class, () -> {
                linkedList.last();
            });
        }

        @Test
        @DisplayName("Borrar ultimo nodo lista unitaria")
        void borrarUltimoNodo(){
            linkedList.deleteLast();
            assertThrows(NullPointerException.class, () -> {
                linkedList.first();
            });
        }

        @Test
        @DisplayName("Ordenar no produce cambios")
        void sort_WithoutChanges(){
            Integer first = linkedList.first();
            Integer last = linkedList.last();

            linkedList.sort(Comparator.reverseOrder());

            assertAll(
                    () -> assertEquals(first, linkedList.first()),
                    () -> assertEquals(last, linkedList.last())
            );
        }
    }

    @Nested
    @DisplayName("En lista con mas de un elemento")
    class NotEmptyDoubleLinkedList{

        @BeforeEach
        void setup(){
            linkedList = new DoubleLinkedList<>();
            linkedList.prepend(0);
            linkedList.append(1);
            linkedList.append(2);
        }

        @Test
        @DisplayName("Anyadir nodo al principio de lista no vacia")
        void anyadirNodoAlPrincipioListaNoVacia(){
            linkedList.prepend(7);

            Integer first = linkedList.first();

            assertEquals(7, first);
        }

        @Test
        @DisplayName("Anyadir nodo al final de lista no vacia")
        void anyadirNodoAlFinalListaNoVacia(){
            linkedList.append(7);

            Integer last = linkedList.last();

            assertEquals(7, last);
        }

        @Test
        @DisplayName("Borrar primer nodo lista no vacia")
        void borrarPrimerNodoListaNoVacia(){
            linkedList.deleteFirst();

            Integer first = linkedList.first();

            assertEquals(1, first);
        }

        @Test
        @DisplayName("Borrar ultimo nodo lista no vacia")
        void borrarUltimoNodoListaNoVacia(){
            linkedList.deleteLast();

            Integer last = linkedList.last();

            assertEquals(1, last);
        }

        @Test
        @DisplayName("Obtener primer nodo lista no vacia")
        void obtenerPrimerNodo(){
            Integer first = linkedList.first();

            assertEquals(0, first);
        }

        @Test
        @DisplayName("Obtener ultimo nodo lista no vacia")
        void obtenerUltimoNodo(){
            Integer last = linkedList.last();

            assertEquals(2, last);
        }

        @Test
        @DisplayName("Obtener tamanyo lista no vacia")
        void obtenerTamanyo(){
            int tamanyo = linkedList.size();

            assertEquals(3, tamanyo);
        }

        @Test
        @DisplayName("Obtener elemento lista no vacia correcto")
        void get_ReturnAElement(){
            Integer element = linkedList.get(1);

            Integer elementoEsperado = 1;

            assertEquals(elementoEsperado, element);
        }

        @Test
        @DisplayName("Obtener elemento lista no vacia indice negativo lanza excepcion")
        void get_IndexNegative_ThrowException(){
            assertThrows(DoubleLinkedQueueException.class, () -> {
               linkedList.get(-3);
            });
        }

        @Test
        @DisplayName("Obtener elemento lista no vacia indice mayor que tamanyo lanza excepcion")
        void get_IndexGreaterOrEqualThanSize_ThrowException(){
            assertThrows(DoubleLinkedQueueException.class, () -> {
                linkedList.get(5);
            });
        }

        @Test
        @DisplayName("Contiene lista no vacia con elemento contenido devuelve true")
        void contains_ReturnTrue(){
            Integer element = 2;

            assertTrue(linkedList.contains(element));
        }

        @Test
        @DisplayName("Contiene lista no vacia con elemento no contenido devuelve false")
        void contais_ReturnFalse(){
            Integer element = 7;

            assertFalse(linkedList.contains(element));
        }

        @Test
        @DisplayName("Borrar en lista no vacia correcto")
        void remove_Correct(){
            Integer element = 1;

            linkedList.remove(element);

            assertFalse(linkedList.contains(element));
            assertNotEquals(3, linkedList.size());
        }

        @Test
        @DisplayName("Borrar primer elemento lista no vacia correcto")
        void remove_FirstElement_Correct(){
            Integer element = 0;

            linkedList.remove(0);

            assertFalse(linkedList.contains(element));
            assertNotEquals(3, linkedList.size());
        }

        @Test
        @DisplayName("Borrar ultimo elemento lista no vacia correcto")
        void remove_LastElement_Correct(){
            Integer element = linkedList.size()-1;

            linkedList.remove(element);

            assertFalse(linkedList.contains(element));
            assertNotEquals(3, linkedList.size());
        }

        @Test
        @DisplayName("Borrar elemento inexistente lista no vacia lanza excepcion")
        void remove_NonExistentElement_ThrowException(){
            Integer element = 7;

            assertThrows(DoubleLinkedQueueException.class, () ->{
                linkedList.remove(element);
            });
        }

        @Test
        @DisplayName("Ordenar lista no vacia correcto")
        void sort_Correct(){
            linkedList.sort(Comparator.reverseOrder());

            DoubleLinkedList<Integer> orderedList = new DoubleLinkedList<>();
            orderedList.prepend(0);
            orderedList.prepend(1);
            orderedList.prepend(2);

            for(int i = 0; i < linkedList.size(); i++){
                Integer elExpected = orderedList.get(i);
                Integer elActual = linkedList.get(i);

                assertEquals(elExpected, elActual);
            }
        }
    }

}















