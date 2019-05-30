package src.Application;

import src.Domain.GameObjects.*;

public class ShapeFactory {

    private static ShapeFactory myInstance;

    private Background myBackground;

    private ShapeFactory(){
        this.myBackground = new Background();
    }

    public static ShapeFactory getInstance(){

        if(myInstance == null){
            myInstance = new ShapeFactory();
        }

        return myInstance;
    }


    public UFO createUFO(){
        UFO ufo = new UFO(myBackground.getWidth(), myBackground.getHeight());
        ufo.getShape().addPoint(-15, 0);
        ufo.getShape().addPoint(-10, -5);
        ufo.getShape().addPoint(-5, -5);
        ufo.getShape().addPoint(-5, -8);
        ufo.getShape().addPoint(5, -8);
        ufo.getShape().addPoint(5, -5);
        ufo.getShape().addPoint(10, -5);
        ufo.getShape().addPoint(15, 0);
        ufo.getShape().addPoint(10, 5);
        ufo.getShape().addPoint(-10, 5);
        return ufo;
    }

    public Ship createShip(int MAX_SHOTS,double MAX_ROCK_SPEED) {

        Ship ship = new Ship(myBackground.getWidth(), myBackground.getHeight());
        ship.getShape().addPoint(0, -10);
        ship.getShape().addPoint(7, 10);
        ship.getShape().addPoint(-7, 10);

        Thruster fwdThruster = new Thruster(myBackground.getWidth(), myBackground.getHeight());
        fwdThruster.getShape().addPoint(0, 12);
        fwdThruster.getShape().addPoint(-3, 16);
        fwdThruster.getShape().addPoint(0, 26);
        fwdThruster.getShape().addPoint(3, 16);
        Thruster revThruster = new Thruster(myBackground.getWidth(), myBackground.getHeight());
        revThruster.getShape().addPoint(-2, 12);
        revThruster.getShape().addPoint(-4, 14);
        revThruster.getShape().addPoint(-2, 20);
        revThruster.getShape().addPoint(0, 14);
        revThruster.getShape().addPoint(2, 12);
        revThruster.getShape().addPoint(4, 14);
        revThruster.getShape().addPoint(2, 20);
        revThruster.getShape().addPoint(0, 14);

        Photon[] photons = createPhotons(MAX_SHOTS,MAX_ROCK_SPEED);

        ship.setMyPhotons(photons);
        ship.setFwdThruster(fwdThruster);
        ship.setRevThruster(revThruster);

        return ship;

    }

    public Missile createMissile() {

        Missile missile = new Missile(myBackground.getWidth(), myBackground.getHeight());
        missile.getShape().addPoint(0, -4);
        missile.getShape().addPoint(1, -3);
        missile.getShape().addPoint(1, 3);
        missile.getShape().addPoint(2, 4);
        missile.getShape().addPoint(-2, 4);
        missile.getShape().addPoint(-1, 3);
        missile.getShape().addPoint(-1, -3);

        return missile;
    }

    public Photon[] createPhotons(int MAX_SHOTS, double MAX_ROCK_SPEED) {
        Photon[] photons = new Photon[MAX_SHOTS];

        for (int i = 0; i < MAX_SHOTS; i++) {
            photons[i] = new Photon(myBackground.getWidth(), myBackground.getHeight(),MAX_ROCK_SPEED);
            photons[i].getShape().addPoint(1, 1);
            photons[i].getShape().addPoint(1, -1);
            photons[i].getShape().addPoint(-1, 1);
            photons[i].getShape().addPoint(-1, -1);
        }

        return photons;
    }

    public Asteroid[] createAsteroids(int MAX_ROCKS){
        Asteroid[] asteroids = new Asteroid[MAX_ROCKS];

        for (int i = 0; i < MAX_ROCKS; i++)
            asteroids[i] = new Asteroid(myBackground.getWidth(), myBackground.getHeight());

        return asteroids;
    }


    public Explosion[] createExplosions(int MAX_SCRAP){

        Explosion[] explosions = new Explosion[MAX_SCRAP];

        for (int i = 0; i < MAX_SCRAP; i++)
            explosions[i] = new Explosion(myBackground.getWidth(), myBackground.getHeight());

        return explosions;
    }

}
