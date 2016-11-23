/**
 * Created by madelinewu on 10/3/16.
 */
public class TwoStackArray<AnyType> {

    AnyType[] arr;
    int index1 = 0;
    int index2;

    public TwoStackArray(int x) {
        arr = (AnyType[]) new Object[x];
        index2 = arr.length - 1;
    }

    public void push1(AnyType item) {
        if (index1 != index2) {
            arr[index1] = item;
            index1++;
        } else {
            throw new StackOverflowError("Array is at full capacity.");
        }
    }

    public AnyType pop1() {
        index1--;
        return arr[index1 + 1];
    }

    public AnyType peek1() {
        return arr[index1];
    }

    public boolean empty1() {
        return (index1 == 0);
    }

    public void push2(AnyType item) {
        if (index1 != index2) {
            arr[index2] = item;
            index2--;
        } else {
            throw new StackOverflowError("Array is at full capacity.");
        }
    }

    public AnyType pop2() {
        index2++;
        return arr[index2 - 1];
    }

    public AnyType peek2() {
        return arr[index2];
    }

    public boolean empty2() {
        return ((arr.length - 1) == index2);
    }

} //end class
