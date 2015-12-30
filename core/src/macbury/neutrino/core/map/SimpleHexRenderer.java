package macbury.neutrino.core.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;


/**
 * Renders simple 2d Hex map
 */
public class SimpleHexRenderer implements IHexGridRenderer {
  private static final Color HEX_LINE_COLOR = new Color(1,1,1,0.5f);
  private ShapeRenderer shapeRenderer;
  private RenderContext renderContext;
  private HexGrid grid;
  private Camera camera;

  private Vector3 orig = new Vector3();
  private Vector3 vecA = new Vector3();
  private Vector3 vecB = new Vector3();

  public SimpleHexRenderer(HexGrid grid, RenderContext renderContext) {
    this.grid = grid;
    this.shapeRenderer = new ShapeRenderer();
    this.renderContext = renderContext;
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
        orig.set(100, 100, 0);
        renderHex();
        /*shapeRenderer.setColor(HEX_LINE_COLOR);
        orig.set(100, 139, 0);
        renderHex();
        shapeRenderer.setColor(HEX_LINE_COLOR);
        orig.set(135, 119, 0);
        renderHex();
        */
      } shapeRenderer.end();
    } renderContext.end();

  }

  private void renderHex() {
    cornerPart( 5 );
    cornerPart( 4 );
    cornerPart( 3 );
    cornerPart( 2 );
    cornerPart( 1 );
    cornerPart( 0 );
  }

  public void cornerPart(int cornerId) {
    corner(orig, Hex.SIZE, cornerId, vecA);
    corner(orig, Hex.SIZE, cornerId+1, vecB);
    shapeRenderer.line(vecA, vecB);
  }

  public Vector3 corner(Vector3 center, int size, int cornerId, Vector3 out) {
    float angle = 60 * cornerId;

    out.set(center).add(
      Hex.SIZE * MathUtils.cosDeg(angle),
      Hex.SIZE * MathUtils.sinDeg(angle),
      0
    );

    return out;
  }

  @Override
  public void dispose() {
    camera = null;
    grid = null;
    renderContext = null;
    shapeRenderer.dispose();
    shapeRenderer = null;
  }
}
