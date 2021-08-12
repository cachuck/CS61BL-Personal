package gitlet;
import static gitlet.Functions.*;
import java.io.File;
import java.io.IOException;

import static gitlet.Utils.exitWithError;

public class Branch {
//////////////////////////////////////////////////////////////////////////////////////////
///// GIT BRANCH ///// GIT BRANCH ///// GIT BRANCH ///// GIT BRANCH ///// GIT BRANCH /////
//////////////////////////////////////////////////////////////////////////////////////////
    public static void gitBranch(String branchName) {
        // Creates file path for newBranch
        File newBranch = Utils.join(BRANCHES_DIR, branchName);
        // If it exists, error
        if (newBranch.exists()){
            exitWithError("A branch with that name already exists.");
        }
        // Create the newBranch file
        try {
            newBranch.createNewFile();
        } catch (IOException newBranchFileCreationExcep) {
            System.out.println("Error in creating new branch -> Functions.gitBranch");
        }
        // LOOKS to HEAD pointer, and sees where it is pointing
        // goes to that file (ie master.txt) and sees which commit that is pointing to
        // copies commit hash to new branch
        String headCommitHash = Utils.readContentsAsString(getHeadBranchFile());
        Utils.writeContents(newBranch, headCommitHash);
    }

/////////////////////////////////////////////////////////////////////////////////////
///// GIT RM-BRANCH ///// GIT RM-BRANCH ///// GIT RM-BRANCH ///// GIT RM-BRANCH /////
/////////////////////////////////////////////////////////////////////////////////////
    public static void gitRmBranch(String branchName){
        String headBranch = Utils.readContentsAsString(headFile);
        if (headBranch.equals(branchName)) {
            exitWithError("Cannot remove the current branch.");
        }
        File branchToDelete = Utils.join(BRANCHES_DIR, branchName);
        if (branchToDelete.exists()) {
            // Deletes file as long as it exists in .gitlet
            // TODO: FIND OUT WHY THIS DIDNT WORK W/RESTRICTEDDELETE
            branchToDelete.delete();
        }
        else {
            exitWithError("A branch with that name does not exist.");
        }
    }
}
