package gh2;

import deque.ArrayDeque;
import deque.LinkedListDeque;
import deque.Deque;
import java.lang.Math;

public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        double doubleCapacity = Math.round(SR/frequency);
        int capacity = (int) doubleCapacity;
        buffer = new LinkedListDeque<>();
        for (int i = 0; i < capacity; i++){
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for(int bSize = 0; bSize < buffer.size(); bSize++){
            buffer.removeLast();
            double r = Math.random() - 0.5;
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double p1 = buffer.removeFirst();
        double p2 = buffer.get(1);
        double newDouble = DECAY * 0.5 * (p1 + p2);
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
