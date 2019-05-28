package src.Domain;

import static src.UI.Screen.FPS;

public class GameProperties {

    private static GameProperties myInstance;

    private GameProperties(){
    }

    public static GameProperties getInstance(){

        if(myInstance == null){
            myInstance = new GameProperties();
        }

        return myInstance;
    }

    private final int MAX_SHOTS =  8;          // Maximum number of sprites
    private final int MAX_ROCKS =  8;          // for photons, asteroids and
    private final int MAX_SCRAP = 40;          // explosions.

    static final int MAX_SHIPS = 3;           // Starting number of ships for
    // each game.
    static final int UFO_PASSES = 3;          // Number of passes for flying
    // saucer per appearance.

    static final int    MIN_ROCK_SIDES =   6; // Ranges for asteroid shape, size
    static final int    MAX_ROCK_SIDES =  16; // speed and rotation.
    static final int    MIN_ROCK_SIZE  =  20;
    static final int    MAX_ROCK_SIZE  =  40;
    static final double MIN_ROCK_SPEED =  40.0 / FPS;
    static final double MAX_ROCK_SPEED = 240.0 / FPS;
    static final double MAX_ROCK_SPIN  = Math.PI / FPS;

    // Ship's rotation and acceleration rates and maximum speed.

    static final double SHIP_ANGLE_STEP = Math.PI / FPS;
    static final double SHIP_SPEED_STEP = 15.0 / FPS;
    static final double MAX_SHIP_SPEED  = 1.25 * MAX_ROCK_SPEED;

    // Probablility of flying saucer firing a missle during any given frame
    // (other conditions must be met).

    static final double MISSLE_PROBABILITY = 0.45 / FPS;
    static final int FIRE_DELAY = 50;         // Minimum number of milliseconds
    // required between photon shots.

    static final int BIG_POINTS    =  25;     // Points scored for shooting
    static final int SMALL_POINTS  =  50;     // various objects.
    static final int UFO_POINTS    = 250;
    static final int MISSLE_POINTS = 500;

    // Number of points the must be scored to earn a new ship or to cause the
    // flying saucer to appear.

    static final int NEW_SHIP_POINTS = 5000;
    static final int NEW_UFO_POINTS  = 2750;

    public int getMaxShots() {
        return MAX_SHOTS;
    }

    public int getMaxRocks() {
        return MAX_ROCKS;
    }

    public int getMaxScrap() {
        return MAX_SCRAP;
    }
}
