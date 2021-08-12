package deque;

public class ArrayDeque<T> implements Deque<T>{

    private int size; // number of items in array
    private T[] items;
    private int last;
    private int front;

    public ArrayDeque() { // constructs Deque of size 8
        items = (T[])new Object[8];
        size = 0;
        last = 7;
        front = 0;
    }

    private void resize() {
        T[] temp = (T[])new Object[items.length * 2]; //creates new AD with desired capacity (previous size * 2)
        for (int i = 0; i < items.length; i++){ //populates new AD with old values
            temp[i] = get(i);
        }
        last = items.length - 1; //stores items.length before it is reassigned
        items = temp; //sets items to be temp, garbage collector deletes old items AD
        front = 0;
    }

    private void downsize() {
        T[] temp = (T[])new Object[items.length / 2]; //creates new AD with desired capacity (previous size * 2)
        for (int i = 0; i < temp.length; i++){ //populates new AD with old values
            temp[i] = get(i);
        }
        items = temp; //sets items to be temp, garbage collector deletes old items AD
        last = items.length - 1;
        front = 0;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) resize();
        if (front == 0) front = items.length - 1;
        else front--;
        items[front] = item;
        size++;
    }
    // can make code into one function and call for each for readability
    @Override
    public void addLast(T item) {
        if (size == items.length) resize();
        last = (last + 1) % items.length; //check later
        items[last] = item;
        size++;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) return null;
        if(this.size >= 16 && (this.size-2) <= items.length / 4) downsize(); //downsizes to ensure space-efficiency
        T a = items[front];
        if (front == items.length - 1){
            front = 0;
        }
        else front++;
        size--;
        return a;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) return null;
        if(this.size >= 16 && (this.size-2) <= items.length / 4) downsize(); //downsizes to ensure space-efficiency
        T a = items[last];
        if (last == 0){
            last = items.length - 1;
        }
        else last--;
        size--;
        return a;
    }

    @Override
    public T get(int index) {
        return items[(index + front) % items.length];
    }

    @Override
    public boolean equals(Object O) {
        if (O == null) return false;
        if (!(O instanceof Deque)) return false;
        Deque A = this;
        Deque ADList = (Deque) O;
        if (A == ADList) return true;
        if (A.size() != ADList.size()) return false;
        for (int indexes = A.size() - 1; indexes >= 0; indexes--) {
            if (!(A.get(indexes).equals(ADList.get(indexes)))) return false;
        }
        return true;
    }
}