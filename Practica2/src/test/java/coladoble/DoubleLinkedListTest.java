/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
 */

import coladoble.DoubleLinkedList;
import coladoble.DoubleLinkedQueueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> linkedList;

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
    @DisplayName("Anyadir nodo al principio de lista no vacia")
    void anyadirNodoAlPrincipioListaNoVacia(){
        linkedList.prepend(1);
        linkedList.prepend(0);

        assertEquals(0, linkedList.first());
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
    @DisplayName("Borrar primer nodo lista no vacia")
    void borrarPrimerNodoListaNoVacia(){
        linkedList.prepend(1);
        linkedList.append(2);

        linkedList.deleteFirst();

        assertEquals(linkedList.first(), 2);
    }

    @Test
    @DisplayName("Borrar ultimo nodo lista vacia")
    void borrarUltimoNodoListaVacia(){
        assertThrows(DoubleLinkedQueueException.class, () ->{
           linkedList.deleteLast();
        });
    }

    @Test
    @DisplayName("Borrar ultimo nodo lista no vacia")
    void borrarUltimoNodoListaNoVacia(){
        linkedList.prepend(1);
        linkedList.append(2);

        linkedList.deleteLast();

        assertEquals(linkedList.last(), 1);
    }

    @Test
    @DisplayName("Obtener primer nodo de la lista")
    void obtenerPrimerNodo(){
        linkedList.prepend(1);
        linkedList.append(2);
        linkedList.prepend(3);

        assertEquals(linkedList.first(), 3);
    }

    @Test
    @DisplayName("Obtener ultimo nodo de la lista")
    void obtenerUltimoNodo(){
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        assertEquals(linkedList.last(), 3);
    }

    @Test
    @DisplayName("Obtener tamanyo de la lista")
    void obtenerTamanyo(){
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);

        assertEquals(linkedList.size(), 3);
    }

}















