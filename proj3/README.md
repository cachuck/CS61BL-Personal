# CS61BL: COVID World 

**Partner 1:** `Chris Achuck`

**Partner 2:** `Kusha Gupta`

## Classes Overview
**Room**
Room creates the pseudorandom world from engine's seed by the following steps:

1. Pseudorandomly choose size, location, and number of rooms.
   Double for loop to build a box of walls, and another double loop to populate the inner tiles with floor.
   
2. While constructing rooms, take coordinate points and direction (facing) of each midpoint of each side of every room. Store these values in a HashMap.
   The key will be the coordinate pair, and the value will be the direction string (ie "NORTH")
   
3. From the midpoints, we will attempt to generate hallways off of each point, in the direction they are 'facing'. A loop will continually increase an X or Y value (depending on the direction)
   until some tile is encountered. If it is a WALL tile, construct a hallway. Otherwise, break out of the loop to move on to the next midpoint object.
   
4. If a WALL is encountered, but the tile BEHIND that wall is a WALL or NOTHING, we will not build a hallway. This is to prevent the newly generated hallway from
   leading into the void (NOTHING tiles) or being blocked off after a wall is constructed.
   
5. However, some rooms may be "isolated" due to the midpoint-generated hallways hitting the borders of another room (step 4). The hallways will terminate and the room will remain isolated.
   To combat this issue there is a superhall method, which in the case of 3/4 sides of a midpoint being WALL tiles, will instead generate a small room which should brute force a connection.
   This implementation is causing us bugs.
   
**Engine**
Engine facilitates playing the game. It processes the input keys or string to execute and show the related actions, and draws and displays game frames, such as the main menu and HUD.

**Features**
Primary:
1. Replay (R): Replays the last saved game.
2. Lights (O): "Disinfecting" light sources change how the world is rendered, and can ALL be toggled on/off with a keypress.

Secondary:
1. Real Date/Time displayed in HUD. Must press key for this to change.
2. Randomly determine what the environment/theme of the world will be (biome). Specifically, this refers to the borders of the world.
3. Avatar, virus, vaccine, and non-player characters are images instead of unicode characters.

Extra Credit:
1. Lore which is accessible from menu describing the background and story of the game. (F to access lore; M to return to Menu)
2. Viruses (enemies) move to surrounding tiles (within a small range) on the map every time the player moves.
3. Viruses can INFECT NPC's to turn them into more virus particles.
4. The tiles adjacent to light sources keep viruses out and protect the player from harm.
5. Days Since COVID counter (starts at 527), which increases every move.


## Notes:
Classes and Data Structures
** All rooms and hallways are created with their walls

Pseudorandomly choose size, location, and number of rooms. Double for loop to build a box of walls, and another double loop to populate the inner tiles with floor.

While constructing rooms, take coordinate points and direction (facing) of each midpoint of each side of every room. Store these values in a HashMap. The key will be the coordinate pair, and the value will be the direction string (ie "NORTH")

From the midpoints, we will attempt to generate hallways off of each point, in the direction they are 'facing'. A loop will continually increase an X or Y value (depending on the direction) until some tile is encountered. If it is a WALL tile, construct a hallway. Otherwise, break out of the loop to move on to the next midpoint object.

If a WALL is encountered, but the tile BEHIND that wall is a WALL or NOTHING, we will not build a hallway. This is to prevent the newly generated hallway from leading into the void (NOTHING tiles) or being blocked off after a wall is constructed.

However, some rooms may be "isolated" due to the midpoint-generated hallways hitting the borders of another room (step 4). The hallways will terminate and the room will remain isolated. To combat this issue there is a superhall method, which in the case of 3/4 sides of a midpoint being WALL tiles, will instead generate a small room which should brute force a connection. This implementation is causing us bugs.

============================================================================================================================== HALLWAY REQ:

