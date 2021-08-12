public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode ROOT) {
        super();
        this.root = ROOT;
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        TreeNode helperInput = this.root;
        return containsHelper(key, helperInput);
    }
/*        if (key == null) {
            return false;
        }
        if (key.equals(this.root)){
            return true;
        }
        else {

        }*/

        // if empty tree --> return false
        // if key = root --> return true
        // else: if key < root go left, if key is > root go right, and do
        // the search on the tree where the root is now the new left/right node

    /*
    // public boolean contains(T key){
    // helperInput = this.root
    // containsHelper(key, helperInput)
    // private boolean containsHelper(T key, TreeNode root){
    // if key(compareTo(root.item)) < 0) {
    // containsHelper(key, root.left)
    // if key(compareTo(root.item)) > 0
    // containsHelper(key, root.right)
*/

    private boolean containsHelper(T key, TreeNode helpMeOut){
        //Alternate solution --> printPreorder and check
        if (helpMeOut == null) {
            return false;
        }
        if (key.compareTo(helpMeOut.item) == 0){
            return true;
        }

        if (key.compareTo(helpMeOut.item) < 0){
            return containsHelper(key, helpMeOut.left);
        }
        if (key.compareTo(helpMeOut.item) > 0){
            return containsHelper(key, helpMeOut.right);
        }
        return false;
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        if (!this.contains(key)){
            root = addHelper(key, root);
        }
    }

    private TreeNode addHelper(T key, TreeNode helpMeAddANode) {
        if (helpMeAddANode == null) return new TreeNode(key);
        if (key.compareTo(helpMeAddANode.item) > 0) {
            helpMeAddANode.right = addHelper(key, helpMeAddANode.right);
        }
        else if (key.compareTo(helpMeAddANode.item) < 0) {
            helpMeAddANode.left = addHelper(key, helpMeAddANode.left);
        }
        return helpMeAddANode;
    }


    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}