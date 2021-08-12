import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
    }
    @Test
    public void testtoIntArray(){
        BooleanSet aSet = new BooleanSet(25);
        aSet.add(1);
        aSet.add(6);
        aSet.add(9);
        assertEquals(3, aSet.size());
        assertEquals(new int[]{1, 6, 9}, aSet.toIntArray());
    }
    @Test
    public void testtoIntArray2(){
        BooleanSet aSet = new BooleanSet(25);
        aSet.add(0);
        aSet.add(6);
        aSet.add(9);
        aSet.add(1);
        aSet.add(7);
        aSet.add(20);
        aSet.add(4);
        aSet.add(11);
        aSet.add(15);
        assertEquals(9, aSet.size());
        assertEquals(new int[]{0,1,4,6,7,9,11,15,20}, aSet.toIntArray());
    }
    @Test
    public void testtoIntArray3(){
        BooleanSet aSet = new BooleanSet(0);
        assertEquals(0, aSet.size());
        int[] empty = {};
        assertArrayEquals(empty, aSet.toIntArray());
    }
}