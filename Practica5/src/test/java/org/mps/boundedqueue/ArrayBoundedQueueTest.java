package org.mps.boundedqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class ArrayBoundedQueueTest {
    ArrayBoundedQueue boundedQueue;

    @Nested
    @DisplayName("Con cola vacia")
    class emptyBoundedQueue{
        @BeforeEach
        void setupEmptyQueue(){
            boundedQueue = new ArrayBoundedQueue(5);
        }

        @Test
        @DisplayName("isEmpty devuelve true")
        void isEmpty_ReturnTrue(){
            assertThat(boundedQueue).isEmpty();
        }

        @Test
        @DisplayName("isFull devuelve false")
        void isFull_ReturnFalse(){
            assertThat(boundedQueue.isFull()).isFalse();
        }

        @Test
        @DisplayName("Size == 0")
        void size_ReturnZero(){
            assertThat(boundedQueue.size()).isZero();
        }

        @Test
        @DisplayName("Primer elemento es cero")
        void getFirst_ReturnZero(){
            assertThat(boundedQueue.getFirst()).isZero();
        }

        @Test
        @DisplayName("Ultimo elemento es cero")
        void getLast_ReturnZero(){
            assertThat(boundedQueue.getLast()).isZero();
        }

        @Test
        @DisplayName("Iterator devuelve iterador vacio")
        void iterator_ReturnEmptyIterator(){
            Iterator iterator = boundedQueue.iterator();
            assertThat(iterator.hasNext()).isFalse();
            assertThatThrownBy(iterator::next).hasMessage("next: bounded queue iterator exhausted");
        }

    }
}
