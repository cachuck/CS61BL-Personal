//<<<<<<< HEAD
import java.util.Arrays;
import java.util.ArrayList;
//=======
//>>>>>>> 76ae799720355cb2995267029373fb71fed47903

public class UnionFind {

    int[] tree;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        tree = new int[N];
        Arrays.fill(tree, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (tree[v] < 0) return -1 * (tree[v]);
        if (tree[v] >= 0) return -1 * (tree[find(v)]);
        return -1;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return tree[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        return root1 == root2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */

    public int find(int v) {
        if (v < 0 || v >= tree.length) throw new IllegalArgumentException("Fbi open up");
        if (tree[v] < 0){
            return v;
        }
        ArrayList<Integer> compress_list = new ArrayList<>();
        //int root = rootfind(v, compress_list); // why initialize to v
        int root = v;
        while(tree[root] >= 0){
            compress_list.add(root);
            root = tree[root];
        }
        for (int i = 0; i < compress_list.size(); i++) {
            tree[compress_list.get(i)] = root;
        }
        return root;
    }
//    private void pathcompress(ArrayList<Integer> compress_list, int root){
//
//        }
//    }

   /* private int rootfind(int v, ArrayList<Integer> compress_list) {

        while(tree[v] >= 0){
            compress_list.add(v);
            v = tree[v];
        }
        return v;
    }
*/

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (!connected(v1, v2)) {
            int sizeV1 = sizeOf(v1);
            int sizeV2 = sizeOf(v2);
            int rootV1 = find(v1);
            int rootV2 = find(v2);
            if (sizeV1 <= sizeV2) {
                tree[rootV2] -= sizeV1;
                tree[rootV1] = rootV2;
            } else { //if (sizeV1 > sizeV2){
                tree[rootV1] -= sizeV2;
                tree[rootV2] = rootV1;
            }
        }
    }
}