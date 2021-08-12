package deque;

import java.util.Collections;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparatorMax;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparatorMax = c;
    }

    public T max(Comparator<T> c) {
        T max = this.get(0);
        if (isEmpty()) return null;
        for (int i = 0; i < this.size() - 2; i++) {
            int result = c.compare(this.get(i), this.get(i + 1));
            if (result == 1) max = this.get(i);
            else max = this.get(i + 1);
        }


        return max;
    }


    //returns maximum element in the deque as governed by the previously given Comparator.
    //If MaxArrayDeque is empty, return null
    public T max() {
        T max = this.get(0);
        if (isEmpty()) return null;
        for (int i = 0; i < this.size() - 2; i++) {
            int result = comparatorMax.compare(this.get(i), this.get(i + 1));
            if (result == 1) max = this.get(i);
            else max = this.get(i + 1);
        }


        return max;
    }

}
