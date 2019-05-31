package src.Domain;

import src.Application.OnGameListener;
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

    private double MAX_ROCK_SPIN;
    private double MAX_ROCK_SPEED;

    private double MIN_ROCK_SPIN;
    private double MIN_ROCK_SPEED;

    private int SCRAP_COUNT;
    private int MISSILE_COUNT;
    private int HYPER_COUNT;

    private int STORM_PAUSE;

    private double MISSILE_PROBABILITY;
    private double MaxRockSpeedTimesFPSPer2;

    // Sprite objects.

    private Ship myShip;
    private UFO myUfo;
    private Asteroid[] myAsteroids;
    private Explosion[] myExplosions;
    private GameData myGameData;
    private GameProperties myGameProperties;
    private OnGameListener myOnGameListener;

    public Game(int highScore, boolean sound, boolean detail, OnGameListener myOnGameListener){
        setMyGameData(GameData.getInstance());
        getMyGameData().setHighScore(highScore);
        this.setSound(sound);
        this.setDetail(detail);
        this.gameSound = Sound.getInstance();
        myGameProperties = GameProperties.getInstance();
        this.myOnGameListener = myOnGameListener;

    }

    public double getMISSILE_PROBABILITY() {
        return MISSILE_PROBABILITY;
    }

    public void setMISSILE_PROBABILITY(double MISSILE_PROBABILITY) {
        this.MISSILE_PROBABILITY = MISSILE_PROBABILITY;
    }

    public double getMaxRockSpeedTimesFPSPer2() {
        return MaxRockSpeedTimesFPSPer2;
    }

    public void setMaxRockSpeedTimesFPSPer2(double maxRockSpeedTimesFPSPer2) {
        MaxRockSpeedTimesFPSPer2 = maxRockSpeedTimesFPSPer2;
    }

    public int getSTORM_PAUSE() {
        return STORM_PAUSE;
    }

    public void setSTORM_PAUSE(int STORM_PAUSE) {
        this.STORM_PAUSE = STORM_PAUSE;
    }

    public int getMISSILE_COUNT() {
        return MISSILE_COUNT;
    }

    public void setMISSILE_COUNT(int MISSILE_COUNT) {
        this.MISSILE_COUNT = MISSILE_COUNT;
    }

    public int getHYPER_COUNT() {
        return HYPER_COUNT;

    }

    public double getMIN_ROCK_SPIN() {
        return MIN_ROCK_SPIN;
    }

    public void setMIN_ROCK_SPIN(double MIN_ROCK_SPIN) {
        this.MIN_ROCK_SPIN = MIN_ROCK_SPIN;
    }

    public double getMIN_ROCK_SPEED() {
        return MIN_ROCK_SPEED;
    }

    public void setMIN_ROCK_SPEED(double MIN_ROCK_SPEED) {
        this.MIN_ROCK_SPEED = MIN_ROCK_SPEED;
    }

    public void setHYPER_COUNT(int HYPER_COUNT) {
        this.HYPER_COUNT = HYPER_COUNT;
    }

    public int getPhotoIndex() {
        return photoIndex;
    }

    public void setPhotoIndex(int photoIndex) {
        this.photoIndex = photoIndex;
    }

    public Sound getGameSound() {
        return gameSound;
    }

    public void setGameSound(Sound gameSound) {
        this.gameSound = gameSound;
    }

    public double getMAX_ROCK_SPIN() {
        return MAX_ROCK_SPIN;
    }

    public void setMAX_ROCK_SPIN(double MAX_ROCK_SPIN) {
        this.MAX_ROCK_SPIN = MAX_ROCK_SPIN;
    }

    public double getMAX_ROCK_SPEED() {
        return MAX_ROCK_SPEED;
    }

    public void setMAX_ROCK_SPEED(double MAX_ROCK_SPEED) {
        this.MAX_ROCK_SPEED = MAX_ROCK_SPEED;
    }

    public int getSCRAP_COUNT() {
        return SCRAP_COUNT;
    }

    public void setSCRAP_COUNT(int SCRAP_COUNT) {
        this.SCRAP_COUNT = SCRAP_COUNT;
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


    public void initGame(Ship ship, UFO ufo,Asteroid[] asteroids, Explosion[] explosions) {


        // Initialize game data and sprites.

        myGameData.setScore(0);

        myGameData.setAsteroidsSpeed(MIN_ROCK_SPEED); // ToDo: speed should be different
        myGameData.setShipsLeft(myGameProperties.getMAX_SHIPS());

        myGameData.setNewShipScore(myGameProperties.getNewShipPoints());
        myGameData.setNewUfoScore(myGameProperties.getNewUfoPoints());

        this.myUfo = ufo;
        this.myAsteroids = asteroids;
        this.myExplosions = explosions;

        this.myShip = ship;
        myShip.init();
        initPhotons();
        initMissile();

        myGameData.setHyperCounter(0);

        initUfo();
        initAsteroids();
        initExplosions();
        playing = true;
        paused = false;
        myGameData.setPhotonTime(System.currentTimeMillis());
    }

    public void updateGame(int direction) {


        if (!playing){
            return;
        }

        moveShip(direction);
        updateShip();


        updateUfo();
        updateMissile();
        updateAsteroids();
        updateExplosions();
        updatePhotons();

    }


    public void endGame() {

        // Stop myShip, flying saucer, guided myMissile and associated sounds.

        playing = false;
        stopShip();
        stopUfo();
        stopMissile();
    }

    // init sprites -----------------

    public void initUfo(){

        // Randomly set flying saucer at left or right edge of the screen.

        myUfo.init();
        // ToDo: myOnGameListener should take over
        gameSound.initUfoSound(playing);
        myGameData.setUfoCounter(myUfo.getCounter());
    }

    public void initMissile() {

        myUfo.initMissile();

        myGameData.setMissileCounter(MISSILE_COUNT);
        if (this.isPlaying()){
            myOnGameListener.onSoundAction("Missile");
        }

    }


    public void initExplosions() {

        int i = 0;
        for (Explosion explosion : myExplosions) {
            explosion.init();
            myGameData.setExplosionCounter(i++, 0);
        }

        myGameData.setExplosionIndex(0);
    }


    public void initAsteroids() {

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
        for (Photon photon: myShip.getMyPhotons()) {
            photon.init();
            photoIndex = 0;
        }
    }

    // update sprites ---------------

    private void updateShip() {
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
                    endGame();
                }

            }


        }

    }


    public void updateUfo() {

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
                for (Photon photon: myShip.getMyPhotons()) {
                    if (photon.isActive() && myUfo.isColliding(photon)) {
                        if (sound) {
                            // ToDo: myOnGameListener should take over
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
                        Math.random() < MISSILE_PROBABILITY){
                    myUfo.getMyMissile().init();
                }
            }
        }

    }

    public void updateExplosions() {

        // Move any isActive explosion debris. Stop explosion when its counter has
        // expired.

        int i = 0;
        for (Explosion explosion : myExplosions) {
            i++;
            if (explosion.isActive()) {
                explosion.advance();
                explosion.render();
                int[] explosionCounter = myGameData.getExplosionCounter();
                if (--explosionCounter[i] < 0)
                    myExplosions[i].setActive(false);
            }
        }
    }

    public void updateAsteroids() {

       // Move any isActive asteroids and check for collisions.

        for (Asteroid asteroid : myAsteroids) {
            if (asteroid.isActive()) {
                asteroid.advance();
                asteroid.render();

                // If hit by photon, kill asteroid and advance score. If asteroid is
                // large, make some smaller ones to replace it.

                Photon[] photons = myShip.getMyPhotons();
                for (int j = 0; j < myGameProperties.getMAX_SHOTS(); j++)
                    if (photons[j].isActive() && asteroid.isActive() && asteroid.isColliding(photons[j])) {
                        int asteroidsLeft = myGameData.getAsteroidsLeft();
                        myGameData.setAsteroidsLeft(--asteroidsLeft);
                        asteroid.setActive(false);
                        photons[j].setActive(false);
                        myOnGameListener.onSoundAction("Explosion");
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
                    killShip();
                }
            }
        }
    }

    public void updatePhotons(){

        // Move any isActive photons. Stop it when its counter has expired.

        for (Photon photon : myShip.getMyPhotons()){
            if (photon.isActive()) {
                if (!photon.advance())
                    photon.render();
                else
                    photon.setActive(false);
            }
        }

    }

    public void updateMissile() {

        // Move the guided missle and check for collision with ship or photon. Stop
        // it when its counter has expired.

        if (myUfo.getMyMissile().isActive()) {
            int missileCounter = myGameData.getMissileCounter();
            if (--missileCounter <= 0){
                myGameData.setMissileCounter(missileCounter);
                myUfo.stopMissile();
            } else {
                guideMissle();
                myUfo.getMyMissile().advance();
                myUfo.getMyMissile().render();
                for (Photon photon: myShip.getMyPhotons()) {
                    if (photon.isActive() && myUfo.getMyMissile().isColliding(photon)) {
                        myOnGameListener.onSoundAction("Crash");
                        explode(myUfo.getMyMissile());
                        myUfo.getMyMissile().stop();
                        myGameData.setScore(myGameData.getScore() + myGameProperties.getMisslePoints());
                    }
                }

                if (myUfo.getMyMissile().isActive() && myShip.isActive() &&
                        myGameData.getHyperCounter() <= 0 && myShip.isColliding(myUfo.getMyMissile())) {
                    killShip();
                }
            }
        }
    }

    // stop sprites ----------------

    public void stopShip() {
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
        if(playing){
            myOnGameListener.onSoundAction("Saucer");
        }

    }


    public void stopMissile() {
        myUfo.getMyMissile().stop();
        myGameData.setMissileCounter(0);
        if(playing){
            myOnGameListener.onSoundAction("StopMissile");
        }
    }


    // other methods -------------------

    private void killShip(){
        myOnGameListener.onSoundAction("Crash");
        explode(myShip);
        stopShip();
        stopUfo();
        myUfo.stopMissile();
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

    public void guideMissle() {

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


    public void explode(Sprite s) {

        int c, i, j;
        int cx, cy;

        // Create sprites for explosion animation. The each individual line segment
        // of the given sprite is used to create a new sprite that will move
        // outward  from the sprite's original position with a random rotation.

        s.render();
        c = 2;
        if (detail || s.getSprite().npoints < 6)
            c = 1;
        for (i = 0; i < s.getSprite().npoints; i += c) {

            int explosionIndex = myGameData.getExplosionIndex();

            myGameData.setExplosionIndex(++explosionIndex);

            if (explosionIndex >= myGameProperties.getMAX_SCRAP())
                explosionIndex = 0;
            myExplosions[explosionIndex].setActive(true);
            j = i + 1;
            if (j >= s.getSprite().npoints)
                j -= s.getSprite().npoints;
            cx = (int) ((s.getShape().xpoints[i] + s.getShape().xpoints[j]) / 2);
            cy = (int) ((s.getShape().ypoints[i] + s.getShape().ypoints[j]) / 2);
            myExplosions[explosionIndex].getShape().addPoint(
                    s.getShape().xpoints[i] - cx,
                    s.getShape().ypoints[i] - cy);
            myExplosions[explosionIndex].getShape().addPoint(
                    s.getShape().xpoints[j] - cx,
                    s.getShape().ypoints[j] - cy);
            myExplosions[explosionIndex].setX(s.getX() + cx);
            myExplosions[explosionIndex].setY(s.getY() + cy);
            myExplosions[explosionIndex].setAngle(s.getAngle());
            myExplosions[explosionIndex].setDeltaAngle(4 * (Math.random() * 2 * MAX_ROCK_SPIN - MAX_ROCK_SPIN));
            myExplosions[explosionIndex].setDeltaX((Math.random() * 2 * MAX_ROCK_SPEED - MAX_ROCK_SPEED + s.getDeltaX()) / 2);
            myExplosions[explosionIndex].setDeltaY((Math.random() * 2 * MAX_ROCK_SPEED - MAX_ROCK_SPEED + s.getDeltaY()) / 2);
            myGameData.setExplosionCounter(explosionIndex,SCRAP_COUNT);
        }
    }

    public void shipFire(int photonIndex) {
        myShip.firePhotons(photonIndex);
    }

    public void hyperSpaceShip(int screenWidth, int screenHeight) {
        myShip.setX(Math.random() * screenWidth);
        myShip.setY(Math.random() * screenHeight);
    }
}
