package com.mygdx.game;

/**
 * Our own class for counting time. The class does not do anything (no background threads, etc).
 * It just calculates when the next event must be fired and responds with true/false when someone calls mustFire()
 */
public class EventTimer {
    private boolean periodic;
    private long period = -1;
    private long shotTime = -1; // When the timer should fire

    /**
     * Start a timer that will fire after m milliseconds
     *
     * @param m        timeout for the timer, in milliseconds
     * @param periodic when true, timer will be periodic, when false - it will fire only once
     */
    public void start(long m, boolean periodic) {
        shotTime = System.currentTimeMillis() + m;
        this.periodic = periodic;
        if (periodic) {
            this.period = m;
        }
    }

    /**
     * Checks if the timer must be fired now and also sets the next timeout if the timer is periodic.
     *
     * @return true when timeout is reached (and an event must be fired), false otherwise
     */
    public boolean mustFire() {
        boolean fire = (shotTime > 0) && (System.currentTimeMillis() >= shotTime);
        if (fire) {
            if (periodic) {
                shotTime += period;
            } else {
                shotTime = -1;
            }
        }
        return fire;
    }

    /**
     * Stop the timer
     */
    public void stop() {
        shotTime = period = -1;
        periodic = false;
    }
}
