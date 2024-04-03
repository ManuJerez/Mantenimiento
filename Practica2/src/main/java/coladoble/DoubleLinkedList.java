/*
AUTORES: Manuel Jesús Jerez Sánchez y Pablo Astudillo Fraga
*/

package coladoble;

import java.util.Comparator;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void prepend(T value) {
        LinkedNode<T> node = new LinkedNode<>(value,null,null);
        if(size == 0)
        {
            first = node;
            last = node;
        }
        else
        {
            LinkedNode<T> aux = first;
            aux.setPrevious(node);
            node.setNext(aux);
            first = node;
        }
        size++;
    }

    @Override
    public void append(T value) {
        LinkedNode<T> node = new LinkedNode<>(value,null,null);
        if(size == 0)
        {
            first = node;
            last = node;
        }
        else
        {
            LinkedNode<T> aux = last;
            aux.setNext(node);
            node.setPrevious(aux);
            last = node;
        }
        size++;
    }

    @Override
    public void deleteFirst() {
        if(size == 0)
        {
            throw new DoubleLinkedQueueException("No se puede borrar en una lista vacia");
        }
        else
        {
            first = first.getNext();
            size--;
            if(size == 0)
                last = null;
        }
    }

    @Override
    public void deleteLast() {
        if(size == 0)
        {
            throw new DoubleLinkedQueueException("No se puede borrar en una lista vacia");
        }
        else
        {
            last = last.getPrevious();
            size--;
            if(size == 0)
                first = null;
        }
    }

    @Override
    public T first() {
        return first.getItem();
    }

    @Override
    public T last() {
        return last.getItem();
    }

    @Override
    public int size() {
        return size;
    }
    /* 
    @Override
    public T get(int index) {
        if(index < 0)
            throw new DoubleLinkedQueueException("No hay indice negativo");
        else {
            int i = 0;
            LinkedNode<T> node = first;

            while (i < index) {
                node = node.getNext();
                index++;
            }

            return node.getItem();
        }
    }

    @Override
    public boolean contains(T value) {
        LinkedNode<T> node = first;

        while(node != null && !node.getItem().equals(value))
            node = node.getNext();

        return node != null;
    }

    @Override
    public void remove(T value) {
        if(!contains(value))
            throw new DoubleLinkedQueueException("No existe este valor en la cola doble");
        else {
            LinkedNode<T> prev = null;
            LinkedNode<T> node = first;

            while (!node.getItem().equals(value)) {
                prev = node;
                node = node.getNext();
            }

            if (node.isFirstNode()) {
                node.getNext().setPrevious(null);
                first = node.getNext();
            } else if (node.isLastNode()) {
                prev.setNext(null);
                last = prev;
            } else {
                node.getNext().setPrevious(prev);
                prev.setNext(node.getNext());
            }
        }
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        if (size <= 1) return;

        LinkedNode<T> node = first;

        while (node != null) {
            LinkedNode<T> minNode = node;
            LinkedNode<T> aux = node.getNext();
            while (aux != null) {
                if (comparator.compare(aux.getItem(), minNode.getItem()) < 0) {
                    minNode = aux;
                }
                aux = aux.getNext();
            }

            T auxItem = node.getItem();
            node.setItem(minNode.getItem());
            minNode.setItem(auxItem);
            node = node.getNext();
        }
    }
    */
}
