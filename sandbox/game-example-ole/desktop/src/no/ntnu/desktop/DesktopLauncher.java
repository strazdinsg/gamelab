package no.ntnu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.ntnu.ShooterGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config
                = new LwjglApplicationConfiguration();
        ShooterGame be = ShooterGame.getInstance();
        LwjglApplication app = new LwjglApplication(be, config);
    }
}
