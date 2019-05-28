package src.Domain.Data;

import javax.sound.sampled.Clip;

public class SoundData {

    // Sound clips.

    private Clip crashSound;
    private Clip explosionSound;
    private Clip fireSound;
    private Clip missleSound;
    private Clip saucerSound;
    private Clip thrustersSound;
    private Clip warpSound;

    // Flags for looping sound clips.

    private boolean thrustersPlaying;
    private boolean saucerPlaying;
    boolean misslePlaying;

    // Counter and total used to track the loading of the sound clips.

    private int clipTotal   = 0;
    private int clipsLoaded = 0;

    private static SoundData myInstance;

    private SoundData(){

    }

    public static SoundData getInstance(){
        if (myInstance == null){
            myInstance = new SoundData();
        }
        return myInstance;
    }

    public Clip getCrashSound() {
        return crashSound;
    }

    public void setCrashSound(Clip crashSound) {
        this.crashSound = crashSound;
    }

    public Clip getExplosionSound() {
        return explosionSound;
    }

    public void setExplosionSound(Clip explosionSound) {
        this.explosionSound = explosionSound;
    }

    public Clip getFireSound() {
        return fireSound;
    }

    public void setFireSound(Clip fireSound) {
        this.fireSound = fireSound;
    }

    public Clip getMissleSound() {
        return missleSound;
    }

    public void setMissleSound(Clip missleSound) {
        this.missleSound = missleSound;
    }

    public Clip getSaucerSound() {
        return saucerSound;
    }

    public void setSaucerSound(Clip saucerSound) {
        this.saucerSound = saucerSound;
    }

    public Clip getThrustersSound() {
        return thrustersSound;
    }

    public void setThrustersSound(Clip thrustersSound) {
        this.thrustersSound = thrustersSound;
    }

    public Clip getWarpSound() {
        return warpSound;
    }

    public void setWarpSound(Clip warpSound) {
        this.warpSound = warpSound;
    }

    public boolean isThrustersPlaying() {
        return thrustersPlaying;
    }

    public void setThrustersPlaying(boolean thrustersPlaying) {
        this.thrustersPlaying = thrustersPlaying;
    }

    public boolean isSaucerPlaying() {
        return saucerPlaying;
    }

    public void setSaucerPlaying(boolean saucerPlaying) {
        this.saucerPlaying = saucerPlaying;
    }

    public int getClipTotal() {
        return clipTotal;
    }

    public void setClipTotal(int clipTotal) {
        this.clipTotal = clipTotal;
    }

    public int getClipsLoaded() {
        return clipsLoaded;
    }

    public void setClipsLoaded(int clipsLoaded) {
        this.clipsLoaded = clipsLoaded;
    }
}
