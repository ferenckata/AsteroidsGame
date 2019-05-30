package src.Domain;

public class GameProperties {

    private static GameProperties myInstance;

    private GameProperties(){
    }

    public static GameProperties getInstance(){

        if(getMyInstance() == null){
            setMyInstance(new GameProperties());
        }

        return getMyInstance();
    }

    private final int MAX_SHOTS =  8;          // Maximum number of sprites
    private final int MAX_ROCKS =  8;          // for photons, asteroids and
    private final int MAX_SCRAP = 40;          // explosions.

    private static final int MAX_SHIPS = 3;           // Starting number of ships for
    // each game.
    private static final int UFO_PASSES = 3;          // Number of passes for flying
    // saucer per appearance.


    private static final int    MIN_ROCK_SIDES =   6; // Ranges for asteroid shape, size
    private static final int    MAX_ROCK_SIDES =  16; // speed and rotation.
    private static final int    MIN_ROCK_SIZE  =  20;
    private static final int    MAX_ROCK_SIZE  =  40;

    private static final int FIRE_DELAY = 50;         // Minimum number of milliseconds

    // required between photon shots.

    private static final int BIG_POINTS    =  25;     // Points scored for shooting
    private static final int SMALL_POINTS  =  50;     // various objects.
    private static final int UFO_POINTS    = 250;
    private static final int MISSLE_POINTS = 500;

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

    public static int getMaxShips() {
        return MAX_SHIPS;
    }

    public static int getUfoPasses() {
        return UFO_PASSES;
    }

    public static int getMinRockSides() {
        return MIN_ROCK_SIDES;
    }

    public static int getMaxRockSides() {
        return MAX_ROCK_SIDES;
    }

    public static int getMinRockSize() {
        return MIN_ROCK_SIZE;
    }

    public static int getMaxRockSize() {
        return MAX_ROCK_SIZE;
    }


    public static int getFireDelay() {
        return FIRE_DELAY;
    }

    public static int getBigPoints() {
        return BIG_POINTS;
    }

    public static int getSmallPoints() {
        return SMALL_POINTS;
    }

    public static int getUfoPoints() {
        return UFO_POINTS;
    }

    public static int getMisslePoints() {
        return MISSLE_POINTS;
    }

    public static int getNewShipPoints() {
        return NEW_SHIP_POINTS;
    }

    public static int getNewUfoPoints() {
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
}
