package gitlet;

/** Pseudo Interface for Gitlet, a subset of the Git version-control system.
 *  @author C & K
 */
public class Main {
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) { Utils.exitWithError("Please enter a command."); }
        switch(args[0]) {
            case "init":
                if (args.length != 1) { Utils.exitWithError("Incorrect operands."); }
                Init.gitInit();
                break;
            case "add":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Add.gitAdd(args[1]);
                break;
            case "commit":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                CommitFunction.gitCommit(args[1]);
                break;
            case "checkout":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length == 2) { // checkout [branchname]
                    Checkout.gitCheckoutBranch(args[1]);
                }
                else if (args.length == 3 && args[1].equals("--") && !args[2].equals("--")) { // checkout -- [filename]
                    Checkout.gitCheckoutFile(args[2]);
                }
                else if (args.length == 4 && !args[1].equals("--") && args[2].equals("--") && !args[3].equals("--")) { // checkout [commitid] -- [filename]
                    args[1] = Functions.checkID(args[1]);
                    Checkout.gitCheckoutIDFile(args);
                }
                else { Utils.exitWithError("Incorrect operands."); }
                break;
            case "log":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 1) { Utils.exitWithError("Incorrect operands."); }
                Log.gitLog();
                break;
            case "status":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 1) { Utils.exitWithError("Incorrect operands."); }
                Status.gitStatus();
                break;
            case "rm":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Remove.gitRm(args[1]);
                break;
            case "global-log":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 1) { Utils.exitWithError("Incorrect operands."); }
                Log.gitGlobalLog();
                break;
            case "find":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Log.gitFind(args[1]);
                break;
            case "branch":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Branch.gitBranch(args[1]);
                break;
            case "rm-branch":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Branch.gitRmBranch(args[1]);
                break;
            case "reset":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                Reset.gitReset(Functions.checkID(args[1]));
                break;
            case "merge":
                if (!Functions.GITLET_DIR.exists()) { Utils.exitWithError("Not in an initialized Gitlet directory."); }
                if (args.length != 2) { Utils.exitWithError("Incorrect operands."); }
                MergeFunction.gitMerge(args[1]);
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}