import java.util.ArrayList;
import java.util.NoSuchElementException;

// PriorityItem class has a natural ordering that is inconsistent with equals
/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
        //this.size = 0;
        size = 0;
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return (2 * index);
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return (2 * index + 1);
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return (index / 2);

    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (contents.get(index1) == null) return index2;
        if (contents.get(index2) == null) return index1;
        if (contents.get(index1).compareTo(contents.get(index2)) < 0) {
            return index1;
        }
        else {
            return index2;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        if (getElement(index) == null) return;
        if (index == 1) return;
        if (getElement(index).compareTo(getElement(getParentOf(index))) > 0) {
            return;
        } else {
            swap(index, getParentOf(index));
            bubbleUp(getParentOf(index));
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        //if (contents.get(index) == null ||  contents.get(getLeftOf(index)) == null || contents.get(getRightOf(index)) == null) return;
        // if there are no children of node or if node is smaller than or equal to the smallest of the children we want to stop
        // if (
        // E minNode = getElement(min(getLeftOf(index), getRightOf(index)));
        // if (!contains
        while (getElement(index) != null && getElement(getLeftOf(index)) != null && getElement(getRightOf(index)) != null) {
            int smallerChild = min(getLeftOf(index), getRightOf(index));
            if (min(smallerChild, index) == index) break;
            swap(smallerChild, index);
            index = smallerChild;
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return this.size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contents.contains(element)) throw new IllegalArgumentException();
        else {
            size++;
            setElement(size, element);
            bubbleUp(size);
        }

    }
        //TODO: USE bubbleUp and/or bubbleDown (spec sheet says)

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        E returnVal = findMin();
        swap(1, size());
        contents.remove(size());
        size--;
        bubbleDown(1);
        return returnVal;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (!contents.contains(element)) throw new NoSuchElementException();
        else {
            int existingElementIndex = contents.indexOf(element);
            setElement(existingElementIndex, element);
            bubbleUp(existingElementIndex);
            bubbleDown(existingElementIndex);
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        return contents.contains(element);
    }
}
