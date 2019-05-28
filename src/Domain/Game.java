package src.Domain;

import src.Application.Sound;
import src.Asteroids;

import static src.Domain.GameProperties.*;

public class Game {

    // Flags for game state and options.

    private boolean paused;
    private boolean playing;
    private boolean sound;
    private boolean detail;

    private Sound gameSound;

    // Sprite objects.

    private Ship   ship;
    private UFO   ufo;
    private Missile  missle;
    private Photon[] photons    = new Photon[MAX_SHOTS];
    private Asteroid[] asteroids  = new Asteroid[MAX_ROCKS];
    private Explosion[] explosions = new Explosion[MAX_SCRAP];

    private GameData myGameData;


    public Game(int highScore, boolean sound, boolean detail){
        setMyGameData(GameData.getMyInstance());
        getMyGameData().setHighScore(highScore);
        this.setSound(sound);
        this.setDetail(detail);
    }



    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public UFO getUfo() {
        return ufo;
    }

    public void setUfo(UFO ufo) {
        this.ufo = ufo;
    }

    public Missile getMissle() {
        return missle;
    }

    public void setMissle(Missile missle) {
        this.missle = missle;
    }

    public Photon[] getPhotons() {
        return photons;
    }

    public void setPhotons(Photon[] photons) {
        this.photons = photons;
    }

    public Asteroid[] getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(Asteroid[] asteroids) {
        this.asteroids = asteroids;
    }

    public Explosion[] getExplosions() {
        return explosions;
    }

    public void setExplosions(Explosion[] explosions) {
        this.explosions = explosions;
    }

    public GameData getMyGameData() {
        return myGameData;
    }

    public void setMyGameData(GameData myGameData) {
        this.myGameData = myGameData;
    }

    public void initGame(Ship ship) {

        // Initialize game data and sprites.

        myGameData.setScore(0);
        myGameData.setShipsLeft(MAX_SHIPS);
        myGameData.setAsteroidsSpeed(MIN_ROCK_SPEED);
        myGameData.setNewShipScore(NEW_SHIP_POINTS);
        myGameData.setNewUfoScore(NEW_UFO_POINTS);

        this.ship = ship;
        this.ship.initShip();

        myGameData.setHyperCounter(0);

        initPhotons();
        stopUfo();
        stopMissle();
        initAsteroids();
        initExplosions();
        playing = true;
        paused = false;
        myGameData.setPhotonTime(System.currentTimeMillis());
    }

    public void endGame() {

        // Stop ship, flying saucer, guided missle and associated sounds.

        playing = false;
        stopShip();
        stopUfo();
        stopMissle();
    }


    public void evaluateHighScore() {
        if (myGameData.getScore() > myGameData.getHighScore())
            myGameData.setHighScore(myGameData.getScore());
    }


    public void rewardShip() {
        if (myGameData.getScore() > myGameData.getNewShipScore()) {
            myGameData.setNewShipScore( myGameData.getNewShipScore() + NEW_SHIP_POINTS);
            myGameData.setShipsLeft(myGameData.getShipsLeft() + 1);
        }

    }

    public void getUFO() {

        if (playing && myGameData.getScore() > myGameData.getNewUfoScore() && !ufo.active) {
            myGameData.setNewUfoScore( myGameData.getNewUfoScore() + NEW_UFO_POINTS);
            myGameData.setUfoPassesLeft(UFO_PASSES);
            initUfo();
        }
    }
}
