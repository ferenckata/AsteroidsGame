package src.Application;

import src.Domain.*;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;
import src.UI.InputOutput;
import src.UI.StartScreen;

import javax.sound.sampled.AudioSystem;
import java.io.File;

public class GameHandler {

    private boolean isSoundLoaded = false;
    private ShapeFactory myShapeFactory;
    private GameFactory myGameFactory;
    private GameProperties myGameProperties;
    private GameData myGameData;
    private Game myGame;
    private Sound myGameSound;
    private StartScreen myStartScreen;
    private InputOutput myIO;
    private static GameHandler myInstance;

    private UFO myUFO;
    private Ship myShip;
    private Missile myMissile;
    private Photon[] myPhotons;
    private Asteroid[] myAsteroids;
    private Explosion[] myExplosions;

    private GameHandler(){
        this.myGameProperties = GameProperties.getInstance();
        this.myGameData = GameData.getMyInstance();
        this.myShapeFactory = ShapeFactory.getInstance();
        this.myGameFactory = GameFactory.getInstance();
        this.myStartScreen = StartScreen.getInstance();
        this.myIO = InputOutput.getInstance();
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

        myUFO = myShapeFactory.createUFO(myStartScreen.getMaxRockSpeed(),myGameProperties.getUfoPoints(),myGameProperties.getMaxShots(),myStartScreen.getMissleProbability());
        myShip = myShapeFactory.createShip();
        myMissile = myShapeFactory.createMissile();
        myPhotons = myShapeFactory.createPhotons(myGameProperties.getMaxShots());
        myAsteroids = myShapeFactory.createAsteroids(myGameProperties.getMaxRocks(),myGameProperties.getMinRockSides(),myGameProperties.getMaxRockSides(),myGameProperties.getMinRockSize(),myGameProperties.getMaxRockSize(),myStartScreen.getMaxRockSpin(), myGameData.getAsteroidsSpeed());
        myExplosions = myShapeFactory.createExplosions(myGameProperties.getMaxScrap());

    }

    public void createNewGame() {

        int highScore = 0;
        boolean sound = true;
        boolean detail = true;

        myGame = myGameFactory.createGame(highScore,sound,detail);
        myGameSound = myGameFactory.createGameSound();

        myGame.initGame(myShip,myUFO,myMissile,myPhotons,myAsteroids,myExplosions,myStartScreen.getMinRockSpeed());
        myGame.setHYPER_COUNT(myStartScreen.getHyperCount());
        myGame.setMAX_ROCK_SPEED(myStartScreen.getMaxRockSpeed());
        myGame.setMAX_ROCK_SPIN(myStartScreen.getMaxRockSpin());
        myGame.setMaxRockSpeedTimesFPSPer2(myStartScreen.getMaxRockSpeedTimesFPSPer2());
        myGame.setMISSLE_COUNT(myStartScreen.getMissleCount());
        myGame.setSCRAP_COUNT(myStartScreen.getScrapCount());
        myGame.setMISSLE_PROBABILITY(myStartScreen.getMissleProbability());
        myGame.setSTORM_PAUSE(myStartScreen.getStormPause());


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

    private int getDirection(){
        int direction = 0;

        if(myIO.isUp()){
            direction = 1;
        }else if(myIO.isDown()){
            direction = 2;
        }else if(myIO.isRight()){
            direction = 3;
        }else if(myIO.isLeft()){
            direction = 4;
        }

        return direction;
    }

    public void updateGame() {
        if (!myGame.isPaused()) {

            // Move and process all sprites.

            // move ship

            int direction = getDirection();

            myGame.updateGame(direction);


            // Check the score and advance high score, add a new ship or start the
            // flying saucer as necessary.

            myGame.evaluateHighScore();
            myGame.rewardShip();
            myGame.getUFO();

            // If all myAsteroids have been destroyed create a new batch.
            if (myGameData.getAsteroidsLeft() <= 0){
                int asteroidsCounter = myGameData.getAsteroidsCounter();
                if (-- asteroidsCounter <= 0){
                    myGame.initAsteroids();
                }
            }
        }
    }
}
