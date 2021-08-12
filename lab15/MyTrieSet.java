import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL{

    private static class Node {
        private boolean isKey;
        private char character;
        private HashMap<Character, Node> childrenMap;

        private Node(char character, boolean isKey, HashMap<Character, Node> childrenMap) {
            this.character = character;
            this.isKey = isKey; // isKey
            this.childrenMap = childrenMap;
        }
    }

    private Node root;
    public MyTrieSet(){
        this.root = new Node('\0', false, new HashMap<Character, Node>());
    }



    /** Clears all items out of Trie */
    @Override
    public void clear(){
        this.root = new Node('\0', false, new HashMap<>());
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        // Ensures first letter of KEY exists!
        if (!this.root.childrenMap.containsKey(key.charAt(0))) return false;
        Node current = this.root.childrenMap.get(key.charAt(0));
        for (int i = 0; i < key.length(); i++) {
            if (i == key.length() - 1) {
                if (current.isKey) return true;
                else return false;
            }
            if (current.childrenMap.containsKey(key.charAt(i + 1))) {
                current = current.childrenMap.get(key.charAt(i + 1));
            }
            else {
                return false;
            }
        }
        return false;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.childrenMap.containsKey(c)) {
                curr.childrenMap.put(c, new Node(c, false, new HashMap<Character, Node>()));
            }
            curr = curr.childrenMap.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix){
        Node current = root; //set current to last char of prefix's node
        for (int length = 0; length < prefix.length(); length++){
            char myChar = prefix.charAt(length);
            current = current.childrenMap.get(myChar); dummy --> s--> a
        }
        List<String> validWords = new ArrayList<>(); // create list to return later
        return prefixHelper(prefix, current, validWords); // returns result (a list) of helper method
    }
    private List<String> prefixHelper(String currWord, Node current, List<String> myList) {
        if (current.childrenMap.isEmpty()) return null;
        for (char i : current.childrenMap.keySet()) {
            if (current.childrenMap.get(i).isKey) { //i == currWord.length()-1 &&
                myList.add(currWord + i);
                prefixHelper(currWord + i, current.childrenMap.get(i), myList);
            }
        }
        return myList;
    }
    // check if prefix is a word --> if prefix[last] = isKey --> put prefix into arraylsit
    // when children map is null we stop
    // else: for all characters in keys, add next character to prefix to call method again
    //prefix is sa
    // sa-->n, sa-->m

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 15. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Literally throwing.");
    }
}
