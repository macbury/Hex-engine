package macbury.neutrino.core.map;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created on 30.12.15.
 */
public class HexGrid implements Disposable {
  private final int width;
  private final int height;
  private Hex[][] grid;

  public HexGrid(int width, int height) {
    this.width  = width;
    this.height = height;

    this.grid   = new Hex[width][height];
  }

  @Override
  public void dispose() {
    grid = null;
  }
}
