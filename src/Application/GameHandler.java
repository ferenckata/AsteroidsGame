package src.Application;

import src.Domain.*;
import src.Domain.GameObjects.*;
import src.UI.StartScreen;

import javax.sound.sampled.AudioSystem;
import java.io.File;

public class GameHandler {

    private boolean isSoundLoaded = false;
    private Factory myFactory;
    private GameProperties myGameProperties;
    private Game myGame;
    private Sound myGameSound;
    private StartScreen myStartScreen;
    private static GameHandler myInstance;

    private UFO myUFO;
    private Ship myShip;
    private Missile myMissile;
    private Photon[] myPhotons;
    private Asteroid[] myAsteroids;
    private Explosion[] myExplosions;

    private GameHandler(){
        this.myGameProperties = GameProperties.getInstance();
        this.myFactory = Factory.getInstance();
        this.myStartScreen = StartScreen.getInstance();
    }

    public static GameHandler getInstance() {
        if (myInstance == null){
            myInstance = new GameHandler();
        }
        return myInstance;
    }

    public boolean isSoundLoaded() {
        return isSoundLoaded;
    }

    public void setSoundLoaded(boolean soundLoaded) {
        this.isSoundLoaded = soundLoaded;
    }

    public void createGameObjects(){

        myUFO = myFactory.createUFO();
        myShip = myFactory.createShip();
        myMissile = myFactory.createMissile();
        myPhotons = myFactory.createPhotons(myGameProperties.getMaxShots());
        myAsteroids = myFactory.createAsteroids(myGameProperties.getMaxRocks());
        myExplosions = myFactory.createExplosions(myGameProperties.getMaxScrap());

    }

    public void createNewGame() {

        int highScore = 0;
        boolean sound = true;
        boolean detail = true;

        myGame = myFactory.createGame(highScore,sound,detail);
        myGameSound = myFactory.createGameSound();

        myGame.initGame(myShip,myUFO,myMissile,myPhotons,myAsteroids,myExplosions);

        if (isSoundLoaded){
            myGameSound.stopThrustersSound();
            myGameSound.setThrustersPlaying(false);
        }
        myGame.endGame();

    }


    public void loadSounds(int delay) {

        // Load all sound clips by playing and immediately stopping them. Update
        // counter and total for display.

        try {
            myGameSound.loadSound("Crash");
            myGameSound.loadSound("Explosion");
            myGameSound.loadSound("Fire");
            myGameSound.loadSound("Missile");
            myGameSound.loadSound("Saucer");
            myGameSound.loadSound("Thrusters");
            myGameSound.loadSound("Warp");
        } catch (Exception e) {
            System.out.println("Could not load sounds");
        }

        try {
            myGameSound.runSound("Crash");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Explosion");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Fire");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Missile");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Saucer");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Thrusters");
            myStartScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Warp");
            myStartScreen.repaint();
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {
            System.out.println("Could not run sounds");
        }

    }

    public void updateGame() {
        if (!myGame.isPaused()) {

            // Move and process all sprites.

            myGame.updateGame();


            // Check the score and advance high score, add a new ship or start the
            // flying saucer as necessary.

            myGame.evaluateHighScore();
            myGame.rewardShip();
            myGame.getUFO();

            // If all myAsteroids have been destroyed create a new batch.
            if (asteroidsLeft <= 0)
                if (--asteroidsCounter <= 0)
                    initAsteroids();
        }
    }
}
