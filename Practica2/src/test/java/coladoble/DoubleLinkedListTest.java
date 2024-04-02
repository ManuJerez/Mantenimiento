/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
*/

package coladoble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        @DisplayName("Anyadir nodo al principio de lista vacia")
        void anyadirNodoAlPrincipioListaVacia(){
            linkedList.prepend(1);

            assertEquals(1, linkedList.first());
        }


        @Test
        @DisplayName("Anyadir nodo al final de lista vacia")
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
        @DisplayName("Borrar primer nodo")
        void borrarPrimeNodo(){
            linkedList.deleteFirst();
            assertThrows(NullPointerException.class, () -> {
                linkedList.last();
            });
        }

        @Test
        @DisplayName("Borrar ultimo nodo")
        void borrarUltimoNodo(){
            linkedList.deleteLast();
            assertThrows(NullPointerException.class, () -> {
                linkedList.first();
            });
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
        @DisplayName("Obtener primer nodo de la lista")
        void obtenerPrimerNodo(){
            Integer first = linkedList.first();

            assertEquals(0, first);
        }

        @Test
        @DisplayName("Obtener ultimo nodo de la lista")
        void obtenerUltimoNodo(){
            Integer last = linkedList.last();

            assertEquals(2, last);
        }

        @Test
        @DisplayName("Obtener tamanyo de la lista")
        void obtenerTamanyo(){
            int tamanyo = linkedList.size();

            assertEquals(3, tamanyo);
        }
    }
}















