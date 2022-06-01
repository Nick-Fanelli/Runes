package game.states;

import engine.core.display.WindowResizeEvent;
import engine.map.EntityQuery;
import engine.map.TileMap;
import engine.map.ldtk.LDtkLevel;
import engine.state.State;
import engine.state.Transform;
import engine.utils.FileUtils;
import engine.map.ldtk.LDtkParser;
import engine.map.ldtk.LDtkWorld;
import game.objects.Player;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameState extends State {

    private float aspectRatio = 1600.0f / 900.0f;

    private Player player;
    private TileMap tileMap;

    @Override
    public void OnCreate() {
        super.OnCreate();

        super.camera.SetPosition(new Vector2f(aspectRatio, 0.0f));

        LDtkWorld worldFile = LDtkParser.Parse(FileUtils.ReadAssetFile("assets/maps/world.ldtk"));
        LDtkLevel level = worldFile.ldtkLevels.get("Test_Level");

        // Generate Tile Map From Level
        tileMap = new TileMap(this, level);
        tileMap.GenerateGameObjects();

        Vector2f positionOffset = EntityQuery.GetEntityOffset("Player", level.GetEntitiesLayers());

        player = new Player(this);
        player.transform.position = positionOffset;
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

        renderer.GetLightRenderer().DrawLight(new Vector2f(1.861f, 0.89f), new Vector4f(1.0f, 0.35f, 0.35f, 1.0f));
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
