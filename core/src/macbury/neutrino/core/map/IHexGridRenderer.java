package macbury.neutrino.core.map;

import com.badlogic.gdx.graphics.Camera;

/** Models a common way of rendering {@link HexGrid} objects */
public interface IHexGridRenderer {

  /** Sets the projection matrix and viewbounds from the given camera. If the camera changes, you have to call this method again.
   * The viewbounds are taken from the camera's position and viewport size as well as the scale. This method will only work if
   * the camera's direction vector is (0,0,-1) and its up vector is (0, 1, 0), which are the defaults.
   * @param camera the {@link Camera} */
  public void setView (Camera camera);

  /** Renders map. */
  public void render ();

  public void dispose();
}
