package macbury.neutrino.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.LinkedList;


/**
 * Renders simple 2d Hex map
 */
public class SimpleHexRenderer extends HexGridRenderer {
  private static final Color HEX_LINE_COLOR = new Color(0.49f,1,1,0.3f);
  private static final Color ACTIVE_HEX_LINE_COLOR = new Color(0.49f,1,1,1f);
  private static final String TAG = "SimpleHexRenderer";
  private ShapeRenderer shapeRenderer;
  private RenderContext renderContext;
  private Camera camera;
  private HexPolygonAssembler assembler;
  private Hex tempHex = new Hex();

  public SimpleHexRenderer(HexGrid grid, RenderContext renderContext) {
    super(grid, new Layout(Orientation.Flat, new Vector2( Hex.SIZE,  Hex.SIZE), new Vector2(0, 0)));
    this.shapeRenderer = new ShapeRenderer();
    this.renderContext = renderContext;
    this.assembler     = new HexPolygonAssembler(layout);
  }

  @Override
  public void setView(Camera camera) {
    this.camera = camera;
  }

  final Plane xzPlane        = new Plane(new Vector3(0, 1, 0), 0);
  final Vector3 intersectionA = new Vector3();
  final Vector3 intersectionB = new Vector3();
  final Hex mousePosHex       = new Hex();
  final FractionalHex mouseFractionalPos = new FractionalHex();
  // http://www.badlogicgames.com/wordpress/?p=2032
  @Override
  public void render() {
    Intersector.intersectRayPlane(camera.getPickRay(Gdx.input.getX(), Gdx.input.getY()), xzPlane, intersectionA);

    mouseFractionalPos.setWorld(intersectionA, layout);
    mouseFractionalPos.roundTo(mousePosHex);

    renderContext.begin(); {
      renderContext.setBlending(true, GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
      shapeRenderer.setProjectionMatrix(camera.combined);
      shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {


        for (int q = -grid.getRadius(); q <= grid.getRadius(); q++) {
          for (int r = -grid.getRadius(); r <= grid.getRadius(); r++) {
            tempHex.set(q,r);

            if (tempHex.equals(mousePosHex)) {
              shapeRenderer.setColor(ACTIVE_HEX_LINE_COLOR);
            } else {
              shapeRenderer.setColor(HEX_LINE_COLOR);
            }

            if (grid.get(q,r) != null) {
              renderHex(tempHex);
            }
          }
        }
      } shapeRenderer.end();

      shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.box(-0.5f, 0, 0.5f, 1, 1, 1);

        shapeRenderer.line(intersectionA, Vector3.Zero);
        //shapeRenderer.line(Vector3.Zero, intersectionB);
      } shapeRenderer.end();
    } renderContext.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
      this.layout        = new Layout(Orientation.Pointy, new Vector2( Hex.SIZE,  Hex.SIZE), Vector2.Zero);
      this.assembler     = new HexPolygonAssembler(layout);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
      this.layout        = new Layout(Orientation.Flat, new Vector2( Hex.SIZE,  Hex.SIZE), Vector2.Zero);
      this.assembler     = new HexPolygonAssembler(layout);
    }
  }

  private void renderAssemblerResult(int indexStart, int indexEnd) {
    shapeRenderer.line(
      assembler.result.get(indexStart),
      assembler.result.get(indexEnd)
    );

  }

  private void renderHex(Hex hex) {
    assembler.build(hex);

    renderAssemblerResult(0,1);
    renderAssemblerResult(1,2);
    renderAssemblerResult(2,3);
    renderAssemblerResult(3,4);
    renderAssemblerResult(4,5);
    renderAssemblerResult(5,0);

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
    private Array<Vector3> result;

    public HexPolygonAssembler(Layout layout) {
      this.result        = new Array<Vector3>(CORNER_COUNT);
      for (int i = 0; i < CORNER_COUNT; i++) {
        result.add(new Vector3());
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
          0,
          center.y + layout.size.y * MathUtils.sinDeg(angleDeg)
        );
      }
    }
  }
}
