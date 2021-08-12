package gitlet;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Represents a gitlet commit object.
 *  @author C & K
 */
public class Commit implements Serializable {
    // Message of commit
    private String message;
    // Parent of commit, may be null for initial commit
    private String parent;
    // Current timestamp, may be set to Unix Time for initial commit
    private String currTime;
    private HashMap<String, String> fileToBlob;


    /* TODO: fill in the rest of this class. */
    public Commit(String msg, String parent, HashMap<String, String> blobs) {
        // Obtains current timestamp for this commit
        LocalDateTime unformattedTime = LocalDateTime.now();
        DateTimeFormatter timeStyler = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss yyyy -0800");
        this.currTime = unformattedTime.format(timeStyler);
        // If this commit is the initial commit, set timestamp to be Unix Epoch
        if (parent == null){
            this.currTime = Functions.EPOCH;
        }
        this.message = msg;
        this.parent = parent;
        this.fileToBlob = new HashMap<>(blobs); // do we need to make new or can we just pass in blobs? see commit in Functions
    }

    public HashMap<String, String> getBlobMap() {
        return this.fileToBlob;
    }

    public String getMessage() {return this.message;}

    public String getCurrTime() {return this.currTime;}

    public String getParentString() {return this.parent;}





}
