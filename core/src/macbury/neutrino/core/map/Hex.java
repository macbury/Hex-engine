package macbury.neutrino.core.map;

import com.badlogic.gdx.math.MathUtils;

/**
 * This class defines each hexagonal tile on the map using cube cordinate system
 */
public class Hex {
  public final static int SIZE         = 26;
  public final static float WIDTH      = SIZE * 2;
  public final static float HEIGHT     = MathUtils.floorPositive((float)(Math.sqrt(3.0)/2.0) * WIDTH);
  private final static Hex tempHex     = new Hex();

  public final static Hex DIRECTIONS[] = new Hex[] {
    new Hex(1, 0, -1), new Hex(1, -1, 0), new Hex(0, -1, 1),
    new Hex(-1, 0, 1), new Hex(-1, 1, 0), new Hex(0, 1, -1)
  };

  /**
   * Column index
   */
  public int q;
  /**
   * Row index
   */
  public int r;
  public int s;

  /**
   * Creates new hex
   * @param q column index
   * @param r row index
   * @param s
   */
  public Hex(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Creates copy of passed hex tile
   * @param hexToCopy
   */
  public Hex(Hex hexToCopy) {
    this.set(hexToCopy);
  }

  public Hex() {

  }

  /**
   * Copy values from passed tile
   * @param hexToCopy
   * @return current hex tile
   */
  private Hex set(Hex hexToCopy) {
    this.q = hexToCopy.q;
    this.r = hexToCopy.r;
    this.s = hexToCopy.s;
    return this;
  }

  /**
   * Adds other hex tile coordinates to this tile coordinate
   * @param otherHex
   * @return current hex tile
   */
  public Hex add(Hex otherHex) {
    this.q += otherHex.q;
    this.r += otherHex.r;
    this.s += otherHex.s;
    return this;
  }

  /**
   * Subs other hex tile coordinates to this tile coordinate
   * @param otherHex
   * @return current hex tile
   */
  public Hex sub(Hex otherHex) {
    this.q -= otherHex.q;
    this.r -= otherHex.r;
    this.s -= otherHex.s;
    return this;
  }

  /**
   * Multiply other hex tile coordinates to this tile coordinate
   * @param otherHex
   * @return current hex tile
   */
  public Hex mul(Hex otherHex) {
    this.q *= otherHex.q;
    this.r *= otherHex.r;
    this.s *= otherHex.s;
    return this;
  }

  /**
   * Returns length of this hex tile
   * @return
   */
  public int len() {
    return (Math.abs(q) + Math.abs(r) + Math.abs(s))/2;
  }

  /**
   * Returns distance to passed hex from current hex
   * @return
   */
  public int dst(Hex toHex) {
    return tempHex.set(this).sub(toHex).len();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Hex)) return false;

    Hex hex = (Hex) o;

    if (q != hex.q) return false;
    if (r != hex.r) return false;
    return s == hex.s;
  }

  @Override
  public int hashCode() {
    int result = q;
    result = 31 * result + r;
    result = 31 * result + s;
    return result;
  }

  @Override
  public String toString() {
    return "Hex{" +
      "q=" + q +
      ", r=" + r +
      ", s=" + s +
      '}';
  }
}
