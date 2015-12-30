package macbury.neutrino.core.screens;

import com.badlogic.gdx.utils.Disposable;
import macbury.neutrino.GameContext;
import macbury.neutrino.core.assets.Assets;

/** <p>
 * Represents one of many application screens, such as a main menu, a settings menu, the game screen and so on.
 * </p>
 * <p>
 * Note that {@link #dispose()} is not called automatically.
 * </p>*/
public abstract class ScreenBase implements Disposable {
  protected GameContext game;
  protected Assets assets;
  protected ScreenManager screens;

  /**
   * Links references to current {@link GameContext}
   * @param game
   */
  public void link(GameContext game) {
    this.unlink();
    this.game     = game;
    this.assets   = game.assets;
    this.screens  = game.screens;
  }

  /**
   * Unlink references to current {@link GameContext}
   */
  public void unlink() {
    this.game     = null;
    this.assets   = null;
    this.screens  = null;
  }

  /**
   * Called before {@link ScreenBase#create()}. You can add assets to load here. If there are assets to load it shows loading screen
   */
  public abstract void preload();
  /**
   * Called when screen is showed
   */
  public abstract void create();

  /** Called when the screen should render itself.
   * @param delta The time in seconds since the last render. */
  public abstract void render (float delta);

  /** Called after screen show or game window resize */
  public abstract void resize (int width, int height);

  /**
   * Called when screen after screen is hiden
   */
  public abstract void unload();
}