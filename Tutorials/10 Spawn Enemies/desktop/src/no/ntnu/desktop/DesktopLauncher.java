package no.ntnu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.ntnu.EnemyExample;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = EnemyExample.SCREEN_WIDTH;
        config.height = EnemyExample.SCREEN_HEIGHT;
		new LwjglApplication(new EnemyExample(), config);
	}
}
