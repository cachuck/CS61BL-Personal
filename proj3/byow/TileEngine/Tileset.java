package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile PLAYER = new TETile('☺', Color.white, Color.black, "Player", "C:\\Users\\dachr\\OneDrive\\CS61BL\\su21-p92\\proj3\\byow\\TileEngine\\char.png");
    public static final TETile WALL = new TETile('■', new Color(0, 25, 78), Color.darkGray, "Wall");
    public static final TETile WALL2 = new TETile('■', new Color(0, 48, 151), Color.darkGray, "Wall");
    public static final TETile FLOOR = new TETile('·', new Color(99, 232, 99), Color.black, "Floor");
    public static final TETile FLOOR_BRIGHT = new TETile('·', new Color(255, 255, 255), Color.lightGray, "Illuminated Floor");
    public static final TETile FLOOR_LIT = new TETile('·', new Color(174, 255, 174), Color.darkGray, "Dimly Lit Floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "Void");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "Grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "Water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "Flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black, "Locked Door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black, "Unlocked Door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "Sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "Mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "Tree");
    public static final TETile BATTERY = new TETile('✧', Color.pink, Color.black, "Battery", "C:\\Users\\dachr\\OneDrive\\CS61BL\\su21-p92\\proj3\\byow\\TileEngine\\battery.png");
    public static final TETile VIRUS = new TETile('✹', Color.red, Color.black, "Virus", "C:\\Users\\dachr\\OneDrive\\CS61BL\\su21-p92\\proj3\\byow\\TileEngine\\covie2.png");
    public static final TETile VACCINE = new TETile('⤆', Color.cyan, Color.black, "Vaccine", "C:\\Users\\dachr\\OneDrive\\CS61BL\\su21-p92\\proj3\\byow\\TileEngine\\whitevaccine.png");
    public static final TETile NPC = new TETile('㋡', Color.orange, Color.black, "NPC");
    public static final TETile LIGHTBULB_OFF = new TETile('⚪', Color.yellow, Color.black, "Inactive Lightbulb");
    public static final TETile LIGHTBULB_ON = new TETile('⚪', Color.yellow, Color.white, "Active Lightbulb");
}

