import java.util.EmptyStackException;

/**
 * Created by worldyn on 28/01/17.
 */
public class StackImplementation<T> implements Stack<T> {
    private LinkedList<T> storageList;

    public StackImplementation() {
        storageList = new LinkedList<>();
    }

    public void push(T newElement) {
        storageList.addFirst(newElement);
    }

    public T pop() throws Exception {
        if(!isEmpty()) {
            return storageList.removeFirst();
        }

        throw new EmptyStackException();
    }

    public T top() throws Exception {
        if(!isEmpty()) {
            return storageList.getFirst();
        }

        throw new EmptyStackException();
    }

    public int size() {
        return storageList.size();
    }

    public boolean isEmpty() {
        return storageList.size() == 0;
    }
}
