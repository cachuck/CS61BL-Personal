package MemoryGame;

import byowTools.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"What's poppin", "How u doin?",
                                                   "Bruh.", "Heyyyyyy", ":))",
                                                   "She's RLLY good!", "Yoooo!", "Go Diego go!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String randomString = new String();
        for (int counter = 0; counter < n; counter++){
            Random rnd = new Random();
            char c = (char) ('a' + rnd.nextInt(26));
            randomString += c;
        }
        return randomString;
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
        * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, s);

        /* If the game is not over, display encouragement, and let the user know if they
        * should be typing their answer or watching for the next round. */
        if (!gameOver) {
            Font fontSmall = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontSmall);
            StdDraw.line(0, this.height - 2, this.width, this.height - 2);
            StdDraw.textLeft(0, this.height - 1, "Round: "  + this.round);
            if (this.playerTurn) {
                StdDraw.text(this.width / 2, this.height - 1, "Type!");
            } else {
                StdDraw.text(this.width / 2, this.height - 1, "Watch!");
            }
            int randomEncouragement = RandomUtils.uniform(this.rand, ENCOURAGEMENT.length);
            StdDraw.textRight(this.width, this.height - 1, ENCOURAGEMENT[randomEncouragement]);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int iter = 0; iter < letters.length(); iter++){
            drawFrame(Character.toString(letters.charAt(iter)));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String toReturn = "";
        for (int typed = 0; typed < n; typed++){
            if (!StdDraw.hasNextKeyTyped()) return toReturn;
            toReturn += StdDraw.nextKeyTyped();
        }
        return toReturn;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.gameOver = false;
        this.round = 1;
        this.drawFrame("Round" + this.round);

        //TODO: Establish Engine loop
        while (!gameOver) {
            drawFrame("Round: " + round); //Display the message “Round: “ followed by the round number in the center of the screen
            StdDraw.pause(1000); //Waits for 1000 milliseconds
            String RANDOMSTRING = generateRandomString(round); //Generate a random string of length equal to the current round number
            flashSequence(RANDOMSTRING); //Display the random string one letter at a time
            String userString = new String();
            while (userString.length() < RANDOMSTRING.length()){ //Wait for the player to type in a string the same length as the target string
                userString += solicitNCharsInput(1);
            }
            if (userString.equals(RANDOMSTRING)) { //Check to see if the player got it correct
                round++; //If they got it correct, repeat from step 2 after increasing the round by 1
            }
            else gameOver = true; //If they got it wrong, end the game
            StdDraw.pause(1000);
        }
        this.drawFrame("Game Over! You made it to round: " + this.round);
    }
}