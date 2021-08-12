package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    @Test
    public void testaddFirst() {
        Deque<Integer> ad = new ArrayDeque<Integer>();

        ad.addFirst(3);
        //ad.printDeque();
        assertEquals((Integer)3, ad.get(0));
        ad.addFirst(8);
        //ad.printDeque();
        assertEquals((Integer)8, ad.get(0));
        ad.addFirst(73);
        assertEquals((Integer)73, ad.get(0));
        ad.addFirst(5);
        assertEquals((Integer)5, ad.get(0));
        ad.addFirst(482);
        assertEquals((Integer)482, ad.get(0));
        ad.addFirst(25);
        //ad.printDeque();
        assertEquals((Integer)25, ad.get(0));
        ad.addFirst(22);
        assertEquals((Integer)22, ad.get(0));
        ad.addFirst(66);
        assertEquals((Integer)66, ad.get(0));
        ad.addFirst(989);
        assertEquals((Integer)989, ad.get(0));
        ad.addFirst(272);
        assertEquals((Integer)272, ad.get(0));
        /*ad.addFirst(76534);
        assertEquals((Integer)76534, ad.get(13));
        ad.addFirst(43334543);
        assertEquals((Integer)43334543, ad.get(12));
        ad.addFirst(56734);
        assertEquals((Integer)56734, ad.get(11));*/
    }

    @Test
    public void testaddLast() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.addLast(3);
        //ad.printDeque();
        assertEquals((Integer)3, ad.get(0));
        ad.addLast(8);
        //ad.printDeque();
        assertEquals((Integer)8, ad.get(1));
        ad.addLast(73);
        //ad.printDeque();
        assertEquals((Integer)73, ad.get(2));
        ad.addLast(5);
        //ad.printDeque();
        assertEquals((Integer)5, ad.get(3));
        ad.addLast(482);
        assertEquals((Integer)482, ad.get(4));
        ad.addLast(25);
        assertEquals((Integer)25, ad.get(5));
        ad.addLast(22);
        assertEquals((Integer)22, ad.get(6));
        ad.addLast(66);
        //ad.printDeque();
        assertEquals((Integer)66, ad.get(7));
        ad.addLast(989);
        //ad.printDeque();
        assertEquals((Integer)989, ad.get(8));
        ad.addLast(272);
        //ad.printDeque();
        assertEquals((Integer)272, ad.get(9));
        ad.addLast(76534);
        //ad.printDeque();
        assertEquals((Integer)76534, ad.get(10));
        ad.addLast(43334543);
        //ad.printDeque();
        assertEquals((Integer)43334543, ad.get(11));
        ad.addLast(56734);
        assertEquals((Integer)56734, ad.get(12));
        ad.addLast(3);
        assertEquals((Integer)3, ad.get(13));
        ad.addLast(8);
        assertEquals((Integer)8, ad.get(14));
        ad.addLast(73);
        assertEquals((Integer)73, ad.get(15));
        ad.addLast(5);
        assertEquals((Integer)5, ad.get(16));
        ad.addLast(482);
        assertEquals((Integer)482, ad.get(17));
        ad.addLast(25);
        assertEquals((Integer)25, ad.get(18));
        ad.addLast(22);
        assertEquals((Integer)22, ad.get(19));
        ad.addLast(66);
        //ad.printDeque();
        assertEquals((Integer)66, ad.get(20));
        ad.addLast(989);
        assertEquals((Integer)989, ad.get(21));
        ad.addLast(272);
        assertEquals((Integer)272, ad.get(22));
        ad.addLast(76534);
        assertEquals((Integer)76534, ad.get(23));
        ad.addLast(43334543);
        assertEquals((Integer)43334543, ad.get(24));
        ad.addLast(56734);
        assertEquals((Integer)56734, ad.get(25));
    }

    @Test
    public void testisEmpty(){
        Deque<Integer> ad = new ArrayDeque<Integer>();
        assertTrue(ad.isEmpty());
        ad.addFirst(3);
        ad.addFirst(5);
        assertFalse(ad.isEmpty());
        ad.removeFirst();
        ad.removeFirst();
        assertTrue(ad.isEmpty());
        assertTrue(ad.isEmpty());
    }

    @Test
    public void testsize() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        assertEquals(0, ad.size());
        ad.addFirst(1);
        assertEquals(1, ad.size());
        ad.addFirst(1);
        ad.addFirst(1);
        assertEquals(3, ad.size());
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        assertEquals(0, ad.size());
        ad.removeFirst();
        ad.removeFirst();
        assertEquals(0, ad.size());
        ad = new ArrayDeque<Integer>();
        assertEquals(0, ad.size());
        ad.addLast(1);
        assertEquals(1, ad.size());
        ad.addLast(1);
        ad.addLast(1);
        assertEquals(3, ad.size());
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertEquals(0, ad.size());
        ad.removeLast();
        ad.removeLast();
        assertEquals(0, ad.size());
    }

    @Test
    public void testprintDeque() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.addFirst(7);
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.printDeque();
    }

    @Test
    public void testremoveFirst() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        assertNull(ad.removeFirst()); //removeFirst returns null if cannot remove first w/ empty array
        ad.addFirst(2);
        assertEquals((Integer)2, ad.removeFirst());
        assertNull(ad.removeFirst());
    }

    @Test
    public void testremoveLast() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        assertNull(ad.removeLast()); //removeLast returns null if cannot remove last w/ empty array
        ad.addLast(8);
        ad.addLast(5);
        //ad.printDeque();
        assertEquals((Integer)5, ad.removeLast());
        //ad.printDeque();
        assertEquals((Integer)8, ad.removeLast());
        //ad.printDeque();
        assertNull(ad.removeLast());
        assertNull(ad.removeLast());
        assertNull(ad.removeLast());
        assertNull(ad.removeLast());
        assertNull(ad.removeLast());
        //System.out.println("DEBUG TML, size = " + ad.size());
    }

    @Test
    public void testget() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.addFirst(1);
        assertEquals((Integer)1, ad.get(0));
        assertNull(ad.get(10));
        assertNull(ad.get(7));
        assertNull(ad.get(-5));
        ad.addLast(10);
        assertEquals((Integer)10, ad.get(1));
    }

    @Test
    public void testresize() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.addFirst(3);
        //assertEquals((Integer)3, ad.get(7));
        ad.addFirst(8);
        //assertEquals((Integer)8, ad.get(6));
        ad.addFirst(73);
        //assertEquals((Integer)73, ad.get(5));
        ad.addFirst(5);
        //assertEquals((Integer)5, ad.get(4));
        ad.addFirst(482);
        //assertEquals((Integer)482, ad.get(3));
        ad.addFirst(25);
        //System.out.println(ad.size());
        //ad.printDeque();
        //assertEquals((Integer)25, ad.get(2));
        ad.addFirst(22);
        //System.out.println(ad.size());
        //ad.printDeque();
        //assertEquals((Integer)22, ad.get(1));
        ad.addFirst(66);
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.addFirst(989);
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeLast();
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.addLast(10);
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.addFirst(39);
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.addLast(15);
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeFirst();
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeFirst();
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeFirst();
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeLast();
        //System.out.println(ad.size());
        //ad.printDeque();
        ad.removeLast();
        //System.out.println(ad.size());
        //ad.printDeque();
    }

    @Test
    public void testdownsize(){
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        ad.addFirst(3);
        //ad.printDeque();
        ad.addFirst(5);
        ad.addFirst(5);
        //ad.printDeque();
        ad.addFirst(5);
        ad.addFirst(5);
        //ad.printDeque();
        ad.addFirst(5);
        ad.addFirst(5);
        //ad.printDeque();
        ad.removeFirst();
        ad.removeFirst();
        //ad.printDeque();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        //ad.printDeque();
        //System.out.println("here i am!");
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        //ad.printDeque();
        //System.out.println("here i am!");
        ad.removeFirst();
        //System.out.println("did i reach?");
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        //ad.printDeque();
        //System.out.println("here i am!");
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        //ad.printDeque();
        //System.out.println("here i am!");
    }
}