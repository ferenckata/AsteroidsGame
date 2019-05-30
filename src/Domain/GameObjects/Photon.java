package src.Domain.GameObjects;

public class Photon extends Sprite {

    private double MAX_ROCK_SPEED;

    public Photon(int width, int height, double MAX_ROCK_SPEED){
        super(width,height);
        this.MAX_ROCK_SPEED = MAX_ROCK_SPEED;
    }

    @Override
    public void init() {
        //super.isActive = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }
    public void fire(double x, double y, double angle){
        setActive(true);
        setX(x);
        setY(y);
        deltaX = 2 * MAX_ROCK_SPEED * -Math.sin(angle);
        deltaY = 2 * MAX_ROCK_SPEED *  Math.cos(angle);

    }
}
