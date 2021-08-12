package gitlet;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import static gitlet.Functions.*;
import static gitlet.Utils.readContents;
import static gitlet.Utils.readObject;

public class Add {
/////////////////////////////////////////////////////////////////////////////////////////
///// GIT ADD ///// GIT ADD ///// GIT ADD ///// GIT ADD ///// GIT ADD ///// GIT ADD /////
/////////////////////////////////////////////////////////////////////////////////////////
    public static void gitAdd(String filename) {
        // Sets up preliminary variables
        File fileLocation = Utils.join(CWD, filename);
        // Ensures no errors occur that may hinder the integrity of this function
        if (!fileLocation.exists()) {
            Utils.exitWithError("File does not exist.");
        }
        String blobHash = getHash(readContents((fileLocation)));
        File currBlobToAdd = Utils.join(BLOBS_DIR, blobHash);
        try {
            currBlobToAdd.createNewFile();
        } catch (IOException createExcep) {
            Utils.exitWithError("Error in creating blob -> Functions.gitAdd");
        }

        // A removal followed by an add that restores former contents should simply "un-remove" the file without staging
        if (readObject(removeStage, HashSet.class).contains(filename)) {
            //Remove filename from removestage
            HashSet<String> old = readObject(removeStage, HashSet.class);
            old.remove(filename);
            Utils.writeObject(removeStage, old);
            return;
        }

        // SEE if hash of CWD file is same as hash of head commit file blob -- if so, return
        if (blobHash.equals(getHeadCommit().getBlobMap().get(filename))) {
            return;
        }

        // WRITES byte array from file to blob file
        Utils.writeContents(currBlobToAdd, Utils.readContents(fileLocation));
        HashMap<String, String> addMap = Utils.readObject(addStage, HashMap.class);
        addMap.put(filename, blobHash);
        Utils.writeObject(addStage, addMap);
    }
}
