package src.Domain;

public class GameProperties {

    private static GameProperties myInstance = null;

    private GameProperties(){
    }

    public static GameProperties getInstance(){
        if (myInstance == null) {
            myInstance = new GameProperties();
        }
        return myInstance;
    }

    private final int MAX_SHOTS =  8;          // Maximum number of sprites


    private final int MAX_ROCKS =  8;          // for photons, asteroids and
    private final int MAX_SCRAP = 40;          // explosions.

    private final int MAX_SHIPS = 3;           // Starting number of ships for
    // each game.
    private final int UFO_PASSES = 3;          // Number of passes for flying
    // saucer per appearance.


    private final int    MIN_ROCK_SIDES =   6; // Ranges for asteroid shape, size
    private final int    MAX_ROCK_SIDES =  16; // speed and rotation.
    private final int    MIN_ROCK_SIZE  =  20;
    private final int    MAX_ROCK_SIZE  =  40;

    private final int FIRE_DELAY = 50;         // Minimum number of milliseconds

    // required between photon shots.

    private final int BIG_POINTS    =  25;     // Points scored for shooting
    private final int SMALL_POINTS  =  50;     // various objects.
    private final int UFO_POINTS    = 250;
    private final int MISSILE_POINTS = 500;

    // Number of points the must be scored to earn a new ship or to cause the
    // flying saucer to appear.

    private static final int NEW_SHIP_POINTS = 5000;
    private static final int NEW_UFO_POINTS  = 2750;

    public static GameProperties getMyInstance() {
        return myInstance;
    }

    public static void setMyInstance(GameProperties myInstance) {
        GameProperties.myInstance = myInstance;
    }

    public int getMaxShips() {
        return MAX_SHIPS;
    }

    public int getUfoPasses() {
        return UFO_PASSES;
    }

    public int getMinRockSides() {
        return MIN_ROCK_SIDES;
    }

    public int getMaxRockSides() {
        return MAX_ROCK_SIDES;
    }

    public int getMinRockSize() {
        return MIN_ROCK_SIZE;
    }

    public int getMaxRockSize() {
        return MAX_ROCK_SIZE;
    }

    public int getFireDelay() {
        return FIRE_DELAY;
    }

    public int getBigPoints() {
        return BIG_POINTS;
    }

    public int getSmallPoints() {
        return SMALL_POINTS;
    }

    public int getUfoPoints() {
        return UFO_POINTS;
    }

    public int getMisslePoints() {
        return MISSILE_POINTS;
    }

    public int getNewShipPoints() {
        return NEW_SHIP_POINTS;
    }

    public int getNewUfoPoints() {
        return NEW_UFO_POINTS;
    }

    public int getMaxShots() {
        return getMAX_SHOTS();
    }

    public int getMaxRocks() {
        return getMAX_ROCKS();
    }

    public int getMaxScrap() {
        return getMAX_SCRAP();
    }

    public int getMAX_SHOTS() {
        return MAX_SHOTS;
    }

    public int getMAX_ROCKS() {
        return MAX_ROCKS;
    }

    public int getMAX_SCRAP() {
        return MAX_SCRAP;
    }


    public int getMAX_SHIPS() {
        return MAX_SHIPS;
    }

    public int getUFO_PASSES() {
        return UFO_PASSES;
    }

    public int getMIN_ROCK_SIDES() {
        return MIN_ROCK_SIDES;
    }

    public int getMAX_ROCK_SIDES() {
        return MAX_ROCK_SIDES;
    }

    public int getMIN_ROCK_SIZE() {
        return MIN_ROCK_SIZE;
    }

    public int getMAX_ROCK_SIZE() {
        return MAX_ROCK_SIZE;
    }

    public int getFIRE_DELAY() {
        return FIRE_DELAY;
    }

    public int getBIG_POINTS() {
        return BIG_POINTS;
    }

    public int getSMALL_POINTS() {
        return SMALL_POINTS;
    }

    public int getUFO_POINTS() {
        return UFO_POINTS;
    }

    public int getMISSILE_POINTS() {
        return MISSILE_POINTS;
    }
}
