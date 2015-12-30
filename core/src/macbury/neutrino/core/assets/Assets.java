package macbury.neutrino.core.assets;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Logger;

/**
 * This class wraps {@link AssetManager} and extends it with proper features required by game and automaticaly map asssets to path
 */
public class Assets extends AssetManager {

  public Assets() {
    this(new EngineFileHandleResolver());
  }

  public Assets(FileHandleResolver resolver) {
    super(resolver);
    setLogger(new Logger("AssetManager", Application.LOG_INFO));
    //setLoader(TrixelMap.class, new TrixelMapLoader(resolver));
  }
}