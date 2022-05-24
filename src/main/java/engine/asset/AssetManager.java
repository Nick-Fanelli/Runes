package engine.asset;

import engine.render.Shader;
import engine.state.StateChangeEvent;
import engine.state.StateManager;

import java.util.HashMap;

public final class AssetManager {

    static {
        StateManager.AddStateChangeEventListener(AssetManager::OnStateChange);
    }

    private final HashMap<String, Asset<Shader>> shaderHashMap = new HashMap<>();

    public Asset<Shader> LoadShader(String shaderName) {
        if(shaderHashMap.containsKey(shaderName))
            return shaderHashMap.get(shaderName);

        Asset<Shader> asset = new Asset<>(new Shader(shaderName));
        shaderHashMap.put(shaderName, asset);

        return asset;
    }

    private static void OnStateChange(StateChangeEvent e) {

    }

}
