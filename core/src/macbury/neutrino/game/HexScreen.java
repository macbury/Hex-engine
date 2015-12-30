package macbury.neutrino.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import macbury.neutrino.core.map.Hex;
import macbury.neutrino.core.map.HexGrid;
import macbury.neutrino.core.map.IHexGridRenderer;
import macbury.neutrino.core.map.SimpleHexRenderer;
import macbury.neutrino.core.screens.ScreenBase;


/**
 * Created on 30.12.15.
 */
public class HexScreen extends ScreenBase {
  private OrthographicCamera camera;
  private SpriteBatch spriteBatch;
  private Texture hexTexture;
  private HexGrid hexGrid;
  private IHexGridRenderer hexGridRenderer;
  private RenderContext renderContext;

  @Override
  public void preload() {
    assets.load("exhex.png", Texture.class);
  }

  @Override
  public void create() {
    Hex hexA = new Hex(1,1,1);
    Hex hexB = new Hex(2,2,2);
    this.camera = new OrthographicCamera();
    this.spriteBatch = new SpriteBatch();
    hexTexture       = assets.get("exhex.png", Texture.class);
    renderContext    = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    hexGrid          = new HexGrid(10, 10);
    hexGridRenderer  = new SimpleHexRenderer(hexGrid, renderContext);




  }

  @Override
  public void render(float delta) {
    camera.update();
    Gdx.gl.glClearColor(0,0,0,0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    hexGridRenderer.setView(camera);
    hexGridRenderer.render();
  }

  @Override
  public void resize(int width, int height) {
    camera.setToOrtho(false, width, height);
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
