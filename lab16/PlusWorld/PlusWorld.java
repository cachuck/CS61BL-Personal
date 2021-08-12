package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        // initialize tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // adds plus to 25, 25
        int xWidth = 14;
        int yHeight = 17;
        int size = 3;

        for (int x = xWidth; x < xWidth + size; x++){
            for (int y = yHeight; y > yHeight - (size * 3); y--){
                world[x][y] = Tileset.FLOWER;
            }
        }
        for (int x = xWidth - size; x < xWidth + (size * 2); x++) {
            for (int y = yHeight - size; y > yHeight - (size * 2); y--) {
                world[x][y] = Tileset.FLOWER;
            }
        }

        //addPlus(19, 20, 3);

        // draws the world to the screen
        ter.renderFrame(world);
    }

//    public static void addPlus(int xWidth, int yHeight, int size) {
//        for (int x = xWidth; x < xWidth + size; x++) {
//            for (int y = yHeight; y > yHeight - (size * 3); y--) {
//                world[x][y] = Tileset.FLOWER;
//            }
//        }
//        for (int x = xWidth - size; x < xWidth + (size * 2); x++) {
//            for (int y = yHeight - size; y > yHeight - (size * 2); y--) {
//                world[x][y] = Tileset.FLOWER;
//            }
//        }
//    }
}
