package game.states;

import engine.core.display.WindowResizeEvent;
import engine.map.TileMap;
import engine.state.State;
import engine.utils.FileUtils;
import engine.map.ldtk.LDtkParser;
import engine.map.ldtk.LDtkWorld;
import game.objects.Player;
import org.joml.Vector2f;

public class GameState extends State {

    private float aspectRatio = 1600.0f / 900.0f;

    private Player player;
    private TileMap tileMap;

    @Override
    public void OnCreate() {
        super.OnCreate();

        super.camera.SetPosition(new Vector2f(aspectRatio, 0.0f));

        LDtkWorld worldFile = LDtkParser.Parse(FileUtils.ReadAssetFile("assets/maps/testmap.ldtk"));

        tileMap = new TileMap(this, worldFile.ldtkLevels.get("Test_Level"));
        tileMap.GenerateGameObjects();

        player = new Player(this);

    }

    @Override
    public void OnUpdate(float deltaTime) {
        // Update Camera
        super.camera.SetXPosition(Math.max(aspectRatio, player.transform.position.x));

        // Update Player
        this.player.OnUpdate(deltaTime);
    }

    @Override
    public void OnRender() {
        this.renderer.Begin();

        this.tileMap.OnRender();
        this.player.OnRender();

        this.renderer.End();
    }

    @Override
    public void OnWindowResize(WindowResizeEvent event) {
        super.OnWindowResize(event);

        this.player.OnDestroy();
        this.tileMap.OnDestroy();

        this.aspectRatio = event.aspectRation;
    }
}
