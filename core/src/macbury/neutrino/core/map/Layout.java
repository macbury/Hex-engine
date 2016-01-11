package macbury.neutrino.core.map;

import com.badlogic.gdx.math.Vector2;

public class Layout {
  public final Orientation orientation;
  public final Vector2 size;
  public final Vector2 origin;

  public Layout(Orientation orientation, Vector2 size, Vector2 origin) {
    this.orientation = orientation;
    this.size        = size;
    this.origin      = origin;
  }
}
