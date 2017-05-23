package edu.chalmers.notenoughspace.core;

public abstract class CountDownTimer {

    protected float timeLeft;
    protected boolean running;

    public CountDownTimer(float totalTime) {
        timeLeft = totalTime;
        running = true;
    }

    public void tick(float seconds) {
        if(!running)
            return;
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
