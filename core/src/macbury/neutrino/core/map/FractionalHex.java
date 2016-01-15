package macbury.neutrino.core.map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * For pixel-to-hex I need fractional hex coordinates.
 */
public class FractionalHex {
  private final static Vector2 pt  = new Vector2();
  public float q;
  public float r;
  public float s;

  /**
   * Creates new FractionalHex
   * @param q column index
   * @param r row index
   * @param s
   */
  public FractionalHex(float q, float r, float s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Creates copy of passed FractionalHex tile
   * @param hexToCopy
   */
  public FractionalHex(FractionalHex hexToCopy) {
    this.set(hexToCopy);
  }

  public FractionalHex() {

  }

  /**
   * Copy values from passed tile
   * @param hexToCopy
   * @return current hex tile
   */
  private FractionalHex set(FractionalHex hexToCopy) {
    this.q = hexToCopy.q;
    this.r = hexToCopy.r;
    this.s = hexToCopy.s;
    return this;
  }

  /**
   * Transforms world position to {@link FractionalHex}
   * @param in
   * @return
   */
  public FractionalHex setWorld(Vector3 in, Layout layout) {
    pt.set(
      (in.x - layout.origin.x) / layout.size.x,
      (in.z - layout.origin.y) / layout.size.y
    );

    this.q = layout.orientation.b0 * pt.x + layout.orientation.b1 * pt.y;
    this.r = layout.orientation.b2 * pt.x + layout.orientation.b3 * pt.y;
    this.s = -q-r;

    return this;
  }

  /**
   * Rounding turns a fractional hex coordinate into the nearest integer hex coordinate
   * @param outHex
   */
  public Hex roundTo(Hex outHex) {
    outHex.q = MathUtils.round(q);
    outHex.r = MathUtils.round(r);
    outHex.s = MathUtils.round(s);

    float qDiff = Math.abs(outHex.q - q);
    float rDiff = Math.abs(outHex.r - r);
    float sDiff = Math.abs(outHex.s - s);

    if (qDiff > rDiff && qDiff > sDiff) {
      outHex.q = -outHex.r - outHex.s;
    } else if (rDiff > sDiff) {
      outHex.r = -outHex.q - outHex.s;
    } else {
      outHex.s = -outHex.q - outHex.r;
    }

    return outHex;
  }
}
