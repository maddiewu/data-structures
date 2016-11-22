import java.util.*;

public class BST {
    BSTNode root;

    static Iterator iterator;

    public BST(LinkedList list) {
        root = linkedListToTree(list.iterator(), list.size());
    }

    private BSTNode linkedListToTree (Iterator iter, int n) {
        if (!iter.hasNext()) {
            return null;
        }
        iterator = iter;
        return sortedList(0, n-1);
    }

    private BSTNode sortedList(int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end)/2;
        BSTNode leftChild = sortedList(start, mid-1);
        BSTNode root = new BSTNode(iterator.next());
        root.left = leftChild;
        BSTNode rightChild = sortedList(mid+1, end);
        root.right = rightChild;
        return root;
    }

    /*Build a balanced binary search tree from a sorted linked list.
    private BSTNode linkedListToTree (Iterator iter, int n) {
        if (!iter.hasNext()) {
            return null;
        }
        iterator = iter;
        return linkedListToTree(0, n - 1);
    }

    private BSTNode linkedListToTree(int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        BSTNode root = new BSTNode(iterator.next());
        BSTNode leftC = linkedListToTree(start, mid - 1);
        root.left = leftC;
        BSTNode rightC= linkedListToTree(mid + 1, end);
        root.right = rightC;
        return root;
    }*/

    /**
     * Prints the tree to the console.
     */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    /**
     * Node for BST.
     */
    static class BSTNode {

        BSTNode(Object next) {
            item = next;
        }

        /** Item. */
        Object item;

        /** Left child. */
        BSTNode left;

        /** Right child. */
        BSTNode right;
    }
}
