import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* TODO: Instance variables here */
    //private Object[] data = new Object[26];
    private LinkedList<Entry<K, V>>[] data;
    private int size;
    private double loadFactor = 0.75;

    public HashMap() {
        this.data = new LinkedList[16];
        this.size = 0;
        this.loadFactor = 0.75;
        for (int i = 0; i < data.length; i++) {
            data[i] = new LinkedList<>();
        }
    }

    public HashMap(int initialCapacity) {
        this.data = new LinkedList[initialCapacity];
        this.size = 0;
        this.loadFactor = 0.75;
        for (int i = 0; i < data.length; i++) {
            data[i] = new LinkedList<>();
        }
    }

    public HashMap(int initialCapacity, double loadFactor) {
        this.data = new LinkedList[initialCapacity];
        this.size = 0;
        this.loadFactor = loadFactor;
        for (int i = 0; i < data.length; i++) {
            data[i] = new LinkedList<>();
        }
    }

    public int capacity() {
        return this.data.length;
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        if (data[keyMod] == null) return false;
        for (int i = 0; i < data[keyMod].size(); i++) {
            if (data[keyMod].get(i).getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public V get(K key) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        if (!containsKey(key)) return null;
        for (int i = 0; i < data[keyMod].size(); i++) {
            if (data[keyMod].get(i).getKey().equals(key)) {
                return data[keyMod].get(i).getValue();
            } // I like how one line is changed from the previous method
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        Entry<K, V> entrie = new Entry<K, V>(key, value); //Creates new Entry w/given parameters
        if (containsKey(key)) { //If already contains key, overwrite value
            for (int i = 0; i < data[keyMod].size(); i++) { //Finds the key-value pair
                if (data[keyMod].get(i).getKey().equals(key)) { //If the key-value pair equals our key-value input
                    data[keyMod].set(i, entrie); //Overwrite values
                }
            }
        } else { //Otherwise,
            resize();
            keyMod = Math.floorMod(key.hashCode(), data.length);
            data[keyMod].addLast(entrie); //Add key-value pair to end of LinkedList
            size++; //Adds one to size
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public V remove(K key) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        if (!containsKey(key)) return null;
        for (int i = 0; i < data[keyMod].size(); i++) {
            Entry<K, V> element = data[keyMod].get(i);
            if (element.getKey().equals(key)) {
                V storeValue = element.getValue();
                data[keyMod].remove(element);
                size--;
                return storeValue;
            }
        }
        return null;
    }

/*    public int hash(String key) {
        return (key.charAt(0) - 'A');
    }*/

    public void resize() {
        double loaded = ((size() + 1.0) / data.length);
        if (loaded > loadFactor) {
            HashMap arrayResize = new HashMap(data.length * 2);
            for (int i = 0; i < data.length; i++) {
                if (data[i].size() > 0) {
                    K replaceKey = data[i].get(0).key; // get key at this array index
                    int newIndex = Math.floorMod(replaceKey.hashCode(), arrayResize.data.length);
                    arrayResize.data[newIndex] = data[i];
                }
            }
            this.data = arrayResize.data;
        }
        // increase array length if load factor =0.75
        // move elements accordingly w/ mod being new size
        // then add element if new key
    }

    //@Override
    //public Iterator<K> iterator() throws UnsupportedOperationException;

    @Override
    public void clear() {
        // why isnt this possible: this = new HashMap();
        this.data = new LinkedList[10];
        this.size = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = new LinkedList<>();
        }
    }

    @Override
    public boolean remove(K key, V value) {
        if (remove(key) == null || get(key) != value){
            return false;
        }
        remove(key);
        return true;
    }



    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry<K, V>) other).key)
                    && value.equals(((Entry<K, V>) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }
}