import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ pq = new MinHeapPQ();
        pq.insert(5, 3);
        pq.insert(8, 2);
        pq.insert(2, 3);
        pq.insert(4, 1);
        pq.insert(6, 3);
        assertEquals(5, pq.size());
        assertEquals(4, pq.poll());
        pq.changePriority(5, 5);
        pq.changePriority(8, 5);
        pq.insert(100, 5);
        pq.insert(3, 5);
        pq.toString();
    }
}
