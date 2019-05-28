package src.Domain.GameObjects;

public class Ship extends Sprite{

    Thruster fwdThruster;
    Thruster revThruster;

    public Ship(int width, int height){
        super(width,height);
    }

    @Override
    public void init() {
        // Reset the ship sprite at the center of the screen.

        this.isActive = true;
        this.angle = 0.0;
        this.deltaAngle = 0.0;
        this.x = 0.0;
        this.y = 0.0;
        this.deltaX = 0.0;
        this.deltaY = 0.0;
        this.render();

        // Initialize thruster sprites.

        fwdThruster.x = this.x;
        fwdThruster.y = this.y;
        fwdThruster.angle = this.angle;
        fwdThruster.render();
        revThruster.x = this.x;
        revThruster.y = this.y;
        revThruster.angle = this.angle;
        revThruster.render();
    }

    @Override
    public void update() {

        double dx, dy, speed;


    }

    @Override
    public void stop() {

    }

    public void setFwdThruster(Thruster fwdThruster) {
        this.fwdThruster = fwdThruster;
    }

    public void setRevThruster(Thruster revThruster) {
        this.revThruster = revThruster;
    }
}
