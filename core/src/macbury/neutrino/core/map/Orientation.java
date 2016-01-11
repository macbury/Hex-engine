package macbury.neutrino.core.map;

/**
 * This class contains information about {@link Hex} orientation
 */
public class Orientation {
  public final float f0, f1, f2, f3;
  public final float b0, b1, b2, b3;
  public final float startAngleDeg;
  public final float angleOffsetDeg;

  public final static Orientation Pointy = new Orientation(
    (float)Math.sqrt(3.0f), (float)Math.sqrt(3.0f) / 2.0f, 0.0f, 3.0f / 2.0f,
    (float)Math.sqrt(3.0f) / 3.0f, -1.0f / 3.0f, 0.0f, 2.0f / 3.0f,
    60f, 30f);

  public final static Orientation Flat   = new Orientation(
    3.0f / 2.0f, 0.0f, (float)Math.sqrt(3.0f) / 2.0f, (float)Math.sqrt(3.0f),
    2.0f / 3.0f, 0.0f, -1.0f / 3.0f, (float)Math.sqrt(3.0f) / 3.0f,
    60f, 0);

  Orientation(float f0_, float f1_, float f2_, float f3_, float b0_, float b1_, float b2_, float b3_, float start_angle_, float angleOffsetDeg_) {
    this.f0 = f0_;
    this.f1 = f1_;
    this.f2 = f2_;
    this.f3 = f3_;

    this.b0 = b0_;
    this.b1 = b1_;
    this.b2 = b2_;
    this.b3 = b3_;

    this.startAngleDeg  = start_angle_;
    this.angleOffsetDeg = angleOffsetDeg_;
  }
}
