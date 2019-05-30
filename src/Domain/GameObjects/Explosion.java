package src.Domain.GameObjects;

public class Explosion extends Sprite {

    public Explosion(int width, int height){
        super(width,height);
    }

    @Override
    public void init() {
        this.isActive = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }
}
