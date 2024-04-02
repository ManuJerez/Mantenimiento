package coladoble;

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
}
