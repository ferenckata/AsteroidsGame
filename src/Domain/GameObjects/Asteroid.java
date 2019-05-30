package src.Domain.GameObjects;

public class Asteroid extends Sprite {

    private double MIN_ROCK_SIDES;
    private double MAX_ROCK_SIDES;
    private int MIN_ROCK_SIZE;
    private int MAX_ROCK_SIZE;
    private double MAX_ROCK_SPIN;
    private double asteroidsSpeed;
    private boolean isSmall;

    public Asteroid(int width, int height, double MIN_ROCK_SIDES, double MAX_ROCK_SIDES, int MIN_ROCK_SIZE, int MAX_ROCK_SIZE, double MAX_ROCK_SPIN, double asteroidsSpeed){
        super(width,height);
        this.MIN_ROCK_SIDES = MIN_ROCK_SIDES;
        this.MAX_ROCK_SIDES = MAX_ROCK_SIDES;
        this.MIN_ROCK_SIZE = MIN_ROCK_SIZE;
        this.MAX_ROCK_SIZE = MAX_ROCK_SIZE;
        this.MAX_ROCK_SPIN = MAX_ROCK_SPIN;
        this.asteroidsSpeed = asteroidsSpeed;
        this.isSmall = false;
    }

    @Override
    public void init() {
        // asteroids[i].shape = new Polygon();

    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    public void setUpAsteroid(boolean isSmall, double tempX, double tempY) {

        if(isSmall){
            this.isSmall = true;
        }

        double s = MIN_ROCK_SIDES + (int) (Math.random() * (MAX_ROCK_SIDES - MIN_ROCK_SIDES));
        for (int j = 0; j < s; j ++) {
            double theta = 2 * Math.PI / s * j;
            int r;
            if(!isSmall){
                r = MIN_ROCK_SIZE + (int) (Math.random() * (MAX_ROCK_SIZE - MIN_ROCK_SIZE));
            }else{
                r = MIN_ROCK_SIZE + (int) (Math.random() * (MAX_ROCK_SIZE - MIN_ROCK_SIZE)) / 2;
            }

            int x = (int) -Math.round(r * Math.sin(theta));
            int y = (int)  Math.round(r * Math.cos(theta));
            shape.addPoint(x, y);
        }
        isActive = true;
        angle = 0.0;
        deltaAngle = Math.random() * 2 * MAX_ROCK_SPIN - MAX_ROCK_SPIN;


        // if big asteroid
        if(!isSmall){
            // Place the asteroid at one edge of the screen.

            if (Math.random() < 0.5) {
                x = -width / 2;
                if (Math.random() < 0.5)
                    x = width / 2;
                y = Math.random() * height;
            }
            else {
                x = Math.random() * width;
                y = -height / 2;
                if (Math.random() < 0.5)
                    y = height / 2;
            }

            // Set a random motion for the asteroid.
            deltaX = Math.random() * asteroidsSpeed;
            deltaY = Math.random() * asteroidsSpeed;
            if (Math.random() < 0.5){
                deltaX = -deltaX;
                deltaY = -deltaY;
            }


        }else{
            // if small place it where the big has been
            x = tempX;
            y = tempY;
            // Set a random motion for the asteroid.
            deltaX = Math.random() * 2 * asteroidsSpeed - asteroidsSpeed;
            deltaY = Math.random() * 2 * asteroidsSpeed - asteroidsSpeed;
        }

        render();

    }

    public boolean getIsSmall(){
        return isSmall;
    }
}
