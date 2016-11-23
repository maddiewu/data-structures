/*A doubly linked list. */

public class DLList {
    DLNode sentinel;
    int size;

    public class DLNode {
        Object item;
        DLNode prev, next;

        public DLNode(Object item, DLNode prev, DLNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Construct a new DLList with a sentinel that points to itself.
     */
    public DLList() {
        sentinel = new DLNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Insert into the end of this list
     * @param o Object to insert
     */
    public void insertBack(Object o) {
        DLNode n = new DLNode(o, sentinel.prev, sentinel);
        n.next.prev = n;
        n.prev.next = n;
        size++;
    }

    /**
     * Get the value at position pos. If the position does not exist, return null (the item of
     * the sentinel).
     * @param position to get from
     * @return the Object at the position in the list.
     */
    public Object get(int position) {
        DLNode curr = sentinel.next;
        while (position > 0 && curr != sentinel) {
            curr = curr.next;
            position--;
        }
        return curr.item;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("DLList(");
        DLNode curr = sentinel.next;
        while (curr != sentinel) {
            s.append(curr.item.toString());
            if (curr.next != sentinel) s.append(", ");
            curr = curr.next;
        }
        s.append(')');
        return s.toString();
    }

    /**
     * Insert a new node into the DLList.
     * @param o Object to insert
     * @param position position to insert into. If position exceeds the size of the list, insert into
     *            the end of the list.
     */
    public void insert(Object o, int position) {
        DLNode n = this.sentinel;
        if (position > size) {
            insertBack(o);
        }
        else {
            for (int i=0; i<position; i++) {
                n = n.next;
            }
            size++;
            DLNode add = new DLNode(o, n, n.next);
            add.prev.next = add;
            add.next.prev = add;
        }
    }

    /**
     * Insert into the front of this list. You should can do this with a single call to insert().
     * @param o Object to insert
     */
    public void insertFront(Object o) {
        insert(o, 0);
    }

    /**
     * Remove all copies of Object o in this list
     * @param o Object to remove
     */
    public void remove(Object o) {
        DLNode removeObject = this.sentinel;
        int originalSize=size;
        for (int i=0; i<=originalSize; i++) {
            if (removeObject.item==o) {
                removeObject.prev.next = removeObject.next;
                removeObject.next.prev = removeObject.prev;
                size--;
            }
            removeObject = removeObject.next;
        }
    }

    /**
     * Remove a DLNode from this list. Does not error-check to make sure that the node actually
     * belongs to this list.
     * @param n DLNode to remove
     */
    public void remove(DLNode n) {
        DLNode removeNode = this.sentinel;
        int originalSize=size;
        for (int i=0; i<originalSize; i++) {
            if (removeNode==n) {
                removeNode.next.prev = removeNode.prev;
                removeNode.prev.next = removeNode.next;
                size--;
            }
            removeNode = removeNode.next;
        }
    }

    /**
     * Duplicate each node in this linked list destructively.
     */
    public void doubleInPlace() {
        DLNode doubleList= this.sentinel;
        int newSize = size * 2;
        doubleList=doubleList.next;
        for (int i=0; i<newSize; i+=2) {
            insert(doubleList.item, i+1);
            doubleList=doubleList.next.next;
        }
    }

    /**
     * Reverse the order of this list destructively.
     */
    public void reverse() {
        DLNode originalList = this.sentinel;
        DLNode newList = new DLNode(null, null, originalList.prev);
        DLNode temp=originalList.next;
        int originalSize = size;
        for (int i=0; i<=(originalSize); i++) {
            originalList.next=originalList.prev;
            originalList.prev=originalList.next;
            originalList=originalList.next;
        }
        originalList=newList;
        sentinel=originalList;
    }

}
