package src.Domain.GameObjects;

import src.Domain.Data.GameData;
import src.Domain.GameProperties;

public class Ship extends Sprite{

    private static int MAX_SHIP_SPEED = 0;
    private static int SHIP_SPEED_STEP = 0;
    private static int SHIP_ANGLE_STEP = 0;

    private Thruster fwdThruster;
    private Thruster revThruster;
    private Photon[] myPhotons;

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

        initPhotons();
    }

    private void initPhotons() {
        for (Photon photon: myPhotons) {
            photon.init();
            GameData.getMyInstance().setPhotonIndex(0); // this should be changed somehow
        }

    }

    // setters and getters

    public void setFwdThruster(Thruster fwdThruster) {
        this.fwdThruster = fwdThruster;
    }

    public void setRevThruster(Thruster revThruster) {
        this.revThruster = revThruster;
    }

    public static void setMaxShipSpeed(int MaxShipSpeed){
        MAX_SHIP_SPEED = MaxShipSpeed;
    }

    public static void setShipSpeedStep(int ShipSpeedStep){
        SHIP_SPEED_STEP = ShipSpeedStep;
    }

    public static void setShipAngleStep(int ShipAngleStep){
        SHIP_ANGLE_STEP = ShipAngleStep;
    }

    public void setMyPhotons(Photon[] myPhotons) {
        this.myPhotons = myPhotons;
    }

    @Override
    public void update() {


    }

    @Override
    public void stop() {
        isActive = false;
    }

    public void move(int direction){
        // if any key was pressed
        if(direction!=0){

            // if up or down
            if(direction==1 || direction==2){

                // Fire thrusters if up or down cursor key is down.

                double dx = SHIP_SPEED_STEP * -Math.sin(angle);
                double dy = SHIP_SPEED_STEP *  Math.cos(angle);


                // if up
                if(direction==1){
                    deltaX += dx;
                    deltaY += dy;

                // if down
                }else{
                    deltaX -= dx;
                    deltaY -= dy;

                }

                // Don't let ship go past the speed limit.

                double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (speed > MAX_SHIP_SPEED) {
                    dx = MAX_SHIP_SPEED * -Math.sin(angle);
                    dy = MAX_SHIP_SPEED *  Math.cos(angle);

                    // if up
                    if (direction==1){
                        deltaX = dx;
                        deltaY = dy;
                    }else{
                        deltaX = -dx;
                        deltaY = -dy;
                    }

                }

            // if right
            }else if(direction==3){
                angle -= SHIP_ANGLE_STEP;
                if (angle < 0){
                    angle += 2 * Math.PI;
                }

            // if left
            }else{
                angle += SHIP_ANGLE_STEP;
                if (angle > 2 * Math.PI){
                    angle -= 2 * Math.PI;
                }
            }
        }
    }


    public void updateThrusters() {
        fwdThruster.x = x;
        fwdThruster.y = y;
        fwdThruster.angle = angle;
        fwdThruster.render();
        revThruster.x = x;
        revThruster.y = y;
        revThruster.angle = angle;
        revThruster.render();
    }

    public void firePhotons(int photonIndex){
        myPhotons[photonIndex].fire(x,y,angle);

    }
}
