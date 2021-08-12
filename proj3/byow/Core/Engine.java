package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.InputDemo.*;
import edu.princeton.cs.introcs.StdDraw;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.util.Random;

public class Engine {
    public TERenderer ter = new TERenderer();

    public static final int WIDTH = 120;
    public static final int HEIGHT = 80;
    /* attribute that stores seed identifier for world */
    public Random rand;
    public TETile[][] world;

    public static int avatarX;
    public static int avatarY;
    TETile storedPlayerTile = Tileset.FLOOR;
    public boolean possibleSave = false;
    public int daysSinceCovid = 527;
    public boolean replay = false;
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * Whether or not the game is over.
     */
    public String gameString = "";

    public Engine() {
        //ter = new TERenderer();
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        rand = null;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     *
     * This method iterates through the input string. If the input starts with N, a new ter is initialized to be rendered.
     * Once the method extracts the numeric seed value, it generates the Random object using that value, the associated
     * world, and the avatar position in that world. Then, if the input value has characters that indicate avatar movements,
     * the method will accordingly move the avatar's position.
     *
     * If the input starts with L, the user wants to load a saved world and perform any movements after the L. The method
     * will then append the requested movements to the saved string and execute it using the approach outlined in the above
     * paragraph by inputting the conjoined string into this method.
     */
    public TETile[][] interactWithInputString(String input) {
        String newInput = input.toUpperCase();
        gameString = "";
        if (newInput.charAt(0) == 'N') {
            gameString += "N";

            ter.initialize(WIDTH, HEIGHT);
            boolean seedAdded = false;
            String modifiedArg = "";
            for (int i = 1; i < newInput.length(); i++) {
                if (!seedAdded) {
                    gameString += newInput.charAt(i);
                    if (newInput.charAt(i) != 'S') {
                        modifiedArg = modifiedArg + newInput.charAt(i);
                    } else {
                        seedAdded = true;
                        long seed = Long.parseLong(modifiedArg);
                        this.rand = new Random(seed);
                        Room.drawWorld(this);
                        setAvatarPos();
                    }
                } else {
                    if (replay){
                        directions(newInput.charAt(i), true);
                    }else {
                        directions(newInput.charAt(i), false);
                    }
                }
            }
        } else {
            String saved = loadSave();
            String addedInput = saved + newInput.substring(1);
            return interactWithInputString(addedInput);
        }
        return this.world;
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource inputSource = new KeyboardInputSource();
        drawMenu();
        while (inputSource.possibleNextInput()) {
            char key = Character.toUpperCase(inputSource.getNextKey());
            switch (key) {

                case 'N':
                    gameString += 'N';
                    ter.initialize(WIDTH, HEIGHT);
                    drawEnterSeedFrame(false, "");
                    obtainSeed(inputSource); // gets seed and generates new world, rendering it
                    playGame(inputSource); // player can type keys
                    break;
                case 'L':
                    String loadNew = loadSave();
                    interactWithInputString(loadNew); // in here it's adding to gamestring with a second call to Load
                    ter.renderFrame(world);
                    drawHUD();
                    playGame(inputSource);
                    break;
                case 'Q':
                    quitGame();
                    break;
                case 'F':
                    drawLore();
                    break;
                case 'M':
                    drawMenu();
                    break;
                case 'R':
                    String replayInput = loadSave();
                    replay = true;
                    interactWithInputString(replayInput);
                    replay = false;
                    playGame(inputSource);

            }
        }
    }
    /**
     * This method extracts the numeric characters from a keyboard input to use as the seed. Once the user hits S
     * the random world is generated. The coordinates of the Avatar are obtained.
     */
    public void obtainSeed(InputSource inputSource){
        String modifiedArg = "";
        while (inputSource.possibleNextInput()) {
            char key = Character.toUpperCase(inputSource.getNextKey());
            if (key != 'S') {
                modifiedArg = modifiedArg + key;
                drawEnterSeedFrame(true, modifiedArg);
            } else {
                gameString += modifiedArg;
                gameString += 'S';
                long seed = Long.parseLong(modifiedArg);
                this.rand = new Random(seed); // this is the seed we use for all operations
                Room.drawWorld(this);
                setAvatarPos();
                ter.renderFrame(world);
                drawHUD();
                break;
            }
        }
    }

    /**
     *
     * @param inputSource - used to obtain next input value
     * Takes in key and calls directions to perform correct action
     */
    public void playGame(InputSource inputSource) {
        while (inputSource.possibleNextInput()) {
            char key = Character.toUpperCase(inputSource.getNextKey());
            directions(key, true);
        }
    }

    /**
     *
     * @param typed - character that indicates which moves should be made
     * @param keyboard - boolean if the character is from a keyboard input
     * Calls appropriate methods to perform actions given a character. Displays action if keyboard is true.
     */

    public void directions(char typed, boolean keyboard) {
        switch (typed) {
            case 'W':
                moveUp();
                moveViruses(rand, world, WIDTH, HEIGHT);
                gameString += 'W';
                daysSinceCovid++;
                if (keyboard) {
                    ter.renderFrame(this.world);
                    drawHUD();
                }
                break;
            case 'A':
                moveLeft();
                moveViruses(rand, world, WIDTH, HEIGHT);
                gameString += 'A';
                daysSinceCovid++;
                if (keyboard) {
                    ter.renderFrame(this.world);
                    drawHUD();
                }
                break;
            case 'S':
                moveDown();
                moveViruses(rand, world, WIDTH, HEIGHT);
                gameString += 'S';
                daysSinceCovid++;
                if (keyboard) {
                    ter.renderFrame(this.world);
                    drawHUD();
                }
                break;
            case 'D':
                moveRight();
                moveViruses(rand, world, WIDTH, HEIGHT);
                gameString += 'D';
                daysSinceCovid++;
                if (keyboard) {
                    ter.renderFrame(this.world);
                    drawHUD();
                }
                break;
            case 'Q':
                if (possibleSave) {
                    saveGame();
                    possibleSave = false;
                    if (keyboard) {
                        quitGame();
                    }
                }
                break;
            case ':':
                possibleSave = true;
                break;
            case 'O':
                Room.toggleLights(rand, world, WIDTH, HEIGHT);
                gameString += 'O';
                if (keyboard) {
                    ter.renderFrame(this.world);
                    drawHUD();
                }
                break;
            default:
                System.out.println("Invalid keypress.");
                break;
        }
    }

    /**
     * Writes gameString to a text file, which records world seed and the sequence of avatar's subsequent moves.
     */

    public void saveGame(){
        File gameFile = Utils.join(CWD, "savedGame.txt");
        try {
            gameFile.createNewFile();
        } catch (IOException gameFileCreationExcep) {
            System.out.println("Error in saving game. Have you tried turning your computer on and off again?");
        }
        Utils.writeContents(gameFile, gameString);
    }

    /**
     * Reads and returns the saved gameString, a string of the saved world's seed and avatar's subsequent moves in world.
     */
    public String loadSave(){
        File gameFile = Utils.join(CWD, "savedGame.txt");
        return Utils.readContentsAsString(gameFile);
    }

    /**
     * Exits game
     */
    public void quitGame(){
        System.exit(0);
    }

    /**
     *
     * @param world - a TETile array
     * @param WIDTH - the world width
     * @param HEIGHT - the world height
     * @return true if lights are on. False if lights are off. Does so by iterating through world and finding
     * an either an off or on light bulb tile.
     */
    public boolean lightStatus(TETile[][] world, int WIDTH, int HEIGHT) {
        for (int x = 1; x < WIDTH-1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                if (world[x][y] == Tileset.LIGHTBULB_OFF) {
                    return false;
                }
                else if (world[x][y] == Tileset.LIGHTBULB_ON) {
                    return true;
                }
            }
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////                 AVATAR MOVEMENTS                  ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Avatar will move up one tile if it is a floor. It will not move if the tile above is a floor or light bulb.
     * Player will lose game if avatar hits virus, and win game if avatar hits vaccine.
     */
    public void moveUp() {
        setAvatarPos();
        if (world[avatarX][avatarY + 1] == Tileset.WALL || world[avatarX][avatarY + 1] == Tileset.LIGHTBULB_OFF || world[avatarX][avatarY + 1] == Tileset.LIGHTBULB_ON) {
            return;
        }
        else if (world[avatarX][avatarY + 1] == Tileset.FLOOR || world[avatarX][avatarY + 1] == Tileset.FLOOR_LIT || world[avatarX][avatarY + 1] == Tileset.FLOOR_BRIGHT || world[avatarX][avatarY + 1] == Tileset.NPC) {
            if ((storedPlayerTile == Tileset.FLOOR_LIT || storedPlayerTile == Tileset.FLOOR_BRIGHT) && !lightStatus(world, WIDTH, HEIGHT)) {
                storedPlayerTile = Tileset.FLOOR;
            }
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX][avatarY + 1];
            world[avatarX][avatarY + 1] = Tileset.PLAYER;
            Room.forceRestoreLights(rand, world, WIDTH, HEIGHT);
        }
        else if (world[avatarX][avatarY + 1] == Tileset.VACCINE) {
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX][avatarY + 1];
            world[avatarX][avatarY + 1] = Tileset.PLAYER;
            drawWinPage();
        }
        else if (world[avatarX][avatarY + 1] == Tileset.VIRUS) {
            drawLosePage();
        }
    }

    /**
     * Avatar will move down by one tile if it is a floor. It will not move if the tile below is a floor or light bulb.
     * Player will lose game if avatar hits virus, and win game if avatar hits vaccine.
     */
    public void moveDown() {
        setAvatarPos();
        if (world[avatarX][avatarY - 1] == Tileset.WALL || world[avatarX][avatarY - 1] == Tileset.LIGHTBULB_OFF || world[avatarX][avatarY - 1] == Tileset.LIGHTBULB_ON) {
            return;
        }
        else if (world[avatarX][avatarY - 1] == Tileset.FLOOR || world[avatarX][avatarY - 1] == Tileset.FLOOR_LIT || world[avatarX][avatarY - 1] == Tileset.FLOOR_BRIGHT || world[avatarX][avatarY - 1] == Tileset.NPC) {
            if ((storedPlayerTile == Tileset.FLOOR_LIT || storedPlayerTile == Tileset.FLOOR_BRIGHT) && !lightStatus(world, WIDTH, HEIGHT)) {
                storedPlayerTile = Tileset.FLOOR;
            }
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX][avatarY - 1];
            world[avatarX][avatarY - 1] = Tileset.PLAYER;
            Room.forceRestoreLights(rand, world, WIDTH, HEIGHT);
        }
        else if (world[avatarX][avatarY - 1] == Tileset.VACCINE) {
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX][avatarY - 1];
            world[avatarX][avatarY - 1] = Tileset.PLAYER;
            drawWinPage();
        }
        else if (world[avatarX][avatarY - 1] == Tileset.VIRUS) {
            drawLosePage();
        }
    }

    /**
     * Avatar will move to the right by one tile if it is a floor. It will not move if the tile is a floor or light bulb.
     * Player will lose game if avatar hits virus, and win game if avatar hits vaccine.
     */
    public void moveRight() {
        setAvatarPos();
        if (world[avatarX + 1][avatarY] == Tileset.WALL || world[avatarX + 1][avatarY] == Tileset.LIGHTBULB_OFF || world[avatarX + 1][avatarY] == Tileset.LIGHTBULB_ON) {
            return;
        }
        else if (world[avatarX + 1][avatarY] == Tileset.FLOOR || world[avatarX + 1][avatarY] == Tileset.FLOOR_LIT || world[avatarX + 1][avatarY] == Tileset.FLOOR_BRIGHT  || world[avatarX + 1][avatarY] == Tileset.NPC) {
            if ((storedPlayerTile == Tileset.FLOOR_LIT || storedPlayerTile == Tileset.FLOOR_BRIGHT) && !lightStatus(world, WIDTH, HEIGHT)) {
                storedPlayerTile = Tileset.FLOOR;
            }
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX + 1][avatarY];
            world[avatarX + 1][avatarY] = Tileset.PLAYER;
            Room.forceRestoreLights(rand, world, WIDTH, HEIGHT);
        }
        else if (world[avatarX + 1][avatarY] == Tileset.VACCINE) {
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX + 1][avatarY];
            world[avatarX + 1][avatarY] = Tileset.PLAYER;
            drawWinPage();
        }
        else if (world[avatarX + 1][avatarY] == Tileset.VIRUS) {
            drawLosePage();
        }
    }

    /**
     * Avatar will move to the left by one tile if it is a floor. It will not move if the tile is a floor or light bulb.
     * Player will lose game if avatar hits virus, and win game if avatar hits vaccine.
     */
    public void moveLeft() {
        setAvatarPos();
        if (world[avatarX - 1][avatarY] == Tileset.WALL || world[avatarX - 1][avatarY] == Tileset.LIGHTBULB_OFF || world[avatarX - 1][avatarY] == Tileset.LIGHTBULB_ON) {
            return;
        }
        else if (world[avatarX - 1][avatarY] == Tileset.FLOOR || world[avatarX - 1][avatarY] == Tileset.FLOOR_LIT || world[avatarX - 1][avatarY] == Tileset.FLOOR_BRIGHT || world[avatarX - 1][avatarY] == Tileset.NPC ) {
            if ((storedPlayerTile == Tileset.FLOOR_LIT || storedPlayerTile == Tileset.FLOOR_BRIGHT) && !lightStatus(world, WIDTH, HEIGHT)) {
                storedPlayerTile = Tileset.FLOOR;
            }
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX - 1][avatarY];
            world[avatarX - 1][avatarY] = Tileset.PLAYER;
            Room.forceRestoreLights(rand, world, WIDTH, HEIGHT);
        }
        else if (world[avatarX - 1][avatarY] == Tileset.VACCINE) {
            world[avatarX][avatarY] = storedPlayerTile;
            storedPlayerTile = world[avatarX - 1][avatarY];
            world[avatarX - 1][avatarY] = Tileset.PLAYER;
            drawWinPage();
        }
        else if (world[avatarX - 1][avatarY] == Tileset.VIRUS) {
            drawLosePage();
        }
    }

    /**
     * Sets the avatar position variables by iterating through entire world and finding the coordinates of the avatar tile.
     */
    public void setAvatarPos() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == Tileset.PLAYER) {
                    avatarX = x;
                    avatarY = y;
                }
            }
        }
    }

    /**
     *
     * The viruses move every time the avatar attempts to move. This method iterates through the board and finds all the
     * virus tiles to be moved. It randomly selects if the virus should move up, down, left, or right. The associated
     * direction movement methods are written subsequently.
     *
     */

    public void moveViruses(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        for (int x = 1; x < WIDTH-1; x += 1) {
            for (int y = 1; y < HEIGHT-1; y += 1) {
                if (world[x][y] == Tileset.VIRUS) {
                    int direction = randGen.nextInt(4);
                    switch (direction) {
                        case 0: moveVUp(world, x, y); break;
                        case 1: moveVRight(world, x, y); break;
                        case 2: moveVDown(world, x, y); break;
                        case 3: moveVLeft(world, x, y); break;
                    }
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////                 VIRUS MOVEMENTS                   ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *The virus will move one tile in the direction specified if the tile is a floor.
     * If the tile in that direction is an NPC, the NPC will become a virus.
     * If the tile in that direction is the avatar, the player will lose the game.
     */

    public void moveVUp(TETile[][] world, int x, int y) {
        if (world[x][y+1] == Tileset.FLOOR) {
            world[x][y+1] = Tileset.VIRUS;
            world[x][y] = Tileset.FLOOR;
        }
        else if (world[x][y+1] == Tileset.NPC) {
            world[x][y+1] = Tileset.VIRUS;
        }
        else if (world[x][y+1] == Tileset.PLAYER) {
            drawLosePage();
        }
    }
    public void moveVRight(TETile[][] world, int x, int y) {
        if (world[x+1][y] == Tileset.FLOOR) {
            world[x+1][y] = Tileset.VIRUS;
            world[x][y] = Tileset.FLOOR;
        }
        else if (world[x+1][y] == Tileset.NPC) {
            world[x+1][y] = Tileset.VIRUS;
        }
        else if (world[x+1][y] == Tileset.PLAYER) {
            drawLosePage();
        }
    }
    public void moveVDown(TETile[][] world, int x, int y) {
        if (world[x][y-1] == Tileset.FLOOR) {
            world[x][y-1] = Tileset.VIRUS;
            world[x][y] = Tileset.FLOOR;
        }
        else if (world[x][y-1] == Tileset.NPC) {
            world[x][y-1] = Tileset.VIRUS;
        }
        else if (world[x][y-1] == Tileset.PLAYER) {
            drawLosePage();
        }
    }
    public void moveVLeft(TETile[][] world, int x, int y) {
        if (world[x-1][y] == Tileset.FLOOR) {
            world[x-1][y] = Tileset.VIRUS;
            world[x][y] = Tileset.FLOOR;
        }
        else if (world[x-1][y] == Tileset.NPC) {
            world[x-1][y] = Tileset.VIRUS;
        }
        else if (world[x-1][y] == Tileset.PLAYER) {
            drawLosePage();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////                 NON-WORLD PAGES                   ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void drawMenu(){
        StdDraw.setCanvasSize(512, 512);
        StdDraw.clear(Color.BLACK);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenColor(Color.WHITE);
        Font title2 = new Font("Monaco", Font.BOLD, 50);
        Font subtitle2 = new Font("Monaco", Font.BOLD, 22);
        Font nextStep2 = new Font("Monaco", Font.PLAIN, 25);
        StdDraw.setFont(title2);
        StdDraw.text(0.5, 0.8, "CS61B: The Game");
        StdDraw.setFont(subtitle2);
        StdDraw.text(0.5, 0.72, "A COVID World");
        StdDraw.setFont(nextStep2);
        StdDraw.text(0.5, 0.45, "New Game (N)");
        StdDraw.text(0.5, 0.37, "Load Game (L)");
        StdDraw.text(0.5, 0.29, "Lore and Features (F)");
        StdDraw.text(0.5, 0.21, "Replay (R)");
        StdDraw.text(0.5, 0.13, "Quit (Q)");
        Font font2 = new Font("Sans Serif", Font.BOLD, 16);
        StdDraw.setFont(font2);
    }
    public void drawWinPage(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenColor(Color.WHITE);
        Font title = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(title);
        StdDraw.text(0.5, 0.5, "You Win! Exiting game now.");
        StdDraw.show();
        StdDraw.pause(2000);
        quitGame();
    }

    public void drawLosePage(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenColor(Color.WHITE);
        Font title = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(title);
        StdDraw.text(0.5, 0.5, "You Lose. :( Exiting game now.");
        StdDraw.show();
        StdDraw.pause(2000);
        quitGame();
    }

    public void drawLore(){
        StdDraw.setCanvasSize(1200,1200);
        StdDraw.setPenColor(Color.WHITE);
        Font mediom = new Font("Courier New", Font.PLAIN, 25);
        StdDraw.setFont(mediom);
        StdDraw.setXscale(0, 1200);
        StdDraw.setYscale(0, 1350);
        StdDraw.clear(Color.BLACK);
        StdDraw.text(600, 1100, "Lore:");
        StdDraw.text(600, 1060, "School. Work. Happiness. Freedom. My grandmother used to tell me");
        StdDraw.text(600, 1020, "stories about the old days, a time of peace when the CDC kept balance");
        StdDraw.text(600, 980, "between the Common Cold, Sore Throat, Influenza, and Chicken Pox.");
        StdDraw.text(600, 940, "But that all changed when Coronavirus attacked on March 1, 2020.");
        StdDraw.text(600, 900, "");
        StdDraw.text(600, 860, "How to Play & Features:");
        StdDraw.text(600, 820, "Use the WASD keys to move your character.");
        StdDraw.text(600, 780, "Avoid the virus at all costs. It will infect you.");
        StdDraw.text(600, 740, "Reach the vaccine to win the game.");
        StdDraw.text(600, 700, "Disinfecting light sources are spread throughout the map.");
        StdDraw.text(600, 660, "Use the O key to toggle the lights on and off.");
        StdDraw.text(600, 620, "");
        StdDraw.text(600, 580, "Viruses make a move every time you make a move,");
        StdDraw.text(600, 540," either teleporting or moving one square.");
        StdDraw.text(600, 500, "You are safe from the virus if you are in the brightest layer of the lights.");
        StdDraw.text(600, 460, "A virus trapped in the brightest layer of the lights will be unable to move,");
        StdDraw.text(600, 420, "unless you are directly adjacent to the virus.");
        StdDraw.text(600, 380, "Viruses can only enter and move through dark tiles.");
        StdDraw.text(600, 340, "Viruses which touch NPCs will transform the NPC into a new virus.");
        StdDraw.text(600, 300, "You can walk over NPCs.");
        StdDraw.text(600, 260, "");
        StdDraw.text(600, 220, "Press M to return to the Main Menu.");
        StdDraw.show();
    }

    /**
     *
     * Obtains current time to be displayed in HUD. See drawHUD method.
     */
    public String getDateTime() {
        LocalDateTime unformattedTime = LocalDateTime.now();
        DateTimeFormatter timeStyler = DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm:ss");
        return unformattedTime.format(timeStyler);
    }

    public void drawHUD() {
        //TODO: initialize(int width, int height, int xOffset, int yOffset)
        int mXcurr = (int) Math.floor(StdDraw.mouseX());
        int mYcurr = (int) Math.floor(StdDraw.mouseY());
        String mouseTile = "";
        if (mXcurr < WIDTH && mYcurr < HEIGHT) {
            mouseTile = world[(int) mXcurr][(int) mYcurr].description();
        } else {
            mouseTile = "Mouse Out of World";
        }
        StdDraw.clear();
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, HEIGHT - 2, "Pointing to: " + mouseTile + "   " +
                "Current date and time: " + getDateTime() + "         " + "Days since the OUTBREAK: " + daysSinceCovid);
        StdDraw.show();
        Font font = new Font("Sans Serif", Font.BOLD, 16);
        StdDraw.setFont(font);

    }

    public void drawEnterSeedFrame(boolean seedEntered, String arg) { // call when user presses N from keyboard --> take seed and use it
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font mediom = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(mediom);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter a seed number and press S when done");
        if (seedEntered){
            StdDraw.text(WIDTH/2, HEIGHT/2 - 4, "Entered seed: " + arg);
        }
        StdDraw.show();
        Font font = new Font("Sans Serif", Font.BOLD, 16);
        StdDraw.setFont(font);
    }
}