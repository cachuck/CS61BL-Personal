import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;

public class BST<T> {

    BSTNode<T> root;
/*
  ____ ____ _____                   _
 | __ ) ___|_   _|  _ __   ___   __| | ___
 |  _ \___ \ | |   | '_ \ / _ \ / _` |/ _ \
 | |_) |__) || |   | | | | (_) | (_| |  __/
 |____/____/ |_|   |_| |_|\___/ \__,_|\___|
 */
    public BST(LinkedList<T> list) {
        root = sortedIterToTree(list.iterator(), list.size());
    }

    /* Returns the root node of a BST (Binary Search Tree) built from the given
       iterator ITER  of N items. ITER will output the items in sorted order,
       and ITER will contain objects that will be the item of each BSTNode. */
    private BSTNode<T> sortedIterToTree(Iterator<T> iter, int N) {
        List<T> myArray = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            myArray.add(iter.next());
        }
        return iterHelper(myArray);
    }
    private BSTNode<T> iterHelper(List<T> lst){
        if (lst.size() == 0) {
            return null;
        }
        else {
            int pos = lst.size()/2;
            T tip = lst.get(pos);
            BSTNode<T> res = new BSTNode(tip, iterHelper(lst.subList(0,pos)), iterHelper(lst.subList(pos + 1, lst.size())));
            return res;
        }
    }


    /* Prints the tree represented by ROOT. */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode<T> node, int d) {
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

    class BSTNode<T> {
        T item;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T item) {
            this.item = item;
        }
        BSTNode(T item, BSTNode<T> left, BSTNode<T> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

    }
}