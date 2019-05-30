package src.Domain;

import src.Application.Sound;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;

import javax.sound.sampled.Clip;

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
        this.gameSound = Sound.getInstance();
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

    public void initGame(Ship ship, UFO ufo, Missile missile,Photon[] photons,Asteroid[] asteroids, Explosion[] explosions, double MIN_ROCK_SPEED) {

        // Initialize game data and sprites.

        myGameData.setScore(0);
        myGameData.setShipsLeft(getMaxShips());
        myGameData.setAsteroidsSpeed(MIN_ROCK_SPEED);
        myGameData.setNewShipScore(getNewShipPoints());
        myGameData.setNewUfoScore(getNewUfoPoints());

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


    public void endGame(int SCRAP_COUNT) {

        // Stop myShip, flying saucer, guided myMissile and associated sounds.

        playing = false;
        stopShip(SCRAP_COUNT);
        stopUfo();
        stopMissile();
    }

    public void initExplosions() {
    }

    public void initAsteroids() {
    }

    public void initUfo(){

        // Randomly set flying saucer at left or right edge of the screen.

        myUfo.init();

        gameSound.initUfoSound(sound);

        ufoCounter = (int) Math.abs(AsteroidsSprite.width / ufo.deltaX);
    }

    public void stopMissile() {
    }

    public void stopUfo() {
    }

    public void initPhotons() {
        for (Photon photon: myPhotons) {
            photon.init();
            photoIndex = 0;
        }
    }

    public void stopShip(int SCRAP_COUNT) {
        myShip.stop();

        myGameData.setShipCounter(SCRAP_COUNT);
        int shipsLeft = myGameData.getShipsLeft();
        if (shipsLeft > 0)
            myGameData.setShipsLeft(shipsLeft--);
    }


    public void evaluateHighScore() {
        if (myGameData.getScore() > myGameData.getHighScore())
            myGameData.setHighScore(myGameData.getScore());
    }


    public void rewardShip() {
        if (myGameData.getScore() > myGameData.getNewShipScore()) {
            myGameData.setNewShipScore( myGameData.getNewShipScore() + getNewShipPoints());
            myGameData.setShipsLeft(myGameData.getShipsLeft() + 1);
        }

    }

    public void getUFO() {

        if (playing && myGameData.getScore() > myGameData.getNewUfoScore() && !myUfo.isActive()) {
            myGameData.setNewUfoScore( myGameData.getNewUfoScore() + getNewUfoPoints());
            myGameData.setUfoPassesLeft(getUfoPasses());
            myUfo.init();
        }
    }

    public void updateGame(int direction, int HYPER_COUNT, int SCRAP_COUNT) {


        if (!playing){
            return;
        }

        moveShip(direction);
        updateShip(HYPER_COUNT, SCRAP_COUNT);
        

        myUfo.update();
        myMissile.update();

        updateAsteroids();
        updateExplosions();
        updatePhotons();

    }

    private void updateShip(int HYPER_COUNT, int SCRAP_COUNT) {
        // Move the ship. If it is currently in hyperspace, advance the countdown.

        if (myShip.isActive()) {
            myShip.advance();
            myShip.render();

            int hyperCounter = myGameData.getHyperCounter();
            if (hyperCounter > 0)
                myGameData.setHyperCounter(hyperCounter--);

            // Update the thruster sprites to match the ship sprite.

            myShip.updateThrusters();


        }else{

            // Ship is exploding, advance the countdown or create a new ship if it is
            // done exploding. The new ship is added as though it were in hyperspace.
            // (This gives the player time to move the ship if it is in imminent
            // danger.) If that was the last ship, end the game.

            int shipCounter = myGameData.getShipCounter();

            if (--shipCounter <= 0){
                // shipCounter is reduced by one, that is moved back to gameData
                myGameData.setShipCounter(shipCounter);
                if (myGameData.getShipsLeft() > 0) {
                    myShip.init();
                    myGameData.setHyperCounter(HYPER_COUNT);
                }
                else{
                    //ToDo: can game end itself??
                    endGame(SCRAP_COUNT);
                }

            }


        }

    }

    private void moveShip(int direction) {
        myShip.move(direction);
    }

    public void updateExplosions() {
    }

    public void updateAsteroids() {
    }

    public void updatePhotons(){

        // Move any isActive photons. Stop it when its counter has expired.

        for (Photon photon : myPhotons){
            if (photon.isActive()) {
                if (!photon.advance())
                    photon.render();
                else
                    photon.setActive(false);
            }
        }

    }


}
