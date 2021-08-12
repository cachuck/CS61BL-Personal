import org.junit.Test;
import static org.junit.Assert.*;

public class UnionTest {
    @Test
    public void findTest() {
    UnionFind test = new UnionFind(5);
    test.union(1,0);
    test.union(1,2);
    test.union(2,3);
    assertEquals(0, test.find(3));
    assertEquals(0, test.parent(3));
    assertEquals(0, test.parent(2));
    }


}
