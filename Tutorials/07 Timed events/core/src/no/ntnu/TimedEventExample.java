package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * This applications shows how to implement timed events: something happening at specific time intervals
 */
public class TimedEventExample extends ApplicationAdapter {
    private EventTimer timer1;
    private EventTimer timer2;
    private EventTimer timer3;

    @Override
    public void create() {
        timer1 = new EventTimer();
        timer2 = new EventTimer();
        timer3 = new EventTimer();
        timer1.start(1000, true); // This one will fire every second (1000 milliseconds)
        timer2.start(2700, true); // this one will fire every 2.7 seconds (2700 milliseconds)
        timer3.start(10000, false); // This one will fire only once: after 10 sec, and will stop the timer 1
    }

    @Override
    public void render() {
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
    }

    @Override
    public void dispose() {
    }
}
