package src.Domain.GameObjects;

public class UFO extends Sprite {

    private static double MAX_ROCK_SPEED;
    private static double UFO_POINTS;
    private static double MAX_SHOTS;
    private static double MISSLE_PROBABILITY;

     private Missile myMissile;

    public UFO(int width, int height, double MAX_ROCK_SPEED, double UFO_POINTS, double MAX_SHOTS, double MISSLE_PROBABILITY, Missile missile){
        super(width,height);
        this.MAX_ROCK_SPEED = MAX_ROCK_SPEED;
        this.UFO_POINTS = UFO_POINTS;
        this.MAX_SHOTS = MAX_SHOTS;
        this.MISSLE_PROBABILITY = MISSLE_PROBABILITY;
        this.myMissile = missile;
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
        myMissile.update();
    }

    @Override
    public void stop() {
        isActive = false;
    }

    public int getCounter() {
        return (int) Math.abs(width / deltaX);
    }

    public Missile getMyMissile() {
        return myMissile;
    }

    public void initMissile(){
        myMissile.setX(x);
        myMissile.setY(y);
        myMissile.init();
    }

    public void stopMissle() {
        myMissile.stop();
    }
}
