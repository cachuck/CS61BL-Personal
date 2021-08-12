package gitlet;
import static gitlet.Functions.*;
import java.util.*;

public class Status {
//////////////////////////////////////////////////////////////////////////////////////////
///// GIT STATUS ///// GIT STATUS ///// GIT STATUS ///// GIT STATUS ///// GIT STATUS /////
//////////////////////////////////////////////////////////////////////////////////////////
    public static void gitStatus() {
        System.out.println("=== Branches ===");
        List<String> allBranches = Utils.plainFilenamesIn(BRANCHES_DIR);
        Collections.sort(allBranches);
        String currentBranch = Utils.readContentsAsString(headFile);
        for (String i : allBranches) {
            if (!i.equals("HEAD")) {
                if (currentBranch.equals(i)) {
                    System.out.println("*" + i);
                }
                else {
                    System.out.println(i);
                }
            }
        }
        // Empty line after printing branches
        System.out.println("");

        System.out.println("=== Staged Files ===");
        // Look into add_stage map, order and print.
        HashMap<String, String> readAddingStage = Utils.readObject(addStage, HashMap.class);
        List<String> addToPrint = new ArrayList<>(readAddingStage.keySet());
        Collections.sort(addToPrint);
        for (String i : addToPrint) {
            System.out.println(i);
        }
        System.out.println("");

        System.out.println("=== Removed Files ===");
        // Look into remove_stage map, order and print.
        HashSet<String> readRemovingStage = Utils.readObject(removeStage, HashSet.class);
        List<String> removeToPrint = new ArrayList<>(readRemovingStage);
        Collections.sort(removeToPrint);
        for (String i: removeToPrint) {
            System.out.println(i);
        }
        System.out.println("");

        System.out.println("=== Modifications Not Staged For Commit ==="); // OPTIONAL
        // All files in CWD that are:
        // Tracked in the current commit, changed in the working directory, but not staged; or
        //Staged for addition, but with different contents than in the working directory; or
        //Staged for addition, but deleted in the working directory; or
        //Not staged for removal, but tracked in the current commit and deleted from the working directory.
        System.out.println("");
        System.out.println("=== Untracked Files ==="); // OPTIONAL
        // All files in the CWD that are: NOT staged AND NOT tracked
        System.out.println("");
    }
}
