package macbury.neutrino.core.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Disposable;

/** Models a common way of rendering {@link HexGrid} objects */
public abstract class HexGridRenderer implements Disposable {
  protected Layout layout;
  protected HexGrid grid;

  public HexGridRenderer(HexGrid grid, Layout layout) {
    this.layout = layout;
    this.grid   = grid;
  }

  /** Sets the projection matrix and viewbounds from the given camera. If the camera changes, you have to call this method again.
   * The viewbounds are taken from the camera's position and viewport size as well as the scale. This method will only work if
   * the camera's direction vector is (0,0,-1) and its up vector is (0, 1, 0), which are the defaults.
   * @param camera the {@link Camera} */
  public abstract void setView (Camera camera);

  /** Renders map. */
  public abstract void render ();

  @Override
  public void dispose() {
    grid = null;
    layout = null;
  }
}
