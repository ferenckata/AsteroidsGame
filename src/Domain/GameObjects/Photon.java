package src.Domain.GameObjects;

public class Photon extends Sprite {

    public Photon(int width, int height){
        super(width,height);
    }

    @Override
    public void init() {
        super.isActive = false;
    }
}
