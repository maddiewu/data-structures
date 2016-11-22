import java.util.ArrayList;
import java.util.jar.Pack200;

public class BinaryTree {

    private TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode t) {
        root = t;
    }

    public TreeNode getRoot() {
        return root;
    }

    // Print the values in the tree in preorder: root value first,
    // then values in the left subtree (in preorder), then values
    // in the right subtree (in preorder).
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    // Print the values in the tree in inorder: values in the left
    // subtree first (in inorder), then the root value, then values
    // in the right subtree (in inorder).
    public void printInorder() {
        if (root == null) {
            System.out.println("(emptytree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    public void fillSampleTree1() {
        TreeNode temp = new TreeNode("a");
        root = new TreeNode("a", temp, temp);
    }

    public void fillSampleTree2() {
        root = new TreeNode("a", new TreeNode("b", new TreeNode("d",
                new TreeNode("e"), new TreeNode("f")), null), new TreeNode("c"));
    }

    public void print() {
        if (root != null) {
            root.print(0);
        }
    }

    private ArrayList alreadySeen;

    public boolean check() {
        alreadySeen = new ArrayList();
        try {
            isOK(root);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private void isOK(TreeNode t) throws IllegalStateException {
        if (t == null) {
            return;
        }
        for (Object test : alreadySeen) {
            if (test == t) {
               throw new IllegalStateException("node was already seen");
            }
        }
        alreadySeen.add(t);
        isOK(t.left);
        isOK(t.right);
    }

    public static BinaryTree fibTree(int n) {
        BinaryTree result = new BinaryTree();
        if (n==0) {
            result.root = new TreeNode(0);
        } else if (n==1) {
            result.root = new TreeNode(1);
        } else {
            result.root = new TreeNode(n, fibTree(n-1).root, fibTree(n-2).root);
            result.root.item = (int) result.root.left.item + (int) result.root.right.item;
        }
        return result;
    }

    public static BinaryTree exprTree(String s) {
        BinaryTree result = new BinaryTree();
        result.root = result.exprTreeHelper(s);
        return result;
    }

// Return the tree corresponding to the given arithmetic expression.
// The expression is legal, fully parenthesized, contains no blanks,
// and involves only the operations + and *.
    private TreeNode exprTreeHelper(String expr) {
        if (expr.charAt(0) != '(') {
            return new TreeNode(expr.substring(0));
        } else {
            // expr is a parenthesized expression.
            // Strip off the beginning and ending parentheses,
            // find the main operator (an occurrence of + or * not nested
            // in parentheses, and construct the two subtrees.

            int nesting = 0;
            int opPos = 0;
            String operands = "+-/*";
            for (int k = 1; k < expr.length() - 1; k++) {
                if (expr.charAt(k) == '(') {
                    nesting++;
                }
                if (expr.charAt(k) == ')') {
                    nesting--;
                }
                if (nesting == 0) {
                    for (int i = 0; i < operands.length(); i++) {
                        if (expr.charAt(k) == operands.charAt(i)) {
                            opPos = k;
                            break;
                        }
                    }
                }
            }

            String opnd1 = expr.substring(1, opPos);
            String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
            String op = expr.substring(opPos, opPos + 1);
            System.out.println("expression = " + expr);
            System.out.println("operand 1  = " + opnd1);
            System.out.println("operator   = " + op);
            System.out.println("operand 2  = " + opnd2);
            System.out.println();

            // you fill this in
           return new TreeNode(op, exprTree(opnd1).root, exprTree(opnd2).root);
        }
    }

    public void optimize(TreeNode n) {
        String start = n.item.toString();

        if (isNumber(start)) {
            return;
        }
        if (isNumber(n.left.item) && isNumber(n.right.item)) {
            if ((n.item.toString()).equals("+")) {
                n.item = Integer.parseInt(n.left.item.toString()) + Integer.parseInt(n.right.item.toString());
            }
            if ((n.item.toString()).equals("*")) {
                n.item = Integer.parseInt(n.left.item.toString()) * Integer.parseInt(n.right.item.toString());
            }
            n.item = n.item.toString();
            n.left = null;
            n.right = null;

        } else {
            if (isOp(n.left.item)) {
                optimize(n.left);
            }
            if (isOp(n.right.item)) {
                optimize(n.right);
            }
        }
    }

    public void optimize() {
        if (root != null) {
            optimize(root);
        }

    }

    public boolean isNumber(Object o) {
        return (!isLetter(o) && !isOp(o));
    }

    public boolean isLetter(Object o) {
        String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < ALPHABET.length(); i++) {
            if ((o.toString()).equals(ALPHABET.substring(i, i+1))) {
                return true;
            }
        }
        return false;
    }

    public boolean isOp(Object o) {
        String OPERANDS = "+-/*";
        for (int i = 0; i < OPERANDS.length(); i++) {
            if ((o.toString()).equals(OPERANDS.substring(i, i+1))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /*BinaryTree t;
        t = new BinaryTree();
        print(t, "the empty tree");
        t.fillSampleTree1();
        print(t, "sample tree 1");
        t.fillSampleTree2();
        print(t, "sample tree 2");
        t.print();
        System.out.println(t.check());
        fibTree(5).print();*/
        BinaryTree a = exprTree("((a+(5*(a+b)))+(6*5))");
        a.optimize();
        a.print();

    }

    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    public static class TreeNode {

        public Object item;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(Object obj) {
            item = obj;
            left = right = null;
        }

        public TreeNode(Object obj, TreeNode left, TreeNode right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        public Object getItem() {
            return item;
        }

        //In TreeNode
        private static final String indent1 = "    ";

        private void print(int indent) {
            if (right != null) {
                right.print(indent + 1);
            }
            println(item, indent);
            if ((left != null)) {
                left.print(indent + 1);
            }
        }

        private static void println(Object obj, int indent) {
            for (int k=0; k<indent; k++) {
                System.out.print(indent1);
            }
            System.out.println(obj);
        }

    }
}
