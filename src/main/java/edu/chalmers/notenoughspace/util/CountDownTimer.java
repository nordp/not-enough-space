package edu.chalmers.notenoughspace.util;

public abstract class CountDownTimer {

    private float timeLeft;

    public CountDownTimer(float totalTime) {
        timeLeft = totalTime;
    }

    public void tick(float seconds) {
        timeLeft -= seconds;
        if (timeLeft <= 0) {
            onTimeOut();
        }
    }

    public abstract void onTimeOut();

    public float getTimeLeft() {
        return timeLeft;
    }
}
