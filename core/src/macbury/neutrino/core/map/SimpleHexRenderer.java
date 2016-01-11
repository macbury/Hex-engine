package macbury.neutrino.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.LinkedList;


/**
 * Renders simple 2d Hex map
 */
public class SimpleHexRenderer extends HexGridRenderer {
  private static final Color HEX_LINE_COLOR = new Color(1,1,1,0.3f);
  private ShapeRenderer shapeRenderer;
  private RenderContext renderContext;
  private Camera camera;
  private HexPolygonAssembler assembler;
  private Hex tempHex = new Hex();

  public SimpleHexRenderer(HexGrid grid, RenderContext renderContext) {
    super(grid, new Layout(Orientation.Flat, new Vector2( Hex.SIZE,  Hex.SIZE), new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));
    this.shapeRenderer = new ShapeRenderer();
    this.renderContext = renderContext;
    this.assembler     = new HexPolygonAssembler(layout);
  }

  @Override
  public void setView(Camera camera) {
    this.camera = camera;
  }

  @Override
  public void render() {
    renderContext.begin(); {
      renderContext.setBlending(true, GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
      shapeRenderer.setProjectionMatrix(camera.combined);
      shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
        shapeRenderer.setColor(HEX_LINE_COLOR);

        for (int q = -grid.getRadius(); q <= grid.getRadius(); q++) {
          for (int r = -grid.getRadius(); r <= grid.getRadius(); r++) {
            if (grid.get(q,r) != null) {
              tempHex.set(q,r);
              renderHex(tempHex);
            }
          }
        }
      } shapeRenderer.end();
    } renderContext.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
      this.layout        = new Layout(Orientation.Pointy, new Vector2( Hex.SIZE,  Hex.SIZE), new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
      this.assembler     = new HexPolygonAssembler(layout);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
      this.layout        = new Layout(Orientation.Flat, new Vector2( Hex.SIZE,  Hex.SIZE), new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
      this.assembler     = new HexPolygonAssembler(layout);
    }
  }

  private void renderHex(Hex hex) {
    assembler.build(hex);
    shapeRenderer.line(
      assembler.result.get(0),
      assembler.result.get(1)
    );

    shapeRenderer.line(
      assembler.result.get(1),
      assembler.result.get(2)
    );

    shapeRenderer.line(
      assembler.result.get(2),
      assembler.result.get(3)
    );

    shapeRenderer.line(
      assembler.result.get(3),
      assembler.result.get(4)
    );

    shapeRenderer.line(
      assembler.result.get(4),
      assembler.result.get(5)
    );

    shapeRenderer.line(
      assembler.result.get(5),
      assembler.result.get(0)
    );
  }


  @Override
  public void dispose() {
    super.dispose();
    camera = null;
    renderContext = null;
    shapeRenderer.dispose();
    shapeRenderer = null;
  }

  private class HexPolygonAssembler implements Disposable {
    private static final int CORNER_COUNT = 6;
    private Layout layout;
    private int corner;
    private float angleDeg;
    private Vector2 center;
    private Array<Vector2> result;

    public HexPolygonAssembler(Layout layout) {
      this.result        = new Array<Vector2>(CORNER_COUNT);
      for (int i = 0; i < CORNER_COUNT; i++) {
        result.add(new Vector2());
      }
      this.layout        = layout;
      this.center        = new Vector2();
    }

    @Override
    public void dispose() {
      result      = null;
      layout      = null;
    }

    public void build(Hex hex) {
      hex.toVector2(layout, center);

      for (corner = 0; corner < result.size; corner++) {
        angleDeg         = (corner  * layout.orientation.startAngleDeg) + layout.orientation.angleOffsetDeg;
        result.get(corner).set(
          center.x + layout.size.x * MathUtils.cosDeg(angleDeg),
          center.y + layout.size.y * MathUtils.sinDeg(angleDeg)
        );
      }
    }
  }
}
