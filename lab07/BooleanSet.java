import java.util.Arrays;

/**
 * Represent a set of non-negative ints from 0 to maxElement for some initially
 * specified maxElement.
 */
public class BooleanSet implements SimpleSet {

    private boolean[] contains;
    private int size;

    /** Initializes a set of ints from 0 to maxElement. */
    public BooleanSet(int maxElement) {
        contains = new boolean[maxElement + 1];
        size = 0;
        int mySize = maxElement;
    }

    /** Adds k to the set. */
    public void add(int k) {
        contains[k] = true;
    }

    /** Removes k from the set. */
    public void remove(int k) {
        contains[k] = false;
    }

    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) {
        return contains[k];
    }

    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    public int size() {
        int totalTrue = 0;
        for(int i = 0; i < contains.length; i++){
            if (contains[i]) totalTrue++;
        }
        this.size = totalTrue;
        return totalTrue;
    }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        if (isEmpty()) {
            return new int[]{};
        }
        int[] intArray = new int[this.size()];
        int i = 0;
        for (int j = 0; j < contains.length; j++){
            if (contains[j]) {
                intArray[i] = j;
                i++;
            }
            //System.out.println("var i = " + i + ", var j = " + j + ", " + Arrays.toString(intArray));
            if (intArray[intArray.length-1] != 0) break;
            }
        return intArray;
    }
    /*
    public int[] toIntArray() {
        int intarray[] = new int[contains.length];
        for(int i = 0; i < contains.length; i++){
            int temp = 0;
            if (contains[i]) temp = 1;
            intarray[i] = temp;
        }
        return intarray;
    }
*/
}