package byow.Core;
import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.Point;

public class Room {
    public static void drawWorld(Engine engine) {
        Random randGen = engine.rand;
        TETile[][] world = engine.world;
        int WIDTH = engine.WIDTH;
        int HEIGHT = engine.HEIGHT;
        generateBoard(randGen, world, WIDTH, HEIGHT);
        generateRooms(randGen, world, WIDTH, HEIGHT);

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING /////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initializes border
    public static void generateBoard(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        // Sets perimeter to be a certain boundary tile.
        int pickTile = randGen.nextInt(4);
        for (int x = 0; x < WIDTH; x++) {
            switch (pickTile) {
                case 0:
                    world[x][HEIGHT - 1] = Tileset.MOUNTAIN;
                    world[x][0] = Tileset.MOUNTAIN;
                    break;
                case 1:
                    world[x][HEIGHT - 1] = Tileset.TREE;
                    world[x][0] = Tileset.TREE;
                    break;
                case 2:
                    world[x][HEIGHT - 1] = Tileset.GRASS;
                    world[x][0] = Tileset.GRASS;
                    break;
                default:
                    world[x][HEIGHT - 1] = Tileset.WATER;
                    world[x][0] = Tileset.WATER;
            }
        }
        pickTile = randGen.nextInt(4);
        for (int y = 0; y < HEIGHT; y++) {
            switch (pickTile) {
                case 0:
                    world[WIDTH - 1][y] = Tileset.MOUNTAIN;
                    world[0][y] = Tileset.MOUNTAIN;
                    break;
                case 1:
                    world[WIDTH - 1][y] = Tileset.TREE;
                    world[0][y] = Tileset.TREE;
                    break;
                case 2:
                    world[WIDTH - 1][y] = Tileset.GRASS;
                    world[0][y] = Tileset.GRASS;
                    break;
                default:
                    world[WIDTH - 1][y] = Tileset.WATER;
                    world[0][y] = Tileset.WATER;
            }
        }
    }

    ///// BEGIN ROOM DRAWING ///// BEGIN ROOM DRAWING ///// BEGIN ROOM DRAWING ///// BEGIN ROOM DRAWING ///// BEGIN ROOM DRAWING /////
    public static void generateRooms(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        // Keeps track of certain wall points that will later be used to generate hallways.
        HashMap<Point, String> hallwayDirectionPoint = new HashMap<>();
        // Dictates number of rooms generated. Psuedorandom, with a lower and upper bound.
        // Recommended: (bound: 15) + 25
        int numRooms = randGen.nextInt(15) + 25;
        // Ensures only 1 avatar is spawned in
        boolean avatarSpawned = false;
        // Ensures only 1 vaccine is spawned in
        boolean vaccineSpawned = false;
        // ROOM GENERATOR. Assigns all rooms values, then creates them on the grid.
        for (int roomCount = 0; roomCount < numRooms; roomCount++) {
            int roomWidth = randGen.nextInt(9) + 6;
            int roomHeight = randGen.nextInt(9) + 6;
            int roomXPos = randGen.nextInt(WIDTH - 19) + 3;
            int roomYPos = randGen.nextInt(HEIGHT - 19) + 3;

            for (int widthDrawn = 0; widthDrawn < roomWidth; widthDrawn++) {
                for (int heightDrawn = 0; heightDrawn < roomHeight; heightDrawn++) {
                    if (world[roomXPos + widthDrawn][roomYPos + heightDrawn] == Tileset.NOTHING) {
                        world[roomXPos + widthDrawn][roomYPos + heightDrawn] = Tileset.WALL;
                    }
                }
            }
            for (int widthDrawn = 1; widthDrawn < roomWidth - 1; widthDrawn++) {
                for (int heightDrawn = 1; heightDrawn < roomHeight - 1; heightDrawn++) {
                    world[roomXPos + widthDrawn][roomYPos + heightDrawn] = Tileset.FLOOR;
                }
            }
            createHallwayStartPoints(world, roomXPos, roomYPos, roomWidth, roomHeight, hallwayDirectionPoint);
        }
        generateHallways(hallwayDirectionPoint, randGen, world);
        toggleLights(randGen, world, WIDTH, HEIGHT);
        cleanInaccessibleLights(randGen, world, WIDTH, HEIGHT);
        specialTiles(randGen, world, WIDTH, HEIGHT);
    }

    // Adds specific wall points + directions to a HashMap that will be used to later draw connecting hallways.
    // 1. West (rXP, rH/2) 2. East (rXP+rW, rH/2) 3. South (rW/2, rYP) 4. North (rW/2, rYP+rH)
    public static void createHallwayStartPoints(TETile[][] world, int roomXPos, int roomYPos, int roomWidth, int roomHeight, HashMap<Point, String> hallwayDirectionPoint) {

        int northX = roomXPos + roomWidth / 2;
        int northY = roomYPos + roomHeight - 1;
        int eastX = roomXPos + roomWidth - 1;
        int eastY = roomYPos + roomHeight / 2;
        int southX = roomXPos + roomWidth / 2;
        int southY = roomYPos;
        int westX = roomXPos;
        int westY = roomYPos + roomHeight / 2;
        if (world[northX][northY] == Tileset.WALL) {
            Point northPoint = new Point(northX, northY);
            hallwayDirectionPoint.put(northPoint, "NORTH");
            //world[northX][northY] = Tileset.FLOWER;
        }
        if (world[eastX][eastY] == Tileset.WALL) {
            Point eastPoint = new Point(eastX, eastY);
            hallwayDirectionPoint.put(eastPoint, "EAST");
            //world[eastX][eastY] = Tileset.FLOWER;
        }
        if (world[southX][southY] == Tileset.WALL) {
            Point southPoint = new Point(southX, southY);
            hallwayDirectionPoint.put(southPoint, "SOUTH");
            //world[southX][southY] = Tileset.FLOWER;
        }
        if (world[westX][westY] == Tileset.WALL) {
            Point westPoint = new Point(westX, westY);
            hallwayDirectionPoint.put(westPoint, "WEST");
            //world[westX][westY] = Tileset.FLOWER;
        }
        if (world[(northX+southX)/2][(northY+southY)/2] == Tileset.FLOOR) {
            world[(northX + southX) / 2][(northY + southY) / 2] = Tileset.LIGHTBULB_OFF;
        }
    }

    ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING /////
    public static void generateHallways(HashMap<Point, String> hallwayDirectionPoint, Random randGen, TETile[][] world) {
        for (Map.Entry<Point, String> entries : hallwayDirectionPoint.entrySet()) {
            int hallWidth = randGen.nextInt(2) + 1; // Returns 1 or 2
            int startingX = entries.getKey().x;
            int startingY = entries.getKey().y;
            String direction = entries.getValue();
            // Ensures that a starting point has not already been overwritten. If so, move on to the next midpoint object.
            if (!(world[startingX][startingY] == Tileset.WALL || world[startingX][startingY] == Tileset.FLOWER)) {
                continue;
            }
            // DOCUMENTATION WILL BE FOR NORTH DIRECTION. SAME LOGIC APPLIES TO EACH CARDINAL DIRECTION.
            if (direction.equals("NORTH")) {
                boolean stopped = false;
                // Number of tiles NORTH of starting point that is checked
                int amountNorth = 1;
                // Loop until stopped is true
                while (!stopped) {
                    // If selected position is a FLOOR, create a hallway
                    if (world[startingX][startingY + amountNorth] == Tileset.FLOOR || world[startingX][startingY + amountNorth] == Tileset.BATTERY || world[startingX][startingY + amountNorth] == Tileset.VIRUS || world[startingX][startingY + amountNorth] == Tileset.NPC || world[startingX][startingY + amountNorth] == Tileset.LIGHTBULB_OFF || world[startingX][startingY + amountNorth] == Tileset.LIGHTBULB_ON || world[startingX][startingY + amountNorth] == Tileset.FLOWER) {
                        // Constructs hallway
                        for (int widthDrawn = startingX - hallWidth; widthDrawn <= startingX + 1; widthDrawn++) {
                            for (int heightDrawn = startingY; heightDrawn < startingY + amountNorth; heightDrawn++) {
                                if (world[widthDrawn][heightDrawn] != Tileset.FLOOR)
                                    world[widthDrawn][heightDrawn] = Tileset.WALL;
                            }
                        }
                        for (int widthDrawn = startingX - hallWidth + 1; widthDrawn <= startingX; widthDrawn++) {
                            for (int heightDrawn = startingY; heightDrawn < startingY + amountNorth; heightDrawn++) {
                                world[widthDrawn][heightDrawn] = Tileset.FLOOR;
                            }
                        }
                        break;
                    }
                    // If selected position is a WALL or NOTHING, increment amountNorth/East/South/West
                    else if (world[startingX][startingY + amountNorth] == Tileset.WALL || world[startingX][startingY + amountNorth] == Tileset.NOTHING) {
                        amountNorth++;
                    }
                    // If selected position is a BORDER object, stop the iteration for this midpoint.
                    else if (world[startingX][startingY + amountNorth] == Tileset.TREE || world[startingX][startingY + amountNorth] == Tileset.WATER || world[startingX][startingY + amountNorth] == Tileset.MOUNTAIN || world[startingX][startingY + amountNorth] == Tileset.GRASS) {
                        stopped = true;
                    }
                    else {
                        System.out.println("Error in hallway search algorithm. This should never be printed. " + startingX + ", " + startingY + ": " + direction + " " + world[startingX][startingY]);
                        world[startingX][startingY] = Tileset.FLOWER;
                        break;
                    }
                }
            }
            else if (direction.equals("EAST")) {
                boolean stopped = false;
                int amountEast = 1;
                while (!stopped) {
                    if (world[startingX + amountEast][startingY] == Tileset.FLOOR || world[startingX + amountEast][startingY] == Tileset.BATTERY || world[startingX + amountEast][startingY] == Tileset.VIRUS || world[startingX + amountEast][startingY] == Tileset.NPC || world[startingX + amountEast][startingY] == Tileset.LIGHTBULB_ON || world[startingX + amountEast][startingY] == Tileset.LIGHTBULB_OFF || world[startingX + amountEast][startingY] == Tileset.FLOWER) {
                        for (int widthDrawn = startingX; widthDrawn < startingX + amountEast; widthDrawn++) {
                            for (int heightDrawn = startingY - 1; heightDrawn <= startingY + hallWidth; heightDrawn++) {
                                if (world[widthDrawn][heightDrawn] != Tileset.FLOOR)
                                world[widthDrawn][heightDrawn] = Tileset.WALL;
                            }
                        }
                        for (int widthDrawn = startingX; widthDrawn < startingX + amountEast; widthDrawn++) {
                            for (int heightDrawn = startingY; heightDrawn < startingY + hallWidth; heightDrawn++) {
                                world[widthDrawn][heightDrawn] = Tileset.FLOOR;
                            }
                        }
                        break;
                    }
                    else if (world[startingX + amountEast][startingY] == Tileset.WALL || world[startingX + amountEast][startingY] == Tileset.NOTHING) {
                        amountEast++;
                    }
                    else if (world[startingX + amountEast][startingY] == Tileset.TREE || world[startingX + amountEast][startingY] == Tileset.WATER || world[startingX + amountEast][startingY] == Tileset.MOUNTAIN || world[startingX + amountEast][startingY] == Tileset.GRASS) {
                        stopped = true;
                    }
                    else {
                        System.out.println("Error in hallway search algorithm. This should never be printed. " + startingX + ", " + startingY + ": " + direction + " " + world[startingX][startingY]);
                        world[startingX][startingY] = Tileset.FLOWER;
                        break;
                    }
                }
            }
            else if (direction.equals("SOUTH")) {
                boolean stopped = false;
                int amountSouth = 1;
                while (!stopped) {
                    if (world[startingX][startingY - amountSouth] == Tileset.FLOOR || world[startingX][startingY - amountSouth] == Tileset.BATTERY || world[startingX][startingY - amountSouth] == Tileset.VIRUS || world[startingX][startingY - amountSouth] == Tileset.NPC || world[startingX][startingY - amountSouth] == Tileset.LIGHTBULB_ON || world[startingX][startingY - amountSouth] == Tileset.LIGHTBULB_OFF || world[startingX][startingY - amountSouth] == Tileset.FLOWER) {
                        for (int widthDrawn = startingX - 1; widthDrawn <= startingX + hallWidth; widthDrawn++) {
                            for (int heightDrawn = startingY; heightDrawn > startingY - amountSouth; heightDrawn--) {
                                if (world[widthDrawn][heightDrawn] != Tileset.FLOOR)
                                    world[widthDrawn][heightDrawn] = Tileset.WALL;
                            }
                        }
                        for (int widthDrawn = startingX; widthDrawn < startingX + hallWidth; widthDrawn++) {
                            for (int heightDrawn = startingY; heightDrawn > startingY - amountSouth; heightDrawn--) {
                                world[widthDrawn][heightDrawn] = Tileset.FLOOR;
                            }
                        }
                        break;
                    }
                    else if (world[startingX][startingY - amountSouth] == Tileset.WALL || world[startingX][startingY - amountSouth] == Tileset.NOTHING) {
                        amountSouth++;
                    }
                    else if (world[startingX][startingY - amountSouth] == Tileset.TREE || world[startingX][startingY - amountSouth] == Tileset.WATER || world[startingX][startingY - amountSouth] == Tileset.MOUNTAIN || world[startingX][startingY - amountSouth] == Tileset.GRASS) {
                        stopped = true;
                    }
                    else {
                        System.out.println("Error in hallway search algorithm. This should never be printed. " + startingX + ", " + startingY + ": " + direction + " " + world[startingX][startingY]);
                        world[startingX][startingY] = Tileset.FLOWER;
                        break;
                    }
                }
            }
            else if (direction.equals("WEST")) {
                boolean stopped = false;
                int amountWest = 1;
                while (!stopped) {
                    if (world[startingX - amountWest][startingY] == Tileset.FLOOR || world[startingX - amountWest][startingY] == Tileset.BATTERY || world[startingX - amountWest][startingY] == Tileset.VIRUS || world[startingX - amountWest][startingY] == Tileset.NPC || world[startingX - amountWest][startingY] == Tileset.LIGHTBULB_ON || world[startingX - amountWest][startingY] == Tileset.LIGHTBULB_OFF || world[startingX - amountWest][startingY] == Tileset.FLOWER) {
                        for (int widthDrawn = startingX; widthDrawn > startingX - amountWest; widthDrawn--) {
                            for (int heightDrawn = startingY - hallWidth; heightDrawn <= startingY + 1; heightDrawn++) {
                                if (world[widthDrawn][heightDrawn] != Tileset.FLOOR)
                                    world[widthDrawn][heightDrawn] = Tileset.WALL;
                            }
                        }
                        for (int widthDrawn = startingX; widthDrawn > startingX - amountWest; widthDrawn--) {
                            for (int heightDrawn = startingY - (hallWidth - 1); heightDrawn <= startingY; heightDrawn++) {
                                world[widthDrawn][heightDrawn] = Tileset.FLOOR;
                            }
                        }
                        break;
                    }
                    else if (world[startingX - amountWest][startingY] == Tileset.WALL || world[startingX - amountWest][startingY] == Tileset.NOTHING) {
                        amountWest++;
                    }
                    else if (world[startingX - amountWest][startingY] == Tileset.TREE || world[startingX - amountWest][startingY] == Tileset.WATER || world[startingX - amountWest][startingY] == Tileset.MOUNTAIN || world[startingX - amountWest][startingY] == Tileset.GRASS) {
                        stopped = true;
                    }
                    else {
                        System.out.println("Error in hallway search algorithm. This should never be printed. " + startingX + ", " + startingY + ": " + direction + " " + world[startingX][startingY]);
                        world[startingX][startingY] = Tileset.FLOWER;
                        break;
                    }
                }
            }
            else {
                System.out.println("Error in hallway drawing --> " + direction + " " + startingX + " " + startingY);
            }
        }
    }

    public static void toggleLights(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        // Finds and operates on all lights around the map
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                // If lights are OFF (default), turn ON
                if (world[x][y] == Tileset.LIGHTBULB_OFF) {
                    // Turns LIGHTBULB_OFF tile to LIGHTBULB_ON tile
                    world[x][y] = Tileset.LIGHTBULB_ON;
                    // Turns 5x5 area into bright floor
                    for (int left = x-2; left <= x+2; left++) {
                        for (int bottom = y-2; bottom <= y+2; bottom++) {
                            if (world[left][bottom] == Tileset.FLOOR) {
                                world[left][bottom] = Tileset.FLOOR_LIT;
                            }
                        }
                    }
                    // Turns 3x3 area into dimly lit floor
                    for (int left = x-1; left <= x+1; left++) {
                        for (int bottom = y-1; bottom <= y+1; bottom++) {
                            if (world[left][bottom] == Tileset.FLOOR_LIT) {
                                world[left][bottom] = Tileset.FLOOR_BRIGHT;
                            }
                        }
                    }
                    break;
                }
                // If lights are ON, turn OFF
                else if (world[x][y] == Tileset.LIGHTBULB_ON) {
                    // Turns LIGHTBULB_ON tile to LIGHTBULB_OFF tile
                    world[x][y] = Tileset.LIGHTBULB_OFF;
                    // Turns 5x5 area back into unlit floor
                    for (int left = x-2; left <= x+2; left++) {
                        for (int bottom = y-2; bottom <= y+2; bottom++) {
                            if (world[left][bottom] == Tileset.FLOOR_BRIGHT || world[left][bottom] == Tileset.FLOOR_LIT) {
                                world[left][bottom] = Tileset.FLOOR;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    // After initialization, checks the graph for any lights that are not on, and removes them. This should ONLY be invoked
    public static void cleanInaccessibleLights(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == Tileset.LIGHTBULB_OFF) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }
    public static void forceRestoreLights(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == Tileset.LIGHTBULB_ON) {
                    // Turns 5x5 area into bright floor
                    for (int left = x-2; left <= x+2; left++) {
                        for (int bottom = y-2; bottom <= y+2; bottom++) {
                            if (world[left][bottom] == Tileset.FLOOR) {
                                world[left][bottom] = Tileset.FLOOR_LIT;
                            }
                        }
                    }
                    // Turns 3x3 area into dimly lit floor
                    for (int left = x-1; left <= x+1; left++) {
                        for (int bottom = y-1; bottom <= y+1; bottom++) {
                            if (world[left][bottom] == Tileset.FLOOR_LIT) {
                                world[left][bottom] = Tileset.FLOOR_BRIGHT;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void specialTiles(Random randGen, TETile[][] world, int WIDTH, int HEIGHT) {
        boolean vaccineSpawned = false;
        boolean avatarSpawned = false;
        for (int x = 1; x < WIDTH-1; x += 1) {
            for (int y = 1; y < HEIGHT-1; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    // These values will determine which special tiles, if any, are spawned at a particular x, y coordinate
                    int spawnChances = randGen.nextInt(120);
                    if ((spawnChances == 0 && !vaccineSpawned)) {
                        // 1/40 chance of spawning VACCINE
                        world[x][y] = Tileset.VACCINE;
                        vaccineSpawned = true;
                    } else if ((spawnChances == 3 || spawnChances == 4 || spawnChances == 5 || spawnChances == 6 || spawnChances == 7 || spawnChances == 8) && !avatarSpawned) {
                        // 1/20 chance of spawning AVATAR
                        world[x][y] = Tileset.PLAYER;
                        avatarSpawned = true;
                    } else if (spawnChances == 9 || spawnChances == 10) {
                        // 1/60 chance of spawning NPC
                        world[x][y] = Tileset.NPC;
                    } else if (spawnChances == 11 || spawnChances == 12) {
                        // 1/60 chance of spawning VIRUS
                        world[x][y] = Tileset.VIRUS;
                    } else {
                        // If not a special tile, place a FLOOR tile
                        world[x][y] = Tileset.FLOOR;
                    }
                }
            }
        }
    }
}