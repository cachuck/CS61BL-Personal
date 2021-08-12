import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap heep = new MinHeap();
        heep.insert(5);
        heep.insert(3);
        heep.insert(8);
        assertTrue(heep.contains(3));
        assertTrue(heep.contains(8));
        assertEquals(3, heep.size());
    }
}
