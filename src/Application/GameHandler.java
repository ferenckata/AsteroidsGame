package src.Application;

import src.Domain.*;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;
import src.UI.InputOutput;
import src.UI.StartScreen;

import java.awt.event.KeyEvent;

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

        myUFO = myShapeFactory.createUFO();
        myShip = myShapeFactory.createShip(myGameProperties.getMaxShots(),myGameProperties.getMAX_ROCK_SPEED());
        myAsteroids = myShapeFactory.createAsteroids(myGameProperties.getMaxRocks());
        myExplosions = myShapeFactory.createExplosions(myGameProperties.getMaxScrap());

    }

    public void createNewGame() {

        int highScore = 0;
        boolean sound = true;
        boolean detail = true;

        myGame = myGameFactory.createGame(highScore,sound,detail);
        myGameSound = myGameFactory.createGameSound();

        myGame.initGame(myShip,myUFO,myAsteroids,myExplosions);

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


    public void actOnKeyPressed(KeyEvent keyEvent) {

        if (keyEvent.isActionKey()){
            switch (keyEvent.getKeyCode()){
                case KeyEvent.VK_UP :
                    myGame.updateGame(1,myStartScreen.getHyperCount());
                    if (myGame.getMyShip().isActive() && !myGameSound.isThrustersPlaying()){
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.loopThrustersSound();
                        }
                    }
                case KeyEvent.VK_DOWN :
                    myGame.updateGame(2,myStartScreen.getHyperCount());
                    if (myGame.getMyShip().isActive() && !myGameSound.isThrustersPlaying()){
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.loopThrustersSound();
                        }
                    }
                case KeyEvent.VK_RIGHT :
                    myGame.updateGame(4,myStartScreen.getHyperCount());
                case KeyEvent.VK_LEFT :
                    myGame.updateGame(3,myStartScreen.getHyperCount());

            }
        } else {
            char keyChar = Character.toLowerCase(keyEvent.getKeyChar());
            switch (keyChar) {
                case ' ' :
                    if (myGame.getMyShip().isActive()){
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.startFireSound();
                        }
                        myGameData.setPhotonTime(System.currentTimeMillis());
                        myGameData.setPhotonIndex(myGameData.getPhotonIndex()+1);
                        if (myGameData.getPhotonIndex() >= myGameProperties.getMaxShots()){
                            myGameData.setPhotonIndex(0);
                        }
                        myGame.shipFire(myGameData.getPhotonIndex());
                    }
                case 'h' :
                    if (myGame.getMyShip().isActive() && myGameData.getHyperCounter() <= 0){
                        int screenWidth = myStartScreen.getWidth();
                        int screenHeight = myStartScreen.getHeight();
                        myGame.hyperSpaceShip(screenWidth,screenHeight);
                        myGameData.setHyperCounter(myStartScreen.getHyperCount());
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.startWarpSound();
                        }
                    }

                case 'p' :
                    if (myGame.isPaused()){
                        if (myGame.isSound()){
                            if (myGameSound.isMissilePlaying()){
                                myGameSound.loopMissileSound();
                            }
                            if (myGameSound.isSaucerPlaying()){
                                myGameSound.loopSaucerSound();
                            }
                            if (myGameSound.isThrustersPlaying()){
                                myGameSound.loopThrustersSound();
                            }
                        }
                        myGame.setPaused(false);
                    } else {
                        if (myGameSound.isMissilePlaying()){
                            myGameSound.stopSound("Missile");
                        }
                        if (myGameSound.isMissilePlaying()){
                            myGameSound.stopSound("Saucer");
                        }
                        if (myGameSound.isThrustersPlaying()){
                            myGameSound.stopSound("Thrusters");
                        }
                        myGame.setPaused(true);
                    }
                case 'm' :
                    if (isSoundLoaded){
                        if (myGame.isSound()){
                            myGameSound.stopSound("AllSounds");
                            myGame.setSound(false);
                        } else {
                            if (myGameSound.isMissilePlaying() && !myGame.isPaused()){
                                myGameSound.loopMissileSound();
                            }
                            if (myGameSound.isSaucerPlaying() && !myGame.isPaused()){
                                myGameSound.loopSaucerSound();
                            }
                            if (myGameSound.isThrustersPlaying() && !myGame.isPaused()){
                                myGameSound.loopMissileSound();
                            }
                            myGame.setSound(true);
                        }
                    }

                case 'd' :
                    if (myGame.isDetail()){
                        myGame.setDetail(false);
                    } else {
                        myGame.setDetail(true);
                    }
                case 's' :
                    if (isSoundLoaded && !myGame.isPlaying()){
                        myGame.initGame(myShip,myUFO,myAsteroids,myExplosions);
                    }
                case 'x' :
                    if (isSoundLoaded){
                        myGame.endGame();
                    }
            }
        }
    }

    public void actOnKeyRelease() {
        myGameSound.stopThrustersSound();
    }
}
