package src.Domain;

import src.Application.Sound;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;

import javax.sound.sampled.Clip;

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
        myGameData.setShipsLeft(myGameProperties.getMaxShips());
        myGameData.setAsteroidsSpeed(MIN_ROCK_SPEED);
        myGameData.setNewShipScore(myGameProperties.getNewShipPoints());
        myGameData.setNewUfoScore(myGameProperties.getNewUfoPoints());

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

    public void updateGame(int direction, int HYPER_COUNT, int SCRAP_COUNT, double MAX_ROCK_SPEED, double MISSLE_PROBABILITY,double MaxRockSpeedTimesFPSPer2) {


        if (!playing){
            return;
        }

        moveShip(direction);
        updateShip(HYPER_COUNT, SCRAP_COUNT);


        updateUfo(MAX_ROCK_SPEED,MISSLE_PROBABILITY,MaxRockSpeedTimesFPSPer2);

        updateAsteroids();
        updateExplosions();
        updatePhotons();

    }


    public void endGame(int SCRAP_COUNT) {

        // Stop myShip, flying saucer, guided myMissile and associated sounds.

        playing = false;
        stopShip(SCRAP_COUNT);
        stopUfo();
        stopMissile();
    }

    // init sprites -----------------

    public void initUfo(){

        // Randomly set flying saucer at left or right edge of the screen.

        myUfo.init();

        gameSound.initUfoSound(sound);

        myGameData.setUfoCounter(myUfo.getCounter());
    }

    public void initMissle(int MISSLE_COUNT) {

        myUfo.initMissile();

        myGameData.setMissleCounter(MISSLE_COUNT);

        if (sound) {
            missleSound.start();
            missleSound.loop(Clip.LOOP_CONTINUOUSLY);
        }

        misslePlaying = true;
    }

    public void initExplosions() {
    }



    public void initAsteroids(int STORM_PAUSE, double MAX_ROCK_SPEED) {

        // Create random shapes, positions and movements for each asteroid.

        for (Asteroid asteroid: myAsteroids ) {

            // Create a jagged shape for the asteroid and give it a random rotation.

            // set up normal sized asteroid
            asteroid.setUpAsteroid(false, 0, 0);
        }

        myGameData.setAsteroidsCounter(STORM_PAUSE);
        myGameData.setAsteroidsLeft(myGameProperties.getMAX_ROCKS());
        double asteroidsSpeed = myGameData.getAsteroidsSpeed();
        if (asteroidsSpeed < MAX_ROCK_SPEED)
            myGameData.setAsteroidsSpeed(asteroidsSpeed + 0.5);
    }


    public void initSmallAsteroids(int n) {
        // Create one or two smaller asteroids from a larger one using inactive
        // asteroids. The new asteroids will be placed in the same position as the
        // old one but will have a new, smaller shape and new, randomly generated
        // movements.

        int count = 0;
        int i = 0;
        double tempX = myAsteroids[n].getX();
        double tempY = myAsteroids[n].getY();
        do {
            if (!myAsteroids[i].isActive()) {

                // set up small asteroids
                myAsteroids[i].setUpAsteroid(true, tempX, tempY);

                count++;
                myGameData.setAsteroidsLeft(myGameData.getAsteroidsLeft() + 1);
            }
            i++;
        } while (i < myGameProperties.getMAX_ROCKS() && count < 2);
    }


    public void initPhotons() {
        for (Photon photon: myPhotons) {
            photon.init();
            photoIndex = 0;
        }
    }

    // update sprites ---------------

    private void updateShip(int HYPER_COUNT, int SCRAP_COUNT) {
        // Move the ship. If it is currently in hyperspace, advance the countdown.

        if (myShip.isActive()) {
            myShip.advance();
            myShip.render();

            int hyperCounter = myGameData.getHyperCounter();
            if (hyperCounter > 0)
                myGameData.setHyperCounter(--hyperCounter);

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


    public void updateUfo(double MISSLE_PROBABILITY, double MaxRockSpeedTimesFPSPer2) {

        // Move the flying saucer and check for collision with a photon. Stop it
        // when its counter has expired.

        if (myUfo.isActive()) {
            int ufoCounter = myGameData.getUfoCounter();
            if (--ufoCounter <= 0) {
                myGameData.setUfoCounter(ufoCounter);
                int ufoPassesLeft = myGameData.getUfoPassesLeft();
                if (--ufoPassesLeft > 0){
                    myGameData.setUfoPassesLeft(ufoPassesLeft);
                    initUfo();
                }else{
                    stopUfo();
                }
            }
            if (myUfo.isActive()) {
                myUfo.advance();
                myUfo.render();
                for (Photon photon: myPhotons) {
                    if (photon.isActive() && myUfo.isColliding(photon)) {
                        if (sound) {
                            gameSound.initCrashSound();
                        }
                        explode(myUfo);
                        stopUfo();
                        myGameData.setScore(myGameData.getScore() + myGameProperties.getUfoPoints());

                    }
                }

                // On occassion, fire a missle at the ship if the saucer is not too
                // close to it.

                int d = (int) Math.max(Math.abs(myUfo.getX() - myShip.getX()), Math.abs(myUfo.getY() - myShip.getY()));
                if (myShip.isActive() && myGameData.getHyperCounter() <= 0 &&
                        myUfo.isActive() && !myUfo.getMyMissile().isActive() &&
                        d > MaxRockSpeedTimesFPSPer2 &&
                        Math.random() < MISSLE_PROBABILITY){
                    myUfo.getMyMissile().init();
                }
            }
        }

    }

    public void updateExplosions() {
    }

    public void updateAsteroids(int SCRAP_COUNT) {

       // Move any isActive asteroids and check for collisions.

        for (Asteroid asteroid : myAsteroids) {
            if (asteroid.isActive()) {
                asteroid.advance();
                asteroid.render();

                // If hit by photon, kill asteroid and advance score. If asteroid is
                // large, make some smaller ones to replace it.

                for (int j = 0; j < myGameProperties.getMAX_SHOTS(); j++)
                    if (photons[j].active && asteroid.isActive() && asteroid.isColliding(photons[j])) {
                        int asteroidsLeft = myGameData.getAsteroidsLeft();
                        myGameData.setAsteroidsLeft(--asteroidsLeft);
                        asteroid.setActive(false);
                        photons[j].active = false;
                        if (sound) {
                            explosionSound.setFramePosition(0);
                            explosionSound.start();
                        }

                        explode(asteroid);
                        if (!asteroid.getIsSmall()) {
                            myGameData.setScore(myGameData.getScore() + myGameProperties.getBigPoints());
                            initSmallAsteroids((int) Math.random()*2);
                        } else
                            myGameData.setScore(myGameData.getScore() + myGameProperties.getSmallPoints());
                    }

                // If the ship is not in hyperspace, see if it is hit.

                if (myShip.isActive() && myGameData.getHyperCounter() <= 0 &&
                        asteroid.isActive() && asteroid.isColliding(myShip)) {
                    killShip(SCRAP_COUNT);
                }
            }
        }
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

    public void updateMissle(int SCRAP_COUNT, int MAX_ROCK_SPEED) {

        // Move the guided missle and check for collision with ship or photon. Stop
        // it when its counter has expired.

        if (myUfo.getMyMissile().isActive()) {
            int missleCounter = myGameData.getMissleCounter();
            if (--missleCounter <= 0){
                myGameData.setMissleCounter(missleCounter);
                myUfo.stopMissle();
            } else {
                guideMissle(MAX_ROCK_SPEED);
                myUfo.getMyMissile().advance();
                myUfo.getMyMissile().render();
                for (Photon photon: myPhotons) {
                    if (photon.isActive() && myUfo.getMyMissile().isColliding(photon)) {
                        if (sound) {
                            crashSound.setFramePosition(0);
                            crashSound.start();
                        }
                        explode(myUfo.getMyMissile());
                        myUfo.getMyMissile().stop();
                        myGameData.setScore(myGameData.getScore() + myGameProperties.getMisslePoints());
                    }
                }

                if (myUfo.getMyMissile().isActive() && myShip.isActive() &&
                        myGameData.getHyperCounter() <= 0 && myShip.isColliding(myUfo.getMyMissile())) {
                    killShip(SCRAP_COUNT);
                }
            }
        }
    }

    // stop sprites ----------------

    public void stopShip(int SCRAP_COUNT) {
        myShip.stop();

        myGameData.setShipCounter(SCRAP_COUNT);
        int shipsLeft = myGameData.getShipsLeft();
        if (shipsLeft > 0)
            myGameData.setShipsLeft(--shipsLeft);
    }

    public void stopUfo() {
        myUfo.stop();
        myGameData.setUfoCounter(0);
        myGameData.setUfoPassesLeft(0);
            if (loaded){
                saucerSound.stop();
            }
            saucerPlaying = false;
    }


    public void stopMissile() {
        myUfo.getMyMissile().stop();
        myGameData.setMissleCounter(0);
        if (loaded)
            missleSound.stop();
        misslePlaying = false;
    }


    // other methods -------------------

    private void killShip(int SCRAP_COUNT){
        if (sound) {
            crashSound.setFramePosition(0);
            crashSound.start();
        }
        explode(myShip);
        stopShip(SCRAP_COUNT);
        stopUfo();
        myUfo.stopMissle();
    }

    public void evaluateHighScore() {
        if (myGameData.getScore() > myGameData.getHighScore())
            myGameData.setHighScore(myGameData.getScore());
    }


    public void rewardShip() {
        if (myGameData.getScore() > myGameData.getNewShipScore()) {
            myGameData.setNewShipScore( myGameData.getNewShipScore() + myGameProperties.getNewShipPoints());
            myGameData.setShipsLeft(myGameData.getShipsLeft() + 1);
        }

    }

    public void getUFO() {

        if (playing && myGameData.getScore() > myGameData.getNewUfoScore() && !myUfo.isActive()) {
            myGameData.setNewUfoScore( myGameData.getNewUfoScore() + myGameProperties.getNewUfoPoints());
            myGameData.setUfoPassesLeft(myGameProperties.getUfoPasses());
            initUfo();
        }
    }


    private void moveShip(int direction) {
        myShip.move(direction);
    }

    public void guideMissle(int MAX_ROCK_SPEED) {

        if (!myShip.isActive() || myGameData.getHyperCounter() > 0)
            return;

        // Find the angle needed to hit the ship.

        double dx = myShip.getX() - myUfo.getMyMissile().getX();
        double dy = myShip.getY() - myUfo.getMyMissile().getY();

        double angle = 0.0;

        if (dx == 0 && dy == 0){
            angle = 0;
        }

        if (dx == 0) {
            if (dy < 0)
                angle = -Math.PI / 2;
            else
                angle = Math.PI / 2;
        }
        else {
            angle = Math.atan(Math.abs(dy / dx));
            if (dy > 0)
                angle = -angle;
            if (dx < 0)
                angle = Math.PI - angle;
        }

        // Adjust angle for screen coordinates.

        myUfo.getMyMissile().setAngle(angle - Math.PI / 2);

        // Change the missle's angle so that it points toward the ship.

        myUfo.getMyMissile().setDeltaX( 0.75 * MAX_ROCK_SPEED * -Math.sin(myUfo.getMyMissile().getAngle()));
        myUfo.getMyMissile().setDeltaY(0.75 * MAX_ROCK_SPEED *  Math.cos(myUfo.getMyMissile().getAngle()));
    }

}
