package coladoble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedNodeTest {
    LinkedNode<Integer> node;
    LinkedNode<Integer> prev;
    LinkedNode<Integer> next;


    @Nested
    @DisplayName("Nuevo nodo aislado")
    class newIsolatedNode
    {
        @BeforeEach
        void inicializarNodo()
        {
            node = new LinkedNode<>(0, null, null);
        }

        @Test
        @DisplayName("Item correcto")
        void correctItem()
        {
            int item = node.getItem();
            assertEquals(0, item);
        }

        @Test
        @DisplayName("Sin predecesores ni progenitores")
        void isolatedNode()
        {
            LinkedNode<Integer> prev = node.getPrevious();
            LinkedNode<Integer> next = node.getNext();

            assertTrue(prev == null && next == null);
        }

        @Test
        @DisplayName("Es primer y ultimo nodo")
        void firstnLast()
        {
            assertTrue(node.isFirstNode() && node.isLastNode());
        }
    }

    @Nested
    @DisplayName("Nuevo nodo")
    class newNode
    {
        @BeforeEach
        void inicializarNodo()
        {
            prev = new LinkedNode<>(0, null, null);
            node = new LinkedNode<>(1, null, null);
            next = new LinkedNode<>(2, null, null);

            prev.setNext(node);
            next.setPrevious(node);
            node.setNext(next);
            node.setPrevious(prev);
        }

        @Test
        @DisplayName("Item correcto")
        void correctItem()
        {
            int item = node.getItem();
            assertEquals(1, item);
        }

        @Test
        @DisplayName("Predecesores y progenitores correctos")
        void nodeNeighbours()
        {
            LinkedNode<Integer> prevNode = node.getPrevious();
            LinkedNode<Integer> nextNode = node.getNext();

            assertTrue(prevNode == prev && nextNode == next);
        }

        @Test
        @DisplayName("Primer nodo correcto")
        void correctFirstNode()
        {
            assertTrue(prev.isFirstNode() && !node.isFirstNode() && !next.isFirstNode());
        }

        @Test
        @DisplayName("Ultimo nodo correcto")
        void correctLastNode()
        {
            assertTrue(!prev.isLastNode() && !node.isLastNode() && next.isLastNode());
        }
    }

}
