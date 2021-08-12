package gitlet;
import static gitlet.Functions.*;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Utils.exitWithError;
import static gitlet.Utils.restrictedDelete;

public class Remove {
///////////////////////////////////////////////////////////////////////////////////////////
///// GIT REMOVE ///// GIT REMOVE ///// GIT REMOVE ///// GIT REMOVE ///// GIT REMOVE /////
//////////////////////////////////////////////////////////////////////////////////////////

    public static void gitRm(String fileName) {
        HashMap<String, String> mapAddStage = Utils.readObject(addStage, HashMap.class);
        HashMap <String, String> headCommMap = getHeadCommit().getBlobMap();
        if (!mapAddStage.containsKey(fileName) && (!headCommMap.containsKey(fileName))) {
            exitWithError("No reason to remove the file.");
        }
        // Unstages file from adding stage
        if (mapAddStage.containsKey(fileName)) {
            mapAddStage.remove(fileName);
            Utils.writeObject(addStage, mapAddStage);
        }
        // If we call gitRm on a file in adding stage we want to remove from stage not from commit
        else if (headCommMap.containsKey(fileName)) {
            HashSet<String> removeSet = Utils.readObject(removeStage, HashSet.class);
            removeSet.add(fileName);
            Utils.writeObject(removeStage, removeSet);
            File toDeleteFile = Utils.join(CWD, fileName);
            if (toDeleteFile.exists()) {
                restrictedDelete(toDeleteFile);
            }
        }
    }
}
