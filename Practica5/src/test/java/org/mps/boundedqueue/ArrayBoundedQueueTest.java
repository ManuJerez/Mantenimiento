package org.mps.boundedqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

public class ArrayBoundedQueueTest {
    ArrayBoundedQueue boundedQueue;

    @Nested
    @DisplayName("Con cola vacia")
    class emptyBoundedQueue{
        @BeforeEach
        void setupEmptyQueue(){
            boundedQueue = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Crear cola con capacidad nula lanza excepcion")
        void create_NullCapacityBoundedQueue_ThrowException(){
            Throwable createNullCapacityQueue = catchThrowable( () -> {
                new ArrayBoundedQueue<>(0);
            });
            assertThat(createNullCapacityQueue)
                    .hasMessage("ArrayBoundedException: capacity must be positive");
        }

        @Test
        @DisplayName("Put inserta un nuevo elemento en la cola")
        void put_InsertAnElementInQueue(){
            boundedQueue.put(4);
            assertThat(boundedQueue).contains(4);
        }

        @Test
        @DisplayName("Get lanza excepcion")
        void get_ReturnAnElementOfTheQueue(){
            assertThatThrownBy(boundedQueue::get).hasMessage("get: empty bounded queue");
        }

        @Test
        @DisplayName("isFull devuelve false")
        void isFull_ReturnFalse(){
            assertThat(boundedQueue.isFull()).isFalse();
        }

        @Test
        @DisplayName("isEmpty devuelve true")
        void isEmpty_ReturnTrue(){
            assertThat(boundedQueue).isEmpty();
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
        @DisplayName("Iterator devuelve iterador vacio y lanza excepcion")
        void iterator_ReturnEmptyIterator(){
            Iterator iterator = boundedQueue.iterator();
            assertThat(iterator.hasNext()).isFalse();
            assertThatThrownBy(iterator::next).hasMessage("next: bounded queue iterator exhausted");
        }

    }

    @Nested
    @DisplayName("Con cola llena")
    class fullBoundedQueue{

    }
}