Starting position (startingX, startingY) MUST be a wall tile.
Ignore walls and 'nothing'. Account for random item pickups. Extend until hits FLOOR, TREE, MOUNTAIN, WATER, or GRASS. TODO: ENSURE HALLWAY WALLS DO NOT OVERWRITE FLOORS "Error in hallway search algorithm. This should never be printed." --> Issue: Hallway hitting FLOWER or BATTERY tile. --> Solution: Add those to ignore case. ============================================================================================================================== Game Concept
Coronaviruses move (or randomly appear) and have to dodge // - when you hit a virus 50% chance of health declining, 10% chance of dying // - when you hit mask you increase your health only if health is less than full
Goal: get to vaccine
Primary feature: lightbulbs
Secondary feature: real date/time
Extra credit: (1) Lore, (2) covid particle hits npc becomes covid particle/masked ppl have random chance of becoming covid everytime there is a move, (3) vaccine = immunity Ideas: answer pop up covid questions and if you get five right you win game as well -- tiles for this? Every 5 moves you get another covid on the board
SIDE NOTE: Covid can move to DARK floor tiles, ie. when lights are off.
============================================================================================================================== Phase 2 Integration Interact with Input String --

Make String Upper Case
if N --> ter.initialize + process seed until S --> after S process moves using move methods --> if WASD, runGame/ if Q (exit) or : and then Q save and quit
if L --> take saved string append to front of entered string (w/o L) and return value when running through same method
do not render world in this method
Interact with Keyboard--

InputSource inputSource = new KeyboardInputSource();
Menu(inputSource) --> show screen --> N or L or Q then do appropriate thing (hasNextKeyTyped) if N --> initialize ter?, ask for seed --> while next key type add # to seed; once S move on --> generate world using seed; render world; place avatar if L --> take saved seed and run it through interact with input string to return world; render world if Q --> exit
THEN while hasnextKeyTyped --> key = nextKeyTyped; return upper case of next key OR Q (exit without save) or if : and then Q--> save and Quit
if key is WASD --> run through method where key is used to change positions (runGame) and render/show after every time?
placeAvatar

given random randomly put avatar somewhere
runGame (for keyboard)

call move methods + render and show each time
moveUp if W moveDown if S moveLeft if A moveRight if D

Phase 2 Notes

std draw window + combine with world ; figure out input source

Create avatar

User pressing W,A,S,D moves the avatar; if hits wall does not move; the COORDINATES of the player are saved to state Pseudocode >>>

Tile tilePlayerCurrrentlyOn = Tileset.FLOOR global variable playerX = ??; global variable playerY = ??; if ("W" is pressed) { if (world[playerX][playerY + 1] == WALL) { DO_NOTHING(); } else if (world[playerX][playerY + 1] == FLOOR) { tilePlayerCurrentlyOn = FLOOR; world[playerX][playerY+1] = Tileset.PLAYER; } else if [FLOWER, etc.]... }

1) input "N593S", "WWW:Q" "LWD" --> TOTAL: WWWWD
   Inputting movements into string results in movements above
   L in string loads program state (including any key presses) --> create files in directory using .txt
   Q (:Q) quits and saves program, if in string AND if inputted via keyboard
   Heads Up display includes text that describes type of tile on mouse pointer
   Methods

save() writes game object to file in directory; do we need to address if it already exists?

load(__) takes game file and loads it when one presses L

quit exits

:Q calls saveGame(), quitGame()

startGame

It's a COVID World Now! Get to the vaccine for the current variant, dodging the viruses which lower your health! Picking up masks will increase your health. Enter a seed to generate your world. (N####S; ) If incorrect format -- show error message in screen ask for seed that generates world?
runGame

InputSource Using the input2

HUD

Real date/time
Health mechanic
Tile you're pointing to: _______
Hallway: Take a random length (more than one unit) with a width of 1 (or 2) tile(s); pseudorandomly select left or right orientation; make sure method includes bounds to confine halls within the allotted space (x, y) & code so that one can have multiple hallways and rooms & halls which do not overlap horizontally
Rooms: Take a random width and random length, side should be more than one unit long.

When generating rooms, randomly take one coordinate from each cardinal side of the room. This point will NOT be near an edge, to account for the width of a hallway.

These coordinates will be placed into a data structure (ArrayList, Map, etc). A Map will allow us to store direction (whether the wall is facing north, east, etc).

Connect If a hall or room is isolated from other maze bodies:

