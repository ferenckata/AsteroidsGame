package src.Domain.GameObjects;

public class UFO extends Sprite {

    private Missile myMissile;

    public UFO(int width, int height){
        super(width,height);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    public Missile getMissile(){
        return myMissile;
    }

}
