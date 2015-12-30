package macbury.neutrino;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import macbury.neutrino.core.assets.Assets;
import macbury.neutrino.core.screens.ScreenManager;
import macbury.neutrino.game.HexScreen;

public class GameContext extends ApplicationAdapter {
  public final static String VERSION = "0.0.2";
  private static final String TAG = "macbury.neutrino.GameContext";
  /**
   * Message comunication class used by {@link macbury.indie.core.entities.EntityManager}
   */
  public MessageDispatcher messages;
  /**
   *  Loads and stores assets like textures, bitmapfonts, tile maps, sounds, music and so on.
   */
  public Assets assets;
  /**
   * Manage in game screens
   */
  public ScreenManager screens;

  @Override
  public void create() {
    Gdx.app.setLogLevel(Application.LOG_DEBUG);
    Gdx.app.log(TAG, "Init...");

    this.assets     = new Assets();
    this.messages   = new MessageDispatcher();
    this.screens    = new ScreenManager(this);

    screens.set(new HexScreen());
  }

  @Override
  public void render() {
    messages.update();
    screens.update();
  }

  @Override
  public void dispose() {
    assets.dispose();
    messages.clear();
    messages.clearListeners();
  }
}
