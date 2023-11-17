import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {

    private static final int REF = 50;
    @Test
    public void testStudentArrayDeque() {
        String message = "";
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> actual = new StudentArrayDeque<Integer>();
        for (int i = 0; i < REF; i++) {
            double ref = StdRandom.uniform();
            if (ref < 0.5) {
                message += "addFirst(" + i + ")" + "\n";
                expected.addFirst(i);
                actual.addFirst(i);
            } else {
                message += "addLast(" + i + ")" + "\n";
                expected.addLast(i);
                actual.addLast(i);
            }
        }

        for (int i = 0; i < REF; i++) {
            double test = StdRandom.uniform();
            if (test < 0.5) {
                message += "removeFirst()" + "\n";
                Integer x = expected.removeFirst();
                Integer y = actual.removeFirst();
                assertEquals(message, x, y);
            } else {
                message += "removeLast()" + "\n";
                Integer x = expected.removeLast();
                Integer y = actual.removeLast();
                assertEquals(message, x, y);
            }
        }
    }
}
