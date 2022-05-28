package engine.asset;

import engine.render.Texture;
import engine.state.State;
import engine.state.StateChangeEvent;
import engine.state.StateManager;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public final class AssetManager {

    static {
        StateManager.AddStateChangeEventListener(AssetManager::OnStateChange);
    }

    private static final HashMap<String, Asset<Texture>> textures = new HashMap<>();

    private static String GetFilepath(String prefix, String relativeFilepath) {
        File file = new File(prefix + File.separator + relativeFilepath);

        return file.getAbsolutePath();
    }

    public static Texture LoadTexture(State state, String texturePath) {
        if(textures.containsKey(texturePath)) {
            return textures.get(texturePath).rawAsset();
        }

        Texture texture = new Texture(GetFilepath("assets/textures", texturePath));
        texture.Create();

        Asset<Texture> asset = new Asset<>(texture, state);
        textures.put(texturePath, asset);

        return asset.rawAsset();
    }

    private static void OnStateChange(StateChangeEvent e) {

    }

}
