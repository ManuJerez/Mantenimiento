package coladoble;

public class TestDoubleLinkedList {
    public static void main(String[] args) {
        // Crear una lista doble
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();

        // Agregar elementos a la lista
        list.append(1);
        list.append(2);
        list.prepend(0);

        // Verificar el tamaño de la lista
        System.out.println("Tamaño de la lista: " + list.size());

        // Mostrar el primer y último elemento
        System.out.println("Primer elemento: " + list.first());
        System.out.println("Último elemento: " + list.last());

        // Eliminar el primer y último elemento
        list.deleteFirst();
        list.deleteLast();

        // Verificar el tamaño de la lista después de eliminar elementos
        System.out.println("Tamaño de la lista después de eliminar elementos: " + list.size());

        // Verificar si la lista está vacía
        System.out.println("¿La lista está vacía? " + (list.size() == 0 ? "Sí" : "No"));
    }
}
