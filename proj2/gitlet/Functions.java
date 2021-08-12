package gitlet;
import static gitlet.Utils.*;
import java.io.File;
import java.io.IOException;

/*
 * NEW MESSAGE:
 * 3 AM Shenanigans. Reformatted project and moved each function to its own file.
 * Good practice, good formatting, and easy to read. Many functions call on directories such as GITLET_DIR,
 * so these essential file locations, templates, and public methods are kept in this general "functions" file.
 * Read the OLD MESSAGE below as to what existed here prior to the migration. --Chris :)
 *
 *
 * OLD MESSAGE:
 * Functions.java is the core unit of this project. Within, all commands issued from Main.java (which houses the main method) are executed here.
 * in turn, Functions.java calls on other methods from each extra file (ie Utils.java) to help it reach its goals.
 * @author Chris & Kusha
 */
/////////////////////////////////////////////////////////////////////////////////////////////
///// BEGIN FUNCTIONS ///// BEGIN FUNCTIONS ///// BEGIN FUNCTIONS ///// BEGIN FUNCTIONS /////
/////////////////////////////////////////////////////////////////////////////////////////////
public class Functions {
    // SETS Unix Time
    public static final String EPOCH = "Wed Dec 31 16:00:00 1969 -0800";
    // CREATES directories and essential scaffolding files in .gitlet
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    public static final File BLOBS_DIR = join(GITLET_DIR, "blobs");
    public static final File BRANCHES_DIR = join(GITLET_DIR, "branches");
    public static File addStage = Utils.join(STAGING_DIR, "addingStage");
    public static File removeStage = Utils.join(STAGING_DIR, "removingStage");
    public static File headFile = Utils.join(BRANCHES_DIR, "HEAD");
    public static File masterFile = Utils.join(BRANCHES_DIR, "master");

/////////////////////////////////////////////////////////////////////////////////////////////
///// EXTRA UTILITIES ///// EXTRA UTILITIES ///// EXTRA UTILITIES ///// EXTRA UTILITIES /////
/////////////////////////////////////////////////////////////////////////////////////////////
    public static String getHash(Object... val) {
        return Utils.sha1(val);
    }

    public static void saveCommit(Commit obj) {
        String commitHashName = getHash(serialize(obj));
        //if (commitHashName.length() < 40) commitHashName = commitHashName.substring(0,6);
        File newCommitPath = Utils.join(COMMITS_DIR, commitHashName);
        try {
            newCommitPath.createNewFile();
        } catch (IOException newCommitCreationException) {
            System.out.println("Error in creating newCommit file -> Functions.");
        }
        Utils.writeObject(newCommitPath, obj);
    }

    public static Commit getHeadCommit() {
        String headCommitHash = Utils.readContentsAsString(getHeadBranchFile()); // goes to curr Branch to find head Commit hash
        File currCommit = Utils.join(COMMITS_DIR, headCommitHash);
        Commit headCommit = Utils.readObject(currCommit, Commit.class); // reads Commit from commit file in Commits
        return headCommit;

    }

    public static File getHeadBranchFile() {
        String headBranch = Utils.readContentsAsString(headFile);
        File currBranch = Utils.join(BRANCHES_DIR, headBranch);
        return currBranch;
    }

    public static String checkID(String commitID) {
        if (commitID.length() > 39) {
            return commitID;
        }
        else { // if SHORT id is passed in, return NORMAL id that matches SHORT id
            for (String files : plainFilenamesIn(COMMITS_DIR)) {
                String checkFile = files.substring(0, commitID.length());
                if (checkFile.equals(commitID)){
                    return files;
                }
            }
        }
        return "Error in Functions.checkID";
    }
    // take in commit id in main.java
    // if shorter than normal (40), run this method
    // FOR loop over ALL commits in commits_dir
    // check .substring[0, shortUID.length] for all
    // if matches, pass THAT long commitID through the function.
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
///// END FUNCTIONS ///// END OF FILE ///// END FUNCTIONS ///// END OF FILE ///// END FUNCTIONS /////
/////////////////////////////////////////////////////////////////////////////////////////////////////