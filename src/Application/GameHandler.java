package src.Application;

import src.Domain.*;
import src.Domain.Data.GameData;
import src.Domain.GameObjects.*;
import src.UI.GameScreen;
import src.UI.InputOutput;
import java.awt.event.KeyEvent;

public class GameHandler implements OnGameListener{

    private boolean isSoundLoaded = false;
    private ShapeFactory myShapeFactory = ShapeFactory.getInstance();
    private GameFactory myGameFactory = GameFactory.getInstance();
    private GameProperties myGameProperties = GameProperties.getInstance();
    private GameData myGameData = GameData.getInstance();
    private Game myGame;
    private Sound myGameSound;
    private GameScreen myGameScreen = GameScreen.getInstance();
    private InputOutput myIO = InputOutput.getInstance();
    private static GameHandler myInstance = null;
    private UFO myUFO;
    private Ship myShip;
    private Asteroid[] myAsteroids;
    private Explosion[] myExplosions;

    private GameHandler(){
        System.out.println("GameHandler");
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
        myUFO = myShapeFactory.createUFO(myGameScreen.getMaxRockSpeed(),myGameProperties.getUfoPoints(),myGameProperties.getMaxShots(), myGameScreen.getMissileProbability());
        myShip = myShapeFactory.createShip(myGameProperties.getMAX_SHOTS(), myGameScreen.getMaxRockSpeed());
        myAsteroids = myShapeFactory.createAsteroids(myGameProperties.getMaxRocks(),myGameProperties.getMinRockSides(),myGameProperties.getMaxRockSides(),myGameProperties.getMinRockSize(),myGameProperties.getMaxRockSize(), myGameScreen.getMaxRockSpin(), myGameData.getAsteroidsSpeed());
        myExplosions = myShapeFactory.createExplosions(myGameProperties.getMaxScrap());
    }

    public void createNewGame() {

        int highScore = 0;
        boolean sound = true;
        boolean detail = true;

        myGame = myGameFactory.createGame(highScore,sound,detail,this);
        myGameSound = myGameFactory.createGameSound();


        myGame.initGame(myShip,myUFO,myAsteroids,myExplosions);
        myGame.setHYPER_COUNT(myGameScreen.getHyperCount());
        myGame.setMAX_ROCK_SPEED(myGameScreen.getMaxRockSpeed());
        myGame.setMAX_ROCK_SPIN(myGameScreen.getMaxRockSpin());
        myGame.setMaxRockSpeedTimesFPSPer2(myGameScreen.getMaxRockSpeedTimesFPSPer2());
        myGame.setMISSILE_COUNT(myGameScreen.getMissileCount());
        myGame.setSCRAP_COUNT(myGameScreen.getScrapCount());
        myGame.setMISSILE_PROBABILITY(myGameScreen.getMissileProbability());
        myGame.setSTORM_PAUSE(myGameScreen.getStormPause());



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
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Explosion");
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Fire");
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Missile");
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Saucer");
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Thrusters");
            myGameScreen.repaint();
            Thread.sleep(delay);

            myGameSound.runSound("Warp");
            myGameScreen.repaint();
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
                    myGame.updateGame(1);
                    if (myGame.getMyShip().isActive() && !myGameSound.isThrustersPlaying()){
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.loopThrustersSound();
                        }
                    }
                case KeyEvent.VK_DOWN :
                    myGame.updateGame(2);
                    if (myGame.getMyShip().isActive() && !myGameSound.isThrustersPlaying()){
                        if (myGame.isSound() && !myGame.isPaused()){
                            myGameSound.loopThrustersSound();
                        }
                    }
                case KeyEvent.VK_RIGHT :
                    myGame.updateGame(4);
                case KeyEvent.VK_LEFT :
                    myGame.updateGame(3);

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
                        int screenWidth = myGameScreen.getWidth();
                        int screenHeight = myGameScreen.getHeight();
                        myGame.hyperSpaceShip(screenWidth,screenHeight);
                        myGameData.setHyperCounter(myGameScreen.getHyperCount());
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

    public boolean isGameDetail() {
        return myGame.isDetail();
    }

    public int getMaxShots() {
        return myGameProperties.getMaxShots();
    }

    public Photon[] getPhotons() {
        return myGame.getMyShip().getMyPhotons();
    }

    public Missile getMissile() {
        return myGame.getMyUfo().getMyMissile();
    }

    public int getMaxRocks() {
        return myGameProperties.getMaxRocks();
    }

    public Asteroid[] getAsteroids() {
        return myGame.getMyAsteroids();
    }

    public UFO getUfo() {
        return myGame.getMyUfo();
    }

    public int getHyperCounter() {
        return myGameData.getHyperCounter();
    }

    public Ship getShip() {
        return myGame.getMyShip();
    }

    public boolean isGamePaused() {
        return myGame.isPaused();
    }

    public boolean isUpKeyInUse() {
        return myIO.isUp();
    }

    public Thruster getFwdThruster() {
        return myShip.getFwdThruster();
    }

    public Thruster getRevThruster(){
        return myShip.getRevThruster();
    }

    public boolean isDownKeyInUse() {
        return myIO.isDown();
    }

    public int getMaxScrap() {
        return myGameProperties.getMaxScrap();
    }

    public Explosion[] getExplosions() {
        return myGame.getMyExplosions();
    }

    public int[] getExplosionCounter() {
        return myGameData.getExplosionCounter();
    }

    public int getScore() {
        return myGameData.getScore();
    }

    public int getShipsLeft() {
        return myGameData.getShipsLeft();
    }

    public int getHighScore() {
        return myGameData.getHighScore();
    }

    public boolean isSoundActive() {
        return myGame.isSound();
    }

    public boolean isGameActive() {
        return myGame.isPlaying();
    }

    @Override
    public void onSoundAction(String soundAction) {

        switch (soundAction) {
            case "Missile" :
                myGameSound.loopMissileSound();
            case "Explosion" :
                myGameSound.startExplosionSound(isSoundActive());
            case "Crash" :
                myGameSound.startCrashSound(isSoundActive());

            case "Saucer" :
                myGameSound.stopSound("Saucer");
            case "StopMissile" :
                myGameSound.stopSound("Missile");
        }


    }

    public int getTotalClips() {
        return myGameSound.getClipTotal();
    }

    public int getLoadedClips() {
        return myGameSound.getClipsLoaded();
    }
}