If room tests EVERY direction and does not create a wall, create a super hall.

Checks which quadrant of the map it is in
Creates a hall to midpoint
If left of center, go right, if right of center, to left
Extend hallway all the way to world border -- if and only if left and right are empty, build walls.
If path is blocked by a wall, replace wall with floor
Algorithms
N/A

Persistence
N/A

IDEAS
8 Ambition Points - Primary (6) & Secondary (2)
Features: a) Light sources (primary), real world time on HUD (secondary) b) EC: Portals, health, items (flashlights/batteries), stationary NPC's, lore

Decided: Place random batteries = light sources? in maze, if X amount of batteries collected, you gain immunity if you run into a Griever
If not enough batteries lose health, if no health you die (game ends).

Questions
light source = batteries to collect, when you collect it turns off light how to include key to turn off light? turn off = when you press a key to collect a light source turn on = will flash all lights for certain amount of time and limitd number of flashes

Phase 1 Requirements
a. The world must be a 2D grid, drawn using our tile engine. The tile engine is described in lab 16. b. The world must be pseudorandomly generated. Pseudorandomness is discussed in lab 16. We strongly suggest you rely on the provided randomness utility. c. The generated world must include distinct rooms and hallways, though it may also include outdoor spaces. d. At least some rooms must be rectangular, though you may support other shapes as well. e. Your world generator must be capable of generating hallways that include turns (or equivalently, straight hallways that intersect). f. The world must contain a random number of rooms and hallways. g. The locations of the rooms and hallways must be random. h. The width and height of rooms must be random. i. Hallways should have a width of 1 or 2 tiles and must have a random length. j. Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from unused spaces. k. Rooms and hallways must be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways. l. All rooms must be reachable, i.e. the user can enter every room via some path. m. The world must be substantially different each time, i.e. you should not have the same basic layout with easily predictable features.

#################### SKELLY
Classes and Data Structures Include here any class definitions. For each class, list the instance variables (if any). Include a brief description of each variable and its purpose in the class. Your explanations in this section should be as concise as possible. Leave the full explanation to the following sections. You may cut this section short if you find your document is too wordy.

Algorithms This is where you tell us how your code works. For each class, include a high-level description of the methods in that class. That is, do not include a line-by-line breakdown of your code, but something you would write in a javadoc comment above a method, including any edge cases you are accounting for. We have read the project spec too, so make sure you do not repeat or rephrase what is stated there. This should be a description of how your code accomplishes what is stated in the spec. This is also a place where you can describe how different classes interact with each other (via their methods).

The length of this section depends on the complexity of the task and the complexity of your design. However, simple explanations are preferred. Here are some formatting tips:

For complex tasks, like adding rooms of random sizes at random locations, we recommend that you split your task into parts. Describe your algorithm for each part in a separate section. Start with the simplest component and build up your design, one piece at a time. Different sub-tasks may belong to different classes. If an algorithm uses a method (a subtask) from a different class be sure to mention it. Try to clearly mark titles or names of classes with white space or some other symbols.

Persistence You should only tackle this section after you are done with phase 1. This section should describe how you are going to save the state of a world, and load it again, following the requirements in the spec. Again, try to keep your explanations clear and short. Include all the components your program interacts with - classes, specific methods, and files you may create.















**Game Concept**
School. Work. Happiness. Freedom. My grandmother used to tell me stories about the old days, a time of peace when the CDC kept balance between the Common Cold, Sore Throat, Influenza, and Chicken Pox.
But that all changed when Coronavirus attacked on March 1, 2020.

How to Play & Feature Specifications
Use the WASD keys to move your character.
Avoid the virus at all costs. It will infect you.
Reach the vaccine to win the game.
Disinfecting light sources are spread throughout the map.
Use the O key to toggle the lights on and off.

Viruses make a move every time you make a move, either teleporting or moving one square.
You are safe from the virus if you are in the brightest layer of the lights.
A virus trapped in the brightest layer of the lights will be unable to move, unless you are directly adjacent to the virus.
Viruses can only enter and move through dark tiles.
Viruses which touch NPCs will transform the NPC into a new virus.
You can walk over NPCs.
