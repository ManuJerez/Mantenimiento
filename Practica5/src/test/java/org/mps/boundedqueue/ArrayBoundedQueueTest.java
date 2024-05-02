/*
AUTORES:
- MANUEL JESUS JEREZ SANCHEZ
- PABLO ASTUDILLO FRAGA
 */

package org.mps.boundedqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

public class ArrayBoundedQueueTest {
    ArrayBoundedQueue<Integer> boundedQueue;

    @Nested
    @DisplayName("Empty queue")
    class emptyBoundedQueue{
        @BeforeEach
        void setupEmptyQueue(){
            boundedQueue = new ArrayBoundedQueue<>(5);
        }

        @Test
        @DisplayName("Create empty queue throw exception")
        void create_NullCapacityBoundedQueue_ThrowException(){
            Throwable createNullCapacityQueue = catchThrowable( () -> {
                new ArrayBoundedQueue<>(0);
            });
            assertThat(createNullCapacityQueue)
                    .hasMessage("ArrayBoundedException: capacity must be positive");
        }

        @Test
        @DisplayName("Put insert an element in queue")
        void put_InsertAnElementInQueue(){
            boundedQueue.put(4);
            assertThat(boundedQueue).contains(4);
        }

        @Test
        @DisplayName("Put a null element throw exception")
        void put_NullElement_ThrowException(){
            Throwable putNullElement = catchThrowable( () -> {
                boundedQueue.put(null);
            });
            assertThat(putNullElement).hasMessage("put: element cannot be null");
        }

        @Test
        @DisplayName("Get throw exception")
        void get_ThrowException(){
            assertThatThrownBy(boundedQueue::get).hasMessage("get: empty bounded queue");
        }

        @Test
        @DisplayName("isFull return false")
        void isFull_ReturnFalse(){
            boolean res = boundedQueue.isFull();
            assertThat(res).isFalse();
        }

        @Test
        @DisplayName("isEmpty return true")
        void isEmpty_ReturnTrue(){
            assertThat(boundedQueue).isEmpty();
        }

        @Test
        @DisplayName("Size is zero")
        void size_ReturnZero(){
            int size = boundedQueue.size();
            assertThat(size).isZero();
        }

        @Test
        @DisplayName("First element is zero")
        void getFirst_ReturnZero(){
            int first = boundedQueue.getFirst();
            assertThat(first).isZero();
        }

        @Test
        @DisplayName("Last element is zero")
        void getLast_ReturnZero(){
            int last = boundedQueue.getLast();
            assertThat(last).isZero();
        }

        @Test
        @DisplayName("Last element after put is 1")
        void getLast_ReturnOne(){
            boundedQueue.put(2);

            int last = boundedQueue.getLast();

            assertThat(last).isOne();
        }

        @Test
        @DisplayName("Iterate in empty iterator throw exception")
        void iterator_ReturnEmptyIterator(){
            Iterator<Integer> iterator = boundedQueue.iterator();
            assertThat(iterator.hasNext()).isFalse();
            assertThatThrownBy(iterator::next).hasMessage("next: bounded queue iterator exhausted");
        }

    }

    @Nested
    @DisplayName("Full queue")
    class fullBoundedQueue{
        @BeforeEach
        void setupFullQueue(){
            boundedQueue = new ArrayBoundedQueue<>(5);
            boundedQueue.put(1);
            boundedQueue.put(2);
            boundedQueue.put(3);
            boundedQueue.put(4);
            boundedQueue.put(5);
        }

        @Nested
        @DisplayName("Put insert an element in the queue")
        public class put_OnFullQueue
        {
            @Test
            @DisplayName("Put a null element in full queue throw exception")
            void put_InsertANullElement_ThrowException()
            {
                Throwable insertNullValue = catchThrowable( () -> {
                    boundedQueue.put(null);
                });
                assertThat(insertNullValue)
                        .hasMessage("put: full bounded queue");
            }

            @Test
            @DisplayName("Put an element in full queue throw exception")
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
            int element = boundedQueue.get();

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
        @DisplayName("Size return the value of the queue length that is 5")
        void size_ReturnQueueLength()
        {
            int size = 5;

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
            Iterator<Integer> iterator;
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
            @DisplayName("Next return the next element")
            void next_ReturnNext()
            {
                int next = iterator.next();
                int firstElement = boundedQueue.getFirst();

                assertThat(boundedQueue).element(firstElement).isEqualTo(next);
            }
        }
    }
}
