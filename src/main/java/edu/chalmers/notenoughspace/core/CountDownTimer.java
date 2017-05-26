package edu.chalmers.notenoughspace.core;

/**
 * Classic count down timer for counting down time and reacting when time is out.
 */
public abstract class CountDownTimer {

    protected float timeLeft;
    protected boolean running;

    public CountDownTimer(float initialTimeLeft) {
        timeLeft = initialTimeLeft;
        running = true;
    }


    public void tick(float seconds) {
        if(!running){
            return;
        }

        timeLeft -= seconds;

        if (timeLeft <= 0) {
            running = false;
            onTimeOut();
        }
    }

    public abstract void onTimeOut();

    public float getTimeLeft() {
        return timeLeft;
    }
}
