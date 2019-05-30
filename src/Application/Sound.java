package src.Application;

import src.Domain.Data.SoundData;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {

    private static Sound myInstance;
    private SoundData mySoundData;
    private int clipTotal;
    private int clipsLoaded;
    private Clip crashSound;
    private Clip explosionSound;
    private Clip fireSound;
    private Clip missileSound;
    private Clip saucerSound;
    private Clip thrustersSound;
    private Clip warpSound;

    private Sound(){
        mySoundData = SoundData.getInstance();
        clipTotal = 0;
        clipsLoaded = 0;
    }

    public static Sound getInstance(){
        if (myInstance == null){
            myInstance = new Sound();
        }
        return myInstance;
    }

    public SoundData getMySoundData() {
        return mySoundData;
    }

    public Clip getSaucerSound() {
        return saucerSound;
    }

    public void stopThrustersSound() {
    }


    public void setThrustersPlaying(boolean b) {
    }


    public void loadSound(String clip) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
        Clip audioClip = AudioSystem.getClip();
        switch (clip) {
            case "Crash" :
                crashSound = audioClip;
                crashSound.open(AudioSystem.getAudioInputStream(new File("sounds/crash.wav")));
            case "Explosion" :
                explosionSound = audioClip;
                explosionSound.open(AudioSystem.getAudioInputStream(new File("sounds/explosion.wav")));
            case "Fire" :
                fireSound = audioClip;
                fireSound.open(AudioSystem.getAudioInputStream(new File("sounds/fire.wav")));
            case "Missile" :
                missileSound = audioClip;
                missileSound.open(AudioSystem.getAudioInputStream(new File("sounds/missile.wav")));
            case "Saucer" :
                saucerSound = audioClip;
                saucerSound.open(AudioSystem.getAudioInputStream(new File("sounds/saucer.wav")));
            case "Thrusters" :
                thrustersSound = audioClip;
                thrustersSound.open(AudioSystem.getAudioInputStream(new File("sounds/thrusters.wav")));
            case "Warp" :
                warpSound = audioClip;
                warpSound.open(AudioSystem.getAudioInputStream(new File("sounds/warp.wav")));
        }
        clipTotal++;
    }

    public void runSound(String clip) {
        switch (clip) {
            case "Crash" :
                crashSound.start();
                crashSound.stop();
            case "Explosion" :
                explosionSound.start();
                explosionSound.stop();
            case "Fire" :
                fireSound.start();
                fireSound.stop();
            case "Missile" :
                missileSound.start();
                missileSound.stop();
            case "Saucer" :
                saucerSound.start();
                saucerSound.stop();
            case "Thrusters" :
                thrustersSound.start();
                thrustersSound.stop();
            case "Warp" :
                warpSound.start();
                warpSound.stop();
        }
        clipsLoaded++;

    }

    public void initUfoSound(boolean sound) {

        mySoundData.setSaucerPlaying(true);

        if (sound) {
            saucerSound.setFramePosition(0);
            saucerSound.start();
            saucerSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
