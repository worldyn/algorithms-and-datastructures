/**
 * @author Adam Jacobs
 * @version Jan 2017
 *
 * A self-made stack interface in case you dont like the java developers
 */
public interface Stack<O> {

    /**
     * Adds the element o to the top of the stack
     *
     * @param o the element to be added to the top
     */
    void push(O o);

    /**
     * Removes and returns the top element in the stack,
     * that is the element that was last added to the stack.
     * An error occurs if the stack is empty.
     *
     * @return the element that was removed
     */
    O pop() throws Exception;

    /**
     * Returns the top element in the stack without removing it.
     * An error occurs if the stack is empty.
     *
     * @return the top element
     */
    O top() throws Exception;

    /**
     * Returns the number of elements in the stack.
     */
    int size();

    /**
     *  Indicates whether the stack is empty.
     * @return true is the stack is empty, false otherwise.
     */
    boolean isEmpty();

}

