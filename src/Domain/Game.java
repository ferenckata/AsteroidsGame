package src.Domain;

import src.Application.Sound;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;

import static src.Domain.GameProperties.*;

public class Game {

    // Flags for game state and options.

    private boolean paused;
    private boolean playing;
    private boolean sound;
    private boolean detail;
    private int photoIndex;

    private Sound gameSound;

    // Sprite objects.

    private Ship myShip;
    private UFO myUfo;
    private Missile myMissile;
    private Photon[] myPhotons;
    private Asteroid[] myAsteroids;
    private Explosion[] myExplosions;
    private GameData myGameData;
    private GameProperties myGameProperties;

    public Game(int highScore, boolean sound, boolean detail){
        setMyGameData(GameData.getMyInstance());
        getMyGameData().setHighScore(highScore);
        this.setSound(sound);
        this.setDetail(detail);
        myGameProperties = GameProperties.getInstance();
        myPhotons = new Photon[myGameProperties.getMaxShots()];
        myAsteroids = new Asteroid[myGameProperties.getMaxRocks()];
        myExplosions = new Explosion[myGameProperties.getMaxScrap()];

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

    public Ship getMyShip() {
        return myShip;
    }

    public void setMyShip(Ship myShip) {
        this.myShip = myShip;
    }

    public UFO getMyUfo() {
        return myUfo;
    }

    public void setMyUfo(UFO myUfo) {
        this.myUfo = myUfo;
    }

    public Missile getMyMissile() {
        return myMissile;
    }

    public void setMyMissile(Missile myMissile) {
        this.myMissile = myMissile;
    }

    public Photon[] getMyPhotons() {
        return myPhotons;
    }

    public void setMyPhotons(Photon[] myPhotons) {
        this.myPhotons = myPhotons;
    }

    public Asteroid[] getMyAsteroids() {
        return myAsteroids;
    }

    public void setMyAsteroids(Asteroid[] myAsteroids) {
        this.myAsteroids = myAsteroids;
    }

    public Explosion[] getMyExplosions() {
        return myExplosions;
    }

    public void setMyExplosions(Explosion[] myExplosions) {
        this.myExplosions = myExplosions;
    }

    public GameData getMyGameData() {
        return myGameData;
    }

    public void setMyGameData(GameData myGameData) {
        this.myGameData = myGameData;
    }

    public void initGame(Ship ship, UFO ufo, Missile missile,Photon[] photons,Asteroid[] asteroids, Explosion[] explosions) {

        // Initialize game data and sprites.

        myGameData.setScore(0);
        myGameData.setShipsLeft(MAX_SHIPS);
        myGameData.setAsteroidsSpeed(MIN_ROCK_SPEED);
        myGameData.setNewShipScore(NEW_SHIP_POINTS);
        myGameData.setNewUfoScore(NEW_UFO_POINTS);

        this.myShip = ship;
        myShip.init();

        myGameData.setHyperCounter(0);

        this.myPhotons = photons;
        initPhotons();

        stopUfo();
        stopMissile();
        initAsteroids();
        initExplosions();
        playing = true;
        paused = false;
        myGameData.setPhotonTime(System.currentTimeMillis());
    }


    public void endGame() {

        // Stop myShip, flying saucer, guided myMissile and associated sounds.

        playing = false;
        stopShip();
        stopUfo();
        stopMissile();
    }

    private void initExplosions() {
    }

    private void initAsteroids() {
    }

    private void stopMissile() {
    }

    private void stopUfo() {
    }

    private void initPhotons() {
        for (Photon photon: myPhotons) {
            photon.init();
            photoIndex = 0;
        }
    }

    private void stopShip() {
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

        if (playing && myGameData.getScore() > myGameData.getNewUfoScore() && !myUfo.isActive()) {
            myGameData.setNewUfoScore( myGameData.getNewUfoScore() + NEW_UFO_POINTS);
            myGameData.setUfoPassesLeft(UFO_PASSES);
            myUfo.initUfo();
        }
    }

    private void initUfo() {
    }

    public void updateGame() {


        if (!playing){
            return;
        }

        myShip.update();
        updateShip();
        updatePhotons();
        updateUfo();
        updateMissile();
        updateAsteroids();
        updateExplosions();

    }

    private void updateExplosions() {
    }

    private void updateAsteroids() {
    }

    private void updateMissile() {
    }

    private void updateUfo() {
    }

    private void updatePhotons() {
    }

    public void updateShip() {
    }

}
