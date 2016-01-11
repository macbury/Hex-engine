package macbury.neutrino.core.map;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Creates hexagon grid filled with empty {@link Field}
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 * http://www.redblobgames.com/grids/hexagons/#map-storage
 */
public class HexGrid implements Disposable {
  private final int radius;
  private Field[][] fields;

  /**
   * Creates empty grid with passed radius
   * @param radius
   */
  public HexGrid(int radius) {
    this.radius = radius;

    fields      = new Field[radius*2+1][radius*2+1];
    fillMapWithHexagonShape();
  }

  private void fillMapWithHexagonShape() {
    for (int q = -radius; q <= radius; q++) {
      int r1 = Math.max(-radius, -q - radius);
      int r2 = Math.min(radius, -q + radius);
      for (int r = r1; r <= r2; r++) {
        put(q,r, new Field());
      }
    }

  }

  private void fillMapWithEmptyFields() {
    for (int q = -radius; q < radius; q++) {
      for (int r = -radius; r < radius; r++) {
        put(q,r, new Field());
      }
    }
  }

  public void put(int q, int r, Field field) {
    fields[r + radius][q + radius] = field;
  }

  @Override
  public void dispose() {
    fields = null;
  }

  public int getRadius() {
    return radius;
  }

  public Field get(int q, int r) {
    return fields[q + radius][r + radius];
  }

}
