package byow.Core;
import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Hallway {

    // TETile[][] world = Engine.world;
    // Random randGen = Engine.rand;

    private int WIDTH; // removed final for main method
    private int HEIGHT;
    public Random randGen;
    public TETile[][] world;

    public void main(String[] args) {
        //Random randGen = new Random(821);
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        Engine engine = new Engine();
        engine.interactWithInputString(args[0]);
        TERenderer ter = new TERenderer();
        ter.initialize(engine.WIDTH, engine.HEIGHT);
        drawWorld(engine);
        ter.renderFrame(engine.world);

    }

    public void drawWorld(Engine engine){
        randGen = engine.rand;
        world = engine.world;
        WIDTH = engine.WIDTH;
        HEIGHT = engine.HEIGHT;
        borders(randGen, world, HEIGHT, WIDTH);
        roomDraw(randGen, world);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING ///// BEGIN GRID DRAWING /////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void borders(Random random, TETile[][] world, int HEIGHT, int WIDTH){
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
    public void roomDraw(Random random, TETile[][] world) {// Keeps track of certain wall points that will later be used to generate hallways.
        HashMap<String, Point> arrayOfWallPiecesThatWillBeTurnedIntoHallways = new HashMap<>();
        // Dictates number of rooms generated. Psuedorandom, with a lower and upper bound.
        int numRooms = randGen.nextInt(15)+30;
        // ROOM GENERATOR. Assigns all rooms values, then creates them on the grid.
        for (int roomCount = 0; roomCount < numRooms; roomCount++) {
            int roomWidth = randGen.nextInt(9) + 6;
            int roomHeight = randGen.nextInt(9) + 6;
            int roomXPos = randGen.nextInt(100) + 3;
            int roomYPos = randGen.nextInt(60) + 3;

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
            // Adds specific wall points + directions to a HashMap that will be used to later draw connecting hallways.
            // 1. West (rXP, rH/2) 2. East (rXP+rW, rH/2) 3. South (rW/2, rYP) 4. North (rW/2, rYP+rH)
            int northX = roomXPos+roomWidth/2;
            int northY = roomYPos+roomHeight-1;
            int eastX = roomXPos+roomWidth-1;
            int eastY = roomYPos+roomHeight/2;
            int southX = roomXPos+roomWidth/2;
            int southY = roomYPos;
            int westX = roomXPos;
            int westY = roomYPos+roomHeight/2;
            if (world[northX][northY] == Tileset.WALL) {
                Point northPoint = new Point(northX, northY);
                arrayOfWallPiecesThatWillBeTurnedIntoHallways.put("NORTH", northPoint);
                world[northX][northY] = Tileset.FLOWER;
            }
            if (world[eastX][eastY] == Tileset.WALL) {
                Point eastPoint = new Point(eastX, eastY);
                arrayOfWallPiecesThatWillBeTurnedIntoHallways.put("EAST", eastPoint);
                world[eastX][eastY] = Tileset.FLOWER;
            }
            if (world[southX][southY] == Tileset.WALL) {
                Point southPoint = new Point(southX, southY);
                arrayOfWallPiecesThatWillBeTurnedIntoHallways.put("SOUTH", southPoint);
                world[southX][southY] = Tileset.FLOWER;
            }
            if (world[westX][westY] == Tileset.WALL) {
                Point westPoint = new Point(westX, westY);
                arrayOfWallPiecesThatWillBeTurnedIntoHallways.put("WEST", westPoint);
                world[westX][westY] = Tileset.FLOWER;
            }
            //END ADD TO MAP
        }
///// END ROOM DRAWING ///// END ROOM DRAWING ///// END ROOM DRAWING ///// END ROOM DRAWING ///// END ROOM DRAWING ///////////////
///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING ///// BEGIN HALL DRAWING /////
        int hallWidth = randGen.nextInt(2) + 1;


///// END HALL DRAWING ///// END HALL DRAWING ///// END HALL DRAWING ///// END HALL DRAWING ///// END HALL DRAWING ///////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// END GRID DRAWING ///// END GRID DRAWING ///// END GRID DRAWING ///// END GRID DRAWING ///// END GRID DRAWING ///////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // draws the world to the screen
        //ter.renderFrame(world);
    }
}