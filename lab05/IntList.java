/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        if (position < 0) throw new IllegalArgumentException("Error: Index out of bounds :(");
    //  if (position == 0) return this.item;
        IntList a = this;
        for (int i = 0; i < position; i++) {
            if (a.next != null) {
                a = a.next;
            }
            else throw new IllegalArgumentException("Error: Index out of bounds :(");
        }
        return a.item;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        String toBePrinted = new String("");
        IntList b = this;
        while (true){
            if (b.next == null){
                toBePrinted = toBePrinted + b.item;
                break;
            }
            else{
                toBePrinted = toBePrinted + b.item + " ";
                b = b.next;
            }
        }
        return toBePrinted;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;

        IntList firstLst = this;

        if(firstLst.toString().equals(otherLst.toString())){
            result = true;
        }
        return result;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        IntList a = this;
        while(a.next != null){
            a = a.next;
        }
        a.next = new IntList(value, null);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList a = this;
        int small = a.item;
        while (a.next != null) {
            if (a.item < small) {
                small = a.item;
            }
            a = a.next;
        }
        if (a.item < small) {
            small = a.item;
        }
        return small;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        int result = 0;
        IntList a = this;
        while(a.next != null){
            result += a.item*a.item;
            a = a.next;
        }
        result += a.item*a.item;
        return result;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */

    public static IntList dcatenate(IntList A, IntList B) {

        if (A == null) return B;
        if (B == null) return A;

        String myStr = B.toString();
        int myLen = (myStr.length() / 2) + 1;
        int i = 0;
        while (i < myLen) {
            int addition = B.get(i);
            A.add(addition);
            i++;
        }
        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {

         if (A == null) return B;
         if (B == null) return A;

         int myLen = (A.toString().length() / 2) + 1;
         IntList newA = new IntList(A.get(0), null);
         int i = 1;
         while (i < myLen) {
             newA.add(A.get(i));
             i++;
         }
         return dcatenate(newA, B);
     }
}