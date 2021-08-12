package gitlet;
import static gitlet.Functions.*;
import static gitlet.Checkout.*;
import static gitlet.Utils.*;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class Reset {
/////////////////////////////////////////////////////////////////////////////////////////////
///////// GIT RESET ///// GIT RESET ///// GIT RESET ///// GIT RESET ///// GIT RESET /////////
/////////////////////////////////////////////////////////////////////////////////////////////
    public static void gitReset(String commitID){
        // Ensures commit exists
        if (!Utils.join(COMMITS_DIR, commitID).exists()){
            Utils.exitWithError("No commit with that id exists.");
        }
        //head commit is currently abc --> reset commitID = 123
        //take files from 123 and restore those versions in CWD, deleting all the ones that ARE in abc but NOT in 123
        Commit commitToRestoreFrom = readObject(Utils.join(COMMITS_DIR, commitID), Commit.class);
        Set<String> filesFromCommit = commitToRestoreFrom.getBlobMap().keySet();
        HashMap<String, String> commitRestoreMap = commitToRestoreFrom.getBlobMap();
        Set<String> commitRestoreFiles = commitRestoreMap.keySet();
        // IF a working file is untracked in the current branch and would be overwritten by the checkout, ERROR
        for (String fileID : filesFromCommit) {
            File overwriteDeletionCheck = Utils.join(CWD, fileID);
            if (!getHeadCommit().getBlobMap().containsKey(fileID) && overwriteDeletionCheck.exists()) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
        //TODO Removes tracked files that are not present in that commit.
        // ANY files that are tracked in the current branch (ie master) but are NOT present in the checked-out branch (ie coolbeans) are deleted.
        // edit: adding this changed nothing
        for (String file : getHeadCommit().getBlobMap().keySet()) {
            if (!commitRestoreFiles.contains(file)) {
                restrictedDelete(Utils.join(CWD, file));
            }
        }

        for (String fileID : filesFromCommit) {
            String[] args = {"empty", commitID, "--", fileID};
            gitCheckoutIDFile(args);
        }

        // Clear adding stage and re-serialize it to the adding stage file
        HashMap<String, String> newAddingStage = new HashMap<>();
        Utils.writeObject(addStage, newAddingStage);

        // Given commit will now be considered the current branch's (HEAD)
        Utils.writeContents(getHeadBranchFile(), commitID);
    }
}
