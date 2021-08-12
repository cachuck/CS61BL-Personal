package deque;

import edu.princeton.cs.algs4.StdOut;

public class LinkedListDeque<T> implements Deque<T> {
    private static class Node<T> {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Node() {
            item = null;
            next = null;
            prev = null;
        }

        /*
        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // compares identical object (itself)
            if (o == null || getClass() != o.getClass()) return false;
            Node that = (Node) o;
            return item == that.item;
        }
        */

        @Override
        public String toString() {
            return item + "";
        }
    }

    private Node sentinel;
    private int size;

    // LinkedList constructor.
    public LinkedListDeque() { //constructs Deque of size 0
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        // s > 10
        sentinel.next = new Node(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }
    @Override
    public void addLast(T item) {
        sentinel.prev.next = new Node(item, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
        size++;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(this.get(i) + " ");
        }
        System.out.println("");
    }
    @Override
    public T removeFirst() {
        T temp = (T)sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        if (size > 0) size--;
        return temp;
    }
    @Override
    public T removeLast() {
        T temp = (T)sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        if (size > 0) size--;
        return temp;
    }
    @Override
    public T get(int index) {
        Node p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return (T)p.item;
    }
    @Override
    public boolean equals(Object O) {
        if (O == null) return false;
        if (!(O instanceof Deque)) return false;
        Deque A = this;
        Deque DLList = (Deque) O;
        if (A == DLList) return true;
        if (A.size() != DLList.size()) return false;
        for (int indexes = A.size() - 1; indexes >= 0; indexes--) {
            if (!(A.get(indexes).equals(DLList.get(indexes)))) return false;
        }
        return true;
    }
    /*
    public boolean equals(Object O) {
        Node l1 = sentinel.next;
        LinkedListDeque dllist = (LinkedListDeque) O;
        Node l2 = dllist.sentinel.next;

        if (l1 == null && l2 == null) return true;
        if (l1.item != l2.item) return false;
        return l1.next.equals(l2.next);
    }
    */

    public T getRecursive(int index){
        if (sentinel.next == sentinel) return null;
        Node pointer = sentinel.next;
        return helperRecur(pointer, index);
    }

    private T helperRecur(Node p, int index) {
        if (index == 0) return (T) p.item;
        else return helperRecur(p.next, index-1);
    }
}
/*
if
else
p = .next
getRecursive(
when we have orig index
Node p = sentinel
if we call get helper p = p.next


 */
/*
in helper function:
p.node = sentinel, / else return p = p.next

recursive func:
if index == 0 return p.item, else recursive get with (index--) on the helper return

 */