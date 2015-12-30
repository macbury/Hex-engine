package macbury.neutrino.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import macbury.neutrino.GameContext;

public class DesktopLauncher {
  public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 1360;
    config.height = 768;
    config.resizable = false;
    config.useGL30    = true;
    config.title    = "Neutrino v"+ GameContext.VERSION;
		new LwjglApplication(new GameContext(), config);
	}
}
