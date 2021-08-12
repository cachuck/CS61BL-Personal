import org.junit.Test;
import static org.junit.Assert.*;

public class CodingChallengesTest {

    @Test
    public void testMNset() {
        int[] myArray = {8, 2, 3, 6, 7, 5, 0, 4, 9};
        assertEquals(1, CodingChallenges.missingNumber(myArray));
    }
    @Test
    public void testMNordered() {
        int[] myArray = {0, 1, 2, 3, 5};
        assertEquals(4, CodingChallenges.missingNumber(myArray));
    }
    @Test
    public void testMNone() {
        int[] myArray = {1};
        assertEquals(0, CodingChallenges.missingNumber(myArray));
    }
    @Test
    public void testMNzero() {
        int[] myArray = {0};
        assertEquals(-1, CodingChallenges.missingNumber(myArray));
    }

    @Test
    public void testsumTo() {
        int[] myArray = {1,2,3,4,5};
        assertTrue(CodingChallenges.sumTo(myArray, 7));
    }
    @Test
    public void testsumTo2() {
        int[] myArray = {1,5,3,4,2};
        assertTrue(CodingChallenges.sumTo(myArray, 9));
    }
    @Test
    public void testsumTo3() {
        int[] myArray = {1,5,2};
        assertFalse(CodingChallenges.sumTo(myArray, 12));
    }
    @Test
    public void testPerm1() {
        String s1 = "abcd";
        String s2 = "dcba";
        assertTrue(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm2() {
        String s1 = "abc";
        String s2 = "dcba";
        assertFalse(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm3() {
        String s1 = "aabb";
        String s2 = "bbaa";
        assertTrue(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm4() {
        String s1 = "abcabbc";
        String s2 = "cabbbac";
        assertTrue(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm5() {
        String s1 = "qpxzmfyhekhhfkdn";
        String s2 = "zhxfqhfdpmkykhen";
        assertTrue(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm6() {
        String s1 = "a";
        String s2 = "a";
        assertTrue(CodingChallenges.isPermutation(s1, s2));
    }
    @Test
    public void testPerm7() {
        String s1 = "x";
        String s2 = "z";
        assertFalse(CodingChallenges.isPermutation(s1, s2));
    }
}