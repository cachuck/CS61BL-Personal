package gitlet;
import static gitlet.Functions.*;
import java.util.HashMap;
import java.util.HashSet;
import static gitlet.Utils.exitWithError;
import static gitlet.Utils.serialize;

public class CommitFunction {
//////////////////////////////////////////////////////////////////////////////////////////
///// GIT COMMIT ///// GIT COMMIT ///// GIT COMMIT ///// GIT COMMIT ///// GIT COMMIT /////
//////////////////////////////////////////////////////////////////////////////////////////
    public static void gitCommit(String msg) {
        HashMap<String, String> stageContent = Utils.readObject(addStage, HashMap.class);
        HashSet<String> unstageContent = Utils.readObject(removeStage, HashSet.class);
        if (stageContent.isEmpty() && unstageContent.isEmpty()) {
            exitWithError("No changes added to the commit.");
        }
        if (msg.equals("")) {
            exitWithError("Please enter a commit message.");
        }

        String parentCommitName = Utils.readContentsAsString(getHeadBranchFile());
        // Takes map of files and blobs staged for addition and puts in copied Commit map from head commit
        HashMap<String, String> newBlobs = (HashMap) getHeadCommit().getBlobMap().clone();

        // if file is staged for removal do not put in new commit
        for (Object i : Utils.readObject(removeStage, HashSet.class)){
            newBlobs.remove(i);
        }
        // Deserialized the adding stage map
        HashMap<String, String> readAddingStage = Utils.readObject(addStage, HashMap.class);
        // Copies elements from added files to commit
        newBlobs.putAll(readAddingStage);

        // Creates new Commit from new blob and file map
        Commit newCommitObj = new Commit(msg, parentCommitName, newBlobs);

        // Serialize to Commit folder with Hash name
        saveCommit(newCommitObj);

        // Clear adding stage and re-serialize it to the adding stage file
        HashMap<String, String> newAddingStage = new HashMap<>();
        Utils.writeObject(addStage, newAddingStage);

        // Clear removal stage and re-serialize it to the removing stage file
        HashSet<String> newRemovingStage = new HashSet<>();
        Utils.writeObject(removeStage, newRemovingStage);

        // Writes new Commit to branch name
        String commitHash = getHash(serialize(newCommitObj));
        //if (commitHash.length() < 40) commitHash = commitHash.substring(0,6); //shortUID
        Utils.writeContents(getHeadBranchFile(), commitHash);
    }
}
