import java.util.LinkedList;
import java.util.Objects;

/**
 COMS 3134 Homework 2
 Madeline Wu (mcw2175)
 October 7, 2016

 A generic stack class that is used by SymbolBalance and TwoStackQueue.
 */
public class MyStack<T> {

    /*Use a linked list to store the items.
    * The front of the linked list is like the bottom of the stack
    * because items added first are eventually popped off last. */
    LinkedList<T> stack = new LinkedList<T>();

    /*Return but does not remove the item on top of the stack. */
    public T peek() {
        return stack.getLast();
    }

    /*Remove the top item from the stack (the last item in the linked list). */
    public T pop() {
        return stack.removeLast();
    }

    /*Adds and returns an item to the end of the linked list/ top of the stack. */
    public T push(T item) {
        stack.addLast(item);
        return item;
    }

    /*Returns true if the stack is empty. */
    public boolean empty() {
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    /*Searches the stack for an item and returns the index.
    * Index 1 is like the top of the stack. Returns -1 if item isn't in the stack. */
    public int search(Object o) {
        int position = stack.size();
        int index = 0;
        for (T x : stack) {
            if (Objects.equals(o, x)) {
                return position - index;
            }
            index++;
        }
        return -1;
    }

    public int size() {
        return stack.size();
    }

} //end class
