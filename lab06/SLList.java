/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public int item;
        public IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntListNode sentinel;
    private int size;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new IntListNode(42, null);
        sentinel.next = sentinel;
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntListNode(42, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLList slList = (SLList) o;
        if (size != slList.size) return false;

        IntListNode l1 = sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel && l2 != slList.sentinel) {
            if (!l1.equals(l2)) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return true;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    /** Returns an SLList consisting of the given values. */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /** Return the value at the given index. */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Adds x to the list at the specified index. */
    public void add(int index, int x) {
        IntListNode p = sentinel.next;
        if (index >= size){
            index = size;
        }
    // 1 2 3 4 5, add 2 at index1
        if (index == 0) {
            addFirst(x);
        }
        else {
            while (index > 1) {
                p = p.next;
                index -= 1;
            }
            p.next = new IntListNode(x, p.next);
            size+=1;
        }
    }

    /** Destructively reverses this list.
    public void reverse() {
        reverseHelper(sentinel.next);
    }
    /*
    private IntListNode reverseHelper(IntListNode input){

        if(input.next == sentinel){
            sentinel.next = input;
            return input;
        }
         //.next's, assignment statement, use input
       input.next.next = reverseHelper();

        return input;
    }*/
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
    public void reverse() {
        reverseHelper2(sentinel, sentinel.next);
    }
    private IntListNode reverseHelper2(IntListNode revC, IntListNode revP){
        if (revP == sentinel) {
            revP.next = revC;
            return revP;
        }
        IntListNode revNew = revP.next;
        revP.next = revC;
        return reverseHelper2(revP, revNew);
    }
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
}
// input.next = reverseHelper(input prev, input becomes

//s-1->2->3->4->5->s  //
// p initially is the sentinel
//p.next is 1-2-3-4-5
// add last element of p.next to p, take last element out, and then call that function on p.next
// remove() + addFirst
// removeLast()
/*
IntListNode p = sentinel.next;
p = sentinel
addLast(p.next.size - 1)
p = p.next
while pseudo size sent.next != 0
new = IntNodelist(sent.nextOG.remove.last, reverse(sent.nextOG)
return l[-1] + reverse(l[:-1])
 */