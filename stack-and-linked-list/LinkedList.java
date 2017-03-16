/**
 * A singly linked list.
 * 
 * @author Adam Jacobs
 * @version january 2017
 */
public class LinkedList<T> { 
    private ListElement<T> first;   // First element in list.
    private ListElement<T> last;    // Last element in list.
    private int size;               // Number of elements in list.
    
    /**
     * A list element.
     */
    private static class ListElement<T> {
        public T data;
        public ListElement<T> next;
        
        public ListElement(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Creates an empty list.
     */
    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Inserts the given element at the beginning of this list.
     */
    public void addFirst(T element) {
        ListElement newFirst = new ListElement(element);

        if(size == 0) {
            first = newFirst;
            last = first;
        } else {
            ListElement previousFirst = first;
            first = newFirst;
            first.next = previousFirst;
        }

        size++;
    }

    /**
     * Inserts the given element at the end of this list.
     */
    public void addLast(T element) {
        ListElement newLast = new ListElement(element);

        if(size == 0) {
            last = newLast;
            first = last;
        } else {
            ListElement previousLast = last;
            last = newLast;
            previousLast.next = last;
        }

        size++;
    }

    /**
     * Returns the first element of this list.
     * Returns <code>null</code> if the list is empty.
     */
    public T getFirst() {
        if(size == 0) {
            return null;
        }
        return first.data;
    }

    /**
     * Returns the last element of this list.
     * Returns <code>null</code> if the list is empty.
     */
    public T getLast() {
        if(size == 0) {
            return null;
        }
        return last.data;
    }

    /**
     * Returns the element at the specified position in this list.
     * Returns <code>null</code> if <code>index</code> is out of bounds.
     */
    public T get(int index) {
        if(index < 0 || index > size - 1) {
            return null;
        }

        ListElement searchElement = first;

        // loop until you get the reference of the wanted element and return its data
        while(index-- > 0) {
            searchElement = searchElement.next;
        }

        return (T) searchElement.data;
    }

    /**
     * Removes and returns the first element from this list.
     * Returns <code>null</code> if the list is empty.
     */
    public T removeFirst() {
        if(first != null) {
            ListElement oldFirst = first;
            first = first.next;
            size--;
            return (T)oldFirst.data;
        }
        return null;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns <code>true</code> if this list contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a string representation of this list. The string
     * representation consists of a list of the elements enclosed in
     * square brackets ("[]"). Adjacent elements are separated by the
     * characters ", " (comma and space). Elements are converted to
     * strings by the method toString() inherited from Object.
     */
    public String toString() {
        if(size == 0) {
            return "[]";
        }

        String list = "[";
        ListElement currentElement = first;

        for(int i = 0; i < size; i++) {
            list += currentElement.data.toString() + ", ";
            currentElement = currentElement.next;
        }

        // remove the last ", " in the string
        list = list.substring(0, list.length() - 2);

        list += "]";
        return list;
    }
}
