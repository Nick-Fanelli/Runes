package game.states;

import com.fasterxml.jackson.databind.JsonNode;
import engine.asset.AssetManager;
import engine.map.TileMap;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteRendererComponent;
import engine.utils.FileUtils;
import engine.utils.JSONLoader;
import game.objects.Player;
import org.joml.Vector4f;
import org.w3c.dom.Text;

import java.io.File;

public class GameState extends State {

    private static final float speed = 10.0f;

    private float backgroundSize;

    private Player player;

    @Override
    public void OnCreate() {
        super.OnCreate();

        File file = new File("assets/maps/testmap/simplified/AutoLayers_advanced_demo/_composite.png");

        Texture texture = new Texture(file.getAbsolutePath());
        texture.Create();

        GameObject background = CreateGameObject();
        background.AddComponent(new SpriteRendererComponent(new Vector4f(1.0f), texture));

        backgroundSize = (2.0f / texture.GetHeight()) * texture.GetWidth();

        background.transform.position.y = 0.0f;
        background.transform.scale.x = backgroundSize;
        background.transform.scale.y = 2.0f;

        player = new Player(this);

    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);

        super.camera.SetXPosition(Math.max(0.0f, player.transform.position.x));
    }

}
