package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

/**
 * This applications shows how to implement timed events: something happening at specific time intervals
 */
public class TimedEventExample extends ApplicationAdapter {
    private EventTimer timer1;
    private EventTimer timer2;
    private EventTimer timer3;

    float accumulatedTime; // We will use this to manually count time since last event

    @Override
    public void create() {
        timer1 = new EventTimer();
        timer2 = new EventTimer();
        timer3 = new EventTimer();
        timer1.start(1000, true); // This one will fire every second (1000 milliseconds)
        timer2.start(2700, true); // this one will fire every 2.7 seconds (2700 milliseconds)
        timer3.start(10000, false); // This one will fire only once: after 10 sec, and will stop the timer 1

        accumulatedTime = 0; // We start counting now
    }

    @Override
    public void render() {
        // Alternative #1: Use EventTimer class
        if (timer1.mustFire()) {
            System.out.println("Timer 1 fired");
        }
        if (timer2.mustFire()) {
            System.out.println("Timer 2 fired");
        }
        if (timer3.mustFire()) {
            System.out.println("Timer 3 fired");
            timer1.stop();
        }

        // Alternative #2: Count time since last event manually
        float secondsSinceLastFrame = Gdx.graphics.getDeltaTime();
        accumulatedTime += secondsSinceLastFrame;
        if (accumulatedTime >= 5.0) {
            System.out.println("Five seconds have passed since last checkpoint");
            accumulatedTime = 0;
        }
    }

    @Override
    public void dispose() {
    }
}
