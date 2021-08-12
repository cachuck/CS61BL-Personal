import java.sql.Array;
import java.util.LinkedList;
import java.util.ListIterator;

public class SimpleNameMap {

    /* TODO: Instance variables here */
    //private Object[] data = new Object[26];
    private LinkedList<Entry>[] data;
    private int size;

    public SimpleNameMap() {
        this.data = new LinkedList[10];
        this.size = 0;
    }

    public SimpleNameMap(int startSize) {
        this.data = new LinkedList[startSize];
        this.size = 0;
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        for (int i = 0; i < data.length; i++) {
            size += data[i].size();
        }
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        for (int i = 0; i < data[keyMod].size(); i++) {
            if (data[keyMod].get(i).getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
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
    public void put(String key, String value) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        Entry entrie = new Entry(key, value); //Creates new Entry w/given parameters
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
    public String remove(String key) {
        int keyMod = Math.floorMod(key.hashCode(), data.length);
        //int keyIndex = hash(key);
        if (!containsKey(key)) return null;
        for (int i = 0; i < data[keyMod].size(); i++) {
            Entry element = data[keyMod].get(i);
            if (element.getKey().equals(key)) {
                String storeValue = element.getValue();
                data[keyMod].remove(element);
                return storeValue;
            }
        }
        return null;
    }

    public int hash(String key) {
        return (key.charAt(0) - 'A');
    }

    public void resize(){
        double loaded = ((size() + 1.0) / data.length);
        if (loaded >= 0.75) {
            SimpleNameMap arrayResize = new SimpleNameMap(data.length * 2);
            for (int i = 0; i < data.length; i++){
                // if there is no linked list at element
                // if there is get the key of the first element instead of inputting key
                // delete key method
                String replaceKey = data[i].get(0).key; // get key at this array index
                int newIndex = Math.floorMod(replaceKey.hashCode(), arrayResize.data.length);
                arrayResize.data[newIndex] = data[i];
            }
            this.data = arrayResize.data;
        }
        // increase array length if load factor =0.75
        // move elements accordingly w/ mod being new size
        // then add element if new key
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}