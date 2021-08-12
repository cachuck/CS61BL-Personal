package gitlet;
import static gitlet.Functions.*;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

import static gitlet.Utils.*;

public class Checkout {
////////////////////////////////////////////////////////////////////////////////////////////////////
///// GIT CHECKOUT ///// GIT CHECKOUT ///// GIT CHECKOUT ///// GIT CHECKOUT ///// GIT CHECKOUT /////
////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO: condense repeated code
    // Version 1, then args = ["checkout",  "--", "file name"]
    // Takes the version of the file as it exists in the head commit and puts it in the working directory,
    // overwriting the version of the file that’s already there if there is one. The new version of the file is not staged.
    public static void gitCheckoutFile(String filename) {
        Commit head = getHeadCommit();
        HashMap<String, String> headMap = head.getBlobMap();

        if (!headMap.containsKey(filename)) {
            exitWithError("File does not exist in that commit.");
        }
        String checkoutBlobHash = headMap.get(filename);
        File checkoutBlob = Utils.join(BLOBS_DIR, checkoutBlobHash);
        File checkoutRestoredFile = Utils.join(CWD, filename);
        Utils.writeContents(checkoutRestoredFile, (Utils.readContents(checkoutBlob)));
    }

    // TAKES the version of the file as it exists in the commit with the given id, and puts it in the working directory,
    // overwriting the version of the file that’s already there if there is one. The new version of the file is not staged.
    public static void gitCheckoutIDFile(String[] args) {
        File findCommitFromID = Utils.join(COMMITS_DIR, args[1]); // path with commit ID
        if (!findCommitFromID.exists()) {
            exitWithError("No commit with that id exists.");
        }
        Commit commitToRestoreFrom = readObject(findCommitFromID, Commit.class); // gets commit
        HashMap<String, String> commitRestoreMap = commitToRestoreFrom.getBlobMap(); //gets blob map
        String filename = args[3];
        if (!commitRestoreMap.containsKey(filename)) {
            exitWithError("File does not exist in that commit.");
        }
        String checkoutBlobHash = commitRestoreMap.get(filename); // finds blob value associated w/ filename key
        File checkoutBlob = Utils.join(BLOBS_DIR, checkoutBlobHash);
        File checkoutRestoredFile = Utils.join(CWD, filename);
        Utils.writeContents(checkoutRestoredFile, (Utils.readContents(checkoutBlob)));
    }

    // TAKES files in head commit and puts in CWD, overwriting current versions of the files - branchName will be the new HEAD
    public static void gitCheckoutBranch(String branchName) {
        File branch = Utils.join(BRANCHES_DIR, branchName);
        if (!branch.exists()) {
            exitWithError("No such branch exists.");
        }
        String currentBranch = Utils.readContentsAsString(headFile);
        if (currentBranch.equals(branchName)) {
            exitWithError("No need to checkout the current branch.");
        }

        // SETS up important references that will be used in the following for loops
        Set<String> headCommitFileNames = getHeadCommit().getBlobMap().keySet(); // Gets HEAD commit (current branch head)
        String branchHeadHash = Utils.readContentsAsString(branch); // Gets given branch's head commit hash
        File branchHeadCommit = Utils.join(COMMITS_DIR, branchHeadHash); // Creates file to obtain commit from branch
        Commit headBranch = readObject(branchHeadCommit, Commit.class); // Gets commit from given branch
        HashMap<String, String> commitRestoreMap = headBranch.getBlobMap(); // Gets blob map from commit
        Set<String> commitRestoreFiles = commitRestoreMap.keySet(); // Gets set of file names from blob map

        // IF a working file is untracked in the current branch and would be overwritten by the checkout, ERROR
        for (String file : commitRestoreFiles) {
            File overwriteDeletionCheck = Utils.join(CWD, file);
            if (!headCommitFileNames.contains(file) && overwriteDeletionCheck.exists()) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }

        // ANY files that are tracked in the current branch (ie master) but are NOT present in the checked-out branch (ie coolbeans) are deleted.
        for (String file : headCommitFileNames) {
            if (!commitRestoreFiles.contains(file)) {
                restrictedDelete(Utils.join(CWD, file));
            }
        }

        // TAKES files from map of given commit and restores those associated blobs in the working directory
        for (String i : commitRestoreMap.keySet()) {
            String checkoutBlobHash = commitRestoreMap.get(i); // finds blob value associated w/ filename key
            File checkoutBlob = Utils.join(BLOBS_DIR, checkoutBlobHash);
            File checkoutRestoredFile = Utils.join(CWD, i);
            Utils.writeContents(checkoutRestoredFile, (Utils.readContents(checkoutBlob)));
        }

        // Given branch will now be considered the current branch (HEAD), overwriting previous saved text
        Utils.writeContents(headFile, branchName);

        // Clear adding stage and re-serialize it to the adding stage file
        HashMap<String, String> newAddingStage = new HashMap<>();
        Utils.writeObject(addStage, newAddingStage);

    }
}
