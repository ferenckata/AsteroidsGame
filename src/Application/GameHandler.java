package src.Application;

import src.Asteroids;
import src.Domain.*;

public class GameHandler {

    private boolean loaded = false;

    private Factory myFactory;
    private GameProperties myGameProperties;
    private Game myGame;

    private UFO myUFO;
    private Ship myShip;
    private Missile myMissile;
    private Photon[] photons;
    private Asteroid[] asteroids;
    private Explosion[] explosions;

    public GameHandler(){
        this.myGameProperties = GameProperties.getInstance();
        this.myFactory = Factory.getInstance();
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean createGameObjects(){

        myUFO = myFactory.createUFO();
        myShip = myFactory.createShip();
        myMissile = myFactory.createMissile();
        photons = myFactory.createPhotons(myGameProperties.getMaxShots());
        asteroids = myFactory.createAsteroids(myGameProperties.getMaxRocks());
        explosions = myFactory.createExplosions(myGameProperties.getMaxScrap());

        return true;
    }

    public void createNewGame() {

        int highScore = 0;
        boolean sound = true;
        boolean detail = true;

        myGame = myFactory.createGame(highScore,sound,detail);
        Sound gameSound = myFactory.createGameSound();

        myGame.initGame(myShip);

        if (loaded)
            gameSound.stopThrustersSound();
        gameSound.setThrustersPlaying(false);

        endGame();

    }


    public void loadSounds() {

    }

    public void updateGame() {
        if (!myGame.isPaused()) {

            // Move and process all sprites.

            updateShip();
            updatePhotons();
            updateUfo();
            updateMissle();
            updateAsteroids();
            updateExplosions();

            // Check the score and advance high score, add a new ship or start the
            // flying saucer as necessary.

            myGame.evaluateHighScore();

            myGame.rewardShip();

            myGame.getUFO();



            // If all asteroids have been destroyed create a new batch.

            if (asteroidsLeft <= 0)
                if (--asteroidsCounter <= 0)
                    initAsteroids();
        }
    }
}
