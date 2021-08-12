package gitlet;
import static gitlet.Functions.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static gitlet.Utils.readObject;

public class Log {
///////////////////////////////////////////////////////////////////////////////////////////////
///// GIT FIND ///// GIT FIND ///// GIT FIND ///// GIT FIND ///// GIT FIND ///// GIT FIND /////
///////////////////////////////////////////////////////////////////////////////////////////////
    public static void gitFind(String commitMessage) {
        List<String> allCommitsLst = Utils.plainFilenamesIn(COMMITS_DIR);
        Set<String> messages = new HashSet();
        for (String i : allCommitsLst) {
            File currCommit = Utils.join(COMMITS_DIR, i);
            Commit commitObj = Utils.readObject(currCommit, Commit.class);
            messages.add(commitObj.getMessage());
            if (commitObj.getMessage().equals(commitMessage)) {
                System.out.println(i);
            }
        }
        if (!messages.contains(commitMessage)){
            System.out.println("Found no commit with that message.");
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////
///// GIT GLOBAL LOG ///// GIT GLOBAL LOG ///// GIT GLOBAL LOG ///// GIT GLOBAL LOG /////
/////////////////////////////////////////////////////////////////////////////////////////
    public static void gitGlobalLog() {
        List<String> allCommitsLst = Utils.plainFilenamesIn(COMMITS_DIR);
        for (String name : allCommitsLst) {
            File currCommit = Utils.join(COMMITS_DIR, name);
            Commit commitObj = Utils.readObject(currCommit, Commit.class);
            System.out.println("===");
            System.out.println("commit " + name);
            System.out.println("Date: " + commitObj.getCurrTime());
            System.out.println(commitObj.getMessage());
            System.out.println("");
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////
///// GIT LOG ///// GIT LOG ///// GIT LOG ///// GIT LOG ///// GIT LOG ///// GIT LOG /////
/////////////////////////////////////////////////////////////////////////////////////////
    public static void gitLog() {
        Commit current = getHeadCommit();
        String name = Utils.readContentsAsString(getHeadBranchFile());
        gitLogHelper(current, name);
    }
    private static void gitLogHelper(Commit currentCommit, String name) {
        System.out.println("===");
        System.out.println("commit " + name);
        System.out.println("Date: " + currentCommit.getCurrTime());
        System.out.println(currentCommit.getMessage());
        System.out.println("");

        if (currentCommit.getParentString() == null) {
            return;
        }

        String newName = currentCommit.getParentString();
        File findLogParent = Utils.join(COMMITS_DIR, newName);
        Commit currentCommitsParent = readObject(findLogParent, Commit.class);
        gitLogHelper(currentCommitsParent, newName);
    }
}
