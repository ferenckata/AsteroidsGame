package src.Domain.Data;

import src.Domain.GameProperties;

public class GameData {

    // Game data.
    private int score;
    private int highScore;
    private int newShipScore;
    private int newUfoScore;

    // Ship data.
    private int shipsLeft;       // Number of ships left in game, including current one.
    private int shipCounter;     // Timer counter for ship explosion.
    private int hyperCounter;    // Timer counter for hyperspace.

    // Photon data.
    private int   photonIndex;    // Index to next available photon sprite.
    private long  photonTime;     // Time value used to keep firing rate constant.

    // Flying saucer data.
    private int ufoPassesLeft;    // Counter for number of flying saucer passes.
    private int ufoCounter;       // Timer counter used to track each flying saucer pass.

    // Missile data.
    private int missileCounter;    // Counter for life of missle.

    // Asteroid data.
    private boolean[] asteroidIsSmall;    // Asteroid size flag.
    private int       asteroidsCounter;                            // Break-time counter.
    private double    asteroidsSpeed;                              // Asteroid speed.
    private int       asteroidsLeft;                               // Number of isActive asteroids.

    // Explosion data.
    private int[] explosionCounter;  // Time counters for explosions.

    private int   explosionIndex;                         // Next available explosion sprite.

    private static GameData myInstance = null;
    private GameProperties myGameProperties;

    private GameData(){
        this.myGameProperties = GameProperties.getInstance();
        asteroidIsSmall = new boolean[myGameProperties.getMaxRocks()];
        explosionCounter = new int[myGameProperties.getMaxScrap()];
    }

    public static GameData getInstance(){
        if(myInstance==null){
            myInstance = new GameData();
        }
        return myInstance;
    }

    public int[] getExplosionCounter() {
        return explosionCounter;
    }

    public void setExplosionCounter(int i, int num) {
        this.explosionCounter[i] = num;
    }

    public int getExplosionIndex() {
        return explosionIndex;
    }

    public void setExplosionIndex(int explosionIndex) {
        this.explosionIndex = explosionIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMissileCounter() {
        return missileCounter;
    }

    public void setMissileCounter(int missileCounter) {
        this.missileCounter = missileCounter;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getNewShipScore() {
        return newShipScore;
    }

    public void setNewShipScore(int newShipScore) {
        this.newShipScore = newShipScore;
    }

    public int getNewUfoScore() {
        return newUfoScore;
    }

    public void setNewUfoScore(int newUfoScore) {
        this.newUfoScore = newUfoScore;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    public void setShipsLeft(int shipsLeft) {
        this.shipsLeft = shipsLeft;
    }

    public int getShipCounter() {
        return shipCounter;
    }

    public void setShipCounter(int shipCounter) {
        this.shipCounter = shipCounter;
    }

    public int getHyperCounter() {
        return hyperCounter;
    }

    public void setHyperCounter(int hyperCounter) {
        this.hyperCounter = hyperCounter;
    }

    public int getPhotonIndex() {
        return photonIndex;
    }

    public void setPhotonIndex(int photonIndex) {
        this.photonIndex = photonIndex;
    }

    public long getPhotonTime() {
        return photonTime;
    }

    public void setPhotonTime(long photonTime) {
        this.photonTime = photonTime;
    }

    public int getUfoPassesLeft() {
        return ufoPassesLeft;
    }

    public void setUfoPassesLeft(int ufoPassesLeft) {
        this.ufoPassesLeft = ufoPassesLeft;
    }

    public int getUfoCounter() {
        return ufoCounter;
    }

    public void setUfoCounter(int ufoCounter) {
        this.ufoCounter = ufoCounter;
    }

    public double getAsteroidsSpeed() {
        return asteroidsSpeed;
    }

    public void setAsteroidsSpeed(double asteroidsSpeed) {
        this.asteroidsSpeed = asteroidsSpeed;
    }

    public int getAsteroidsLeft() {
        return asteroidsLeft;
    }

    public int getAsteroidsCounter() {
        return asteroidsCounter;
    }

    public void setAsteroidsCounter(int asteroidsCounter) {
        this.asteroidsCounter = asteroidsCounter;
    }

    public void setAsteroidsLeft(int asteroidsLeft) {
        this.asteroidsLeft = asteroidsLeft;
    }
}
