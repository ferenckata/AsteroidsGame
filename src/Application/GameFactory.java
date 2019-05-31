package src.Application;

import src.Domain.Game;
import src.Domain.GameObjects.Asteroid;
import src.Domain.GameObjects.Explosion;
import src.Domain.GameObjects.UFO;

public class GameFactory {

    private static GameFactory myInstance = null;

    private GameFactory(){
    }

    public static GameFactory getInstance(){

        if(myInstance == null){
            myInstance = new GameFactory();
        }

        return myInstance;
    }

    public Game createGame(int highScore, boolean sound, boolean detail, OnGameListener myOnGameListener, Asteroid[] asteroids, Explosion[] explosions, UFO ufo) {
        Game myGame = new Game(highScore,sound,detail,myOnGameListener);
        myGame.setMyAsteroids(asteroids);
        myGame.setMyExplosions(explosions);
        myGame.setMyUfo(ufo);
        return myGame;
    }


    public Sound createGameSound() {
        Sound gameSound = Sound.getInstance();
        return gameSound;
    }
}
