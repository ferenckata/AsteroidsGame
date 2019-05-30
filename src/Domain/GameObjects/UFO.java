package src.Domain.GameObjects;

public class UFO extends Sprite {

    double MAX_ROCK_SPEED;

    public UFO(int width, int height, double MAX_ROCK_SPEED){
        super(width,height);
        this.MAX_ROCK_SPEED = MAX_ROCK_SPEED;
    }

    @Override
    public void init() {

        isActive = true;
        x = -width / 2;
        y = Math.random() * 2 * height - height;
        angle = Math.random() * Math.PI / 4 - Math.PI / 2;
        double speed = MAX_ROCK_SPEED / 2 + Math.random() * (MAX_ROCK_SPEED / 2);
        deltaX = speed * -Math.sin(angle);
        deltaY = speed *  Math.cos(angle);
        if (Math.random() < 0.5) {
            x = width / 2;
            deltaX = -deltaX;
        }
        if (y > 0)
            deltaY = deltaY;
        render();
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

}
