package engine.asset;

import engine.render.Shader;
import engine.render.Texture;
import engine.state.State;
import engine.state.StateChangeEvent;
import engine.state.StateManager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AssetManager {

    static {
        StateManager.AddStateChangeEventListener(AssetManager::OnStateChange);
    }

    private static final HashMap<String, Asset<Texture>> textures = new HashMap<>();

    private static String GetFilepath(String prefix, String relativeFilepath) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(prefix + "/" + relativeFilepath);

        if(url == null) {
            throw new RuntimeException("Could not find URL!");
        }

        String path = url.getFile();

        if(path.startsWith("/"))
            path = path.substring(1);

        return path;
    }

    public static Texture LoadTexture(State state, String texturePath) {
        if(textures.containsKey(texturePath)) {
            return textures.get(texturePath).rawAsset();
        }

        Texture texture = new Texture(GetFilepath("textures", texturePath));
        texture.Create();

        Asset<Texture> asset = new Asset<>(texture, state);
        textures.put(texturePath, asset);

        return asset.rawAsset();
    }

    private static void OnStateChange(StateChangeEvent e) {

    }

}
