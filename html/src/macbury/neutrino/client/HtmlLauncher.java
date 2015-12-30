package macbury.neutrino.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import macbury.neutrino.GameContext;

public class HtmlLauncher extends GwtApplication {

  @Override
  public GwtApplicationConfiguration getConfig () {
    GwtApplicationConfiguration config = new GwtApplicationConfiguration(480, 320);

    return config;
  }

  @Override
  public ApplicationListener getApplicationListener () {
      return new GameContext();
  }
}