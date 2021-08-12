package gitlet;
import static gitlet.Functions.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import static gitlet.Utils.serialize;

public class Init {
///////////////////////////////////////////////////////////////////////////////////////////////
///// GIT INIT ///// GIT INIT ///// GIT INIT ///// GIT INIT ///// GIT INIT ///// GIT INIT /////
///////////////////////////////////////////////////////////////////////////////////////////////
    public static void gitInit() {
        // ERRORS if .gitlet file already exists in the CWD
        if (GITLET_DIR.exists()) {
            Utils.exitWithError("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        // CREATES a .gitlet folder in the CWD
        GITLET_DIR.mkdir();
        // CREATES sub-folders within the .gitlet folder
        if (!(STAGING_DIR.exists())) {
            STAGING_DIR.mkdir();
        }
        if (!(COMMITS_DIR.exists())) {
            COMMITS_DIR.mkdir();
        }
        if (!(BLOBS_DIR.exists())) {
            BLOBS_DIR.mkdir();
        }
        if (!(BRANCHES_DIR.exists())) {
            BRANCHES_DIR.mkdir();
        }

        // CREATES adding stage in Staging folder
        HashMap<String, String> mapAddStage = new HashMap<>();
        try {
            addStage.createNewFile();
        } catch (IOException addStageCreationExcep) {
            System.out.println("Error in creating addingStage hashmap file -> Functions.gitInit");
        }
        Utils.writeObject(addStage, mapAddStage);

        // CREATES removing stage in Staging folder
        HashSet<String> mapRemoveStage = new HashSet<>();
        try {
            removeStage.createNewFile();
        } catch (IOException removeStageCreationExcep) {
            System.out.println("Error in creating removingStage hashset file -> Functions.gitInit");
        }
        Utils.writeObject(removeStage, mapRemoveStage);

        // CREATES HEAD and master files in BRANCHES_DIR
        try {
            headFile.createNewFile();
        } catch (IOException headFileCreationExcep) {
            System.out.println("Error in creating headFile -> Functions.gitInit");
        }
        try {
            masterFile.createNewFile();
        } catch (IOException masterFileCreationExcep) {
            System.out.println("Error in creating masterFile -> Functions.gitInit");
        }
        Utils.writeContents(headFile, "master");

        Commit initial = new Commit("initial commit", null, new HashMap<String, String>());
        saveCommit(initial);
        String initCommitHash = getHash(serialize(initial));
        //if (initCommitHash.length() < 40) initCommitHash = initCommitHash.substring(0,6);
        Utils.writeContents(masterFile, initCommitHash);
    }
}
