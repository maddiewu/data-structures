/**
COMS 3134 Homework 2
 Madeline Wu (mcw2175)
 October 7, 2016

 This class creates a queue from two stacks using MyStack.
 It implements the given interface MyQueue.
 */
public class TwoStackQueue<T> implements MyQueue<T>{

    /*Declare and initialize two empty stacks S1 and S2. */
    MyStack<T> S1 = new MyStack();
    MyStack<T> S2 = new MyStack();

    /*Add an item to the top of S1. */
    @Override
    public void enqueue(T x) {
        S1.push(x);
    }

    /*If S2 is empty, move all items from S1 onto S2,
    * then return the top element from S2. */
    @Override
    public T dequeue() {
        if (S2.empty()) {
            while (!S1.empty()) {
                S2.push(S1.pop());
            }
        }
        return S2.pop();
    }

    /*Check if the queue is empty. */
    @Override
    public boolean isEmpty() {
        return (S1.empty() && S2.empty());
    }

    /*Returns the size of the queue. */
    @Override
    public int size() {
        return S1.size() + S2.size();
    }

} //end class
