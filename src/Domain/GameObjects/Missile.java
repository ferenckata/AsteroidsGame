package src.Domain.GameObjects;

public class Missile extends Sprite {


    public Missile(int width, int height){
        super(width,height);
    }

    @Override
    public void init() {
        isActive = true;
        angle = 0.0;
        deltaAngle = 0.0;
        deltaX = 0.0;
        deltaY = 0.0;
        render();
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {
        isActive = false;
    }
}
