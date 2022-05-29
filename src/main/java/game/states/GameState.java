package game.states;

import engine.core.Input;
import engine.core.display.WindowResizeEvent;
import engine.map.TileMap;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteRendererComponent;
import engine.utils.FileUtils;
import engine.map.ldtk.LDtkParser;
import engine.map.ldtk.LDtkWorldFile;
import game.objects.Player;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;

public class GameState extends State {

    private float aspectRatio = 1600.0f / 900.0f;

    private Player player;
    private TileMap tileMap;

    @Override
    public void OnCreate() {
        super.OnCreate();

        super.camera.SetPosition(new Vector2f(aspectRatio, 0.0f));

        LDtkWorldFile worldFile = LDtkParser.Parse(FileUtils.ReadAssetFile("assets/maps/testmap.ldtk"));

        tileMap = new TileMap(this, worldFile.ldtkLevels.get("Test_Level"));
        tileMap.GenerateGameObjects();

        player = new Player(this);

    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);
        super.camera.SetXPosition(Math.max(aspectRatio, player.transform.position.x));
    }

    @Override
    public void OnWindowResize(WindowResizeEvent event) {
        super.OnWindowResize(event);

        this.aspectRatio = event.aspectRation;
    }
}
