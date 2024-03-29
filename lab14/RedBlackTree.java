import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */

    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }
        if (r.getItemCount() == 1) {
            // TODO: Replace with code to create a 2 node equivalent
            RBTreeNode<T> tree_root = new RBTreeNode<>(true, r.getItemAt(0));
            tree_root.left = buildRedBlackTree(r.getChildAt(0));
            tree_root.right = buildRedBlackTree(r.getChildAt(1));
            return tree_root;
        } else if (r.getItemCount() == 2) {
            // TODO: Replace with code to create a 3 node equivalent
            RBTreeNode tree_root = new RBTreeNode(true, r.getItemAt(1));
            tree_root.left = new RBTreeNode<>(false, r.getItemAt(0));
            tree_root.left.left = buildRedBlackTree(r.getChildAt(0));
            tree_root.left.right = buildRedBlackTree(r.getChildAt(1));
            tree_root.right = buildRedBlackTree(r.getChildAt(2));
            return tree_root;
        } else {
            RBTreeNode tree_root = new RBTreeNode(true, r.getItemAt(1));
            tree_root.left = new RBTreeNode<>(false, r.getItemAt(0));
            tree_root.right = new RBTreeNode<>(false, r.getItemAt(2));
            tree_root.left.left = buildRedBlackTree(r.getChildAt(0));
            tree_root.left.right = buildRedBlackTree(r.getChildAt(1));
            tree_root.right.left = buildRedBlackTree(r.getChildAt(2));
            tree_root.right.right = buildRedBlackTree(r.getChildAt(3));
            return tree_root;
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        if (node.left == null) return node;
        //RBTreeNode temp = new RBTreeNode(node);
        boolean color_new_parent = node.isBlack;
        RBTreeNode y = node.left; // y refers to node's left child
        node.left = y.right; // y's right child becomes left child of node
        y.right = node;
        y.isBlack = color_new_parent;
        node.isBlack = false;
        return y;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // given node becomes left child of given node's right child
        if (node.right == null) return node;
        boolean color_new_parent = node.isBlack;
        RBTreeNode y = node.right; // y refers to node's left child
        node.right = y.left; // y's right child becomes left child of node
        y.left = node;
        y.isBlack = color_new_parent;
        node.isBlack = false;
        return y;

    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        // handle case C and "Right-leaning" situation.

        //if (node.right == null && node.left.left == null && node.left.right != null) rotateLeft(node.left);
        //else if (node.right.item.compareTo(node.item) > 0) node.left = rotateLeft(node.left);

        //when inserting, node is 1 --> check if node.right
        if (!isRed(node.left) && isRed(node.right)) node = rotateLeft(node);
        // handle case B
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        // handle case A
        if (isRed(node.right) && isRed(node.left)) flipColors(node);

        return node; //fix this return statement 1478
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }


}
