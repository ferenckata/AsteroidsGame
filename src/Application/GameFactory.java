package src.Application;

import src.Domain.Game;

public class GameFactory {

    private static GameFactory myInstance;

    private GameFactory(){

    }

    public static GameFactory getInstance(){

        if(myInstance == null){
            myInstance = new GameFactory();
        }

        return myInstance;
    }

    public Game createGame(int highScore, boolean sound, boolean detail) {
        Game myGame = new Game(highScore,sound,detail);
        return myGame;
    }


    public Sound createGameSound() {
        Sound gameSound = Sound.getInstance();
        return gameSound;
    }
}
