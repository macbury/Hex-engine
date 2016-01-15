package macbury.neutrino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import macbury.neutrino.core.map.Hex;
import macbury.neutrino.core.map.HexGrid;
import macbury.neutrino.core.map.HexGridRenderer;
import macbury.neutrino.core.map.SimpleHexRenderer;
import macbury.neutrino.core.screens.ScreenBase;


/**
 * Created on 30.12.15.
 */
public class HexScreen extends ScreenBase {
  private PerspectiveCamera camera;
  private SpriteBatch spriteBatch;
  private Texture hexTexture;
  private HexGrid hexGrid;
  private HexGridRenderer hexGridRenderer;
  private RenderContext renderContext;
  private CameraInputController cameraController;

  @Override
  public void preload() {
    assets.load("exhex.png", Texture.class);
  }

  @Override
  public void create() {
    this.camera      = new PerspectiveCamera(70f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.near      = 1f;
    camera.far       = 1000f;
    this.spriteBatch = new SpriteBatch();
    hexTexture       = assets.get("exhex.png", Texture.class);
    renderContext    = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    hexGrid          = new HexGrid(100);
    hexGridRenderer  = new SimpleHexRenderer(hexGrid, renderContext);
    cameraController = new CameraInputController(camera);

    camera.position.set(0,10,0);
    camera.lookAt(Vector3.Zero);
    Gdx.input.setInputProcessor(cameraController);
  }

  @Override
  public void render(float delta) {
    cameraController.update();
    camera.update();
    Gdx.gl.glClearColor(0,0,0,0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    hexGridRenderer.setView(camera);
    hexGridRenderer.render();
  }

  @Override
  public void resize(int width, int height) {
    camera.update(true);
  }

  @Override
  public void unload() {
    assets.unload("exhex.png");
  }

  @Override
  public void dispose() {
    hexGrid.dispose();
    spriteBatch.dispose();
    hexGridRenderer.dispose();
    renderContext = null;
  }
}
