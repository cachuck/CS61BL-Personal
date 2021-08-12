package deque;
import org.junit.Test;
import static org.junit.Assert.*;

/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    //public static Deque<Integer> lld = new LinkedListDeque<Integer>();

    @Test
    public void testaddFirst() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        lld.addFirst(3);
        assertEquals((Integer)3, lld.get(0));
        lld.addFirst(8);
        lld.addFirst(73);
        assertEquals((Integer)73, lld.get(0));
    }

    @Test
    public void testaddLast() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        lld.addLast(83);
        int last = lld.size() - 1;
        //System.out.println("size = " + lld.size() + ", last = " + last + ", get(last) = " + lld.get(last) + "; we wanted 83");
        assertEquals((Integer)83, lld.get(last));
        lld.addLast(77);
        last = lld.size() - 1;
        //System.out.println("size = " + lld.size() + ", last = " + last + ", get(last) = " + lld.get(last) + "; we wanted 77");
        assertEquals((Integer)77, lld.get(last));
        lld.addLast(8);
        last = lld.size() - 1;
        //System.out.println("size = " + lld.size() + ", last = " + last + ", get(last) = " + lld.get(last) + "; we wanted 8");
        assertEquals((Integer)8, lld.get(last));
    }

    @Test
    public void testisEmpty(){
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertTrue(lld.isEmpty());
        lld.addFirst(3);
        lld.addFirst(5);
        assertFalse(lld.isEmpty());
    }

    @Test
    public void testsize() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertEquals(0, lld.size());
        lld.addFirst(1);
        assertEquals(1, lld.size());
    }

    @Test
    public void testprintDeque() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        String temp = "1 2 3 \n";
        lld.addFirst(3);
        lld.addFirst(2);
        lld.addFirst(1);
        lld.printDeque();
        System.out.print(temp);
    }

    @Test
    public void testremoveFirst() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertNull(lld.removeFirst()); //removeFirst returns null if cannot remove first w/ empty array
        lld.addFirst(2);
        assertEquals((Integer)2, lld.removeFirst());
        lld.addFirst(8);
        lld.addFirst(5);
        lld.addFirst(10);
        lld.addFirst(13);
        assertEquals((Integer)13, lld.removeFirst());
        assertEquals((Integer)10, lld.removeFirst());
        assertEquals((Integer)5, lld.removeFirst());
        assertEquals((Integer)8, lld.removeFirst());
        assertNull(lld.removeFirst());
        assertNull(lld.removeFirst());
        assertNull(lld.removeFirst());
        System.out.println(lld.size());
    }

    @Test
    public void testremoveLast() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertNull(lld.removeLast());
        lld.addLast(8);
        assertEquals((Integer)8, lld.removeLast());
        lld.addFirst(2);
        lld.addFirst(4);
        lld.addLast(10);
        lld.addLast(9);
        assertEquals((Integer)9, lld.removeLast());
        assertEquals((Integer)10, lld.removeLast());
        assertEquals((Integer)2, lld.removeLast());
        assertEquals((Integer)4, lld.removeLast());
        assertNull(lld.removeLast());
        assertNull(lld.removeLast());
        assertNull(lld.removeLast());
        System.out.println(lld.size());
    }

    @Test
    public void testget() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertNull(lld.get(0));
        assertNull(lld.get(738));
        lld.addLast(8);
        assertEquals((Integer)8, lld.get(0));
        lld.addFirst(2);
        assertEquals((Integer)8, lld.get(1));
        lld.addFirst(4);
        assertEquals((Integer)8, lld.get(2));
        assertEquals((Integer)4, lld.get(0));
        assertEquals((Integer)2, lld.get(1));
        lld.addLast(9);
        lld.addLast(10);
        assertEquals((Integer)10, lld.get(4));
    }

    @Test
    public void testgetRecursive() {
        Deque<Integer> lld = new LinkedListDeque<Integer>();
        assertNull(((LinkedListDeque<Integer>) lld).getRecursive(0));
        assertNull(((LinkedListDeque<Integer>) lld).getRecursive(15));
        lld.addLast(8);
        assertEquals((Integer)8, ((LinkedListDeque<Integer>) lld).getRecursive(0));
        lld.addFirst(2);
        assertEquals((Integer)8, ((LinkedListDeque<Integer>) lld).getRecursive(1));
        lld.addFirst(4);
        assertEquals((Integer)8, ((LinkedListDeque<Integer>) lld).getRecursive(2));
        assertEquals((Integer)4, ((LinkedListDeque<Integer>) lld).getRecursive(0));
        assertEquals((Integer)2, ((LinkedListDeque<Integer>) lld).getRecursive(1));
        lld.addLast(9);
        lld.addLast(10);
        assertEquals((Integer)10, ((LinkedListDeque<Integer>) lld).getRecursive(4));
    }

    @Test
    public void testEquals() {


    }

}
