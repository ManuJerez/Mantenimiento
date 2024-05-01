package org.mps.boundedqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

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
        @BeforeEach
        void setupEmptyQueue(){
            boundedQueue = new ArrayBoundedQueue<>(5);
            boundedQueue.put(1);
            boundedQueue.put(2);
            boundedQueue.put(3);
            boundedQueue.put(4);
            boundedQueue.put(5);
        }

        @Nested
        @DisplayName("Put insert an element int the queue")
        public class put_OnFullQueue
        {
            @Test
            @DisplayName("put insert a null element in a full queue")
            void put_InsertANullElement_ThrowException()
            {
                Throwable insertNullValue = catchThrowable( () -> {
                    boundedQueue.put(null);
                });
                assertThat(insertNullValue)
                        .hasMessage("put: full bounded queue");
            }

            @Test
            @DisplayName("put insert an element in a full queue")
            void put_InsertAnElement_ThrowException()
            {
                Throwable insert = catchThrowable( () -> {
                    boundedQueue.put(6);
                });
                assertThat(insert)
                        .hasMessage("put: full bounded queue");
            }
        }

        @Test
        @DisplayName("Get return the first element of the queue and delete the element from the queue")
        void get_ReturnAnElementOfTheQueue()
        {
            int element = (int) boundedQueue.get();

            assertThat(element).isEqualTo(1);
            assertThat(boundedQueue).doesNotContain(element);
        }

        @Test
        @DisplayName("isFull return true")
        void isFull_ReturnTrue()
        {
            boolean isFull = boundedQueue.isFull();

            assertThat(isFull).isTrue();
        }

        @Test
        @DisplayName("isEmpty return false")
        void isEmpty_ReturnFalse()
        {
            boolean isEmpty = boundedQueue.isEmpty();

            assertThat(isEmpty).isFalse();
        }

        @Test
        @DisplayName("size return the value of the queue length that is 5")
        void size_ReturnQueueLength()
        {
            int size = boundedQueue.size();

            assertThat(boundedQueue).size().isEqualTo(size);
        }

        @Test
        @DisplayName("getFirst return the index of the first element that is 0")
        void getFirst_ReturnZero()
        {
            int firstElement = boundedQueue.getFirst();

            assertThat(firstElement).isZero();
        }

        @Test
        @DisplayName("getLast return the first position available that is 0")
        void getLast_ReturnFive()
        {
            int lastElement = boundedQueue.getLast();

            assertThat(lastElement).isZero();
        }

        @Nested
        @DisplayName("Iterator iterate the elements from a queue")
        public class iterator_OnAFullQueue
        {
            Iterator iterator;
            @BeforeEach
            void setupIterator()
            {
                iterator = boundedQueue.iterator();
            }

            @Test
            @DisplayName("hasNext return true")
            void hasNext_ReturnTrue()
            {
                boolean hasNext = iterator.hasNext();

                assertThat(hasNext).isTrue();
            }

            @Test
            @DisplayName("next return the next element")
            void next_ReturnNext()
            {
                int next = (int) iterator.next();

                assertThat(boundedQueue).element(boundedQueue.getFirst()).isEqualTo(next);
            }
        }
    }
}
