package game.states;

import engine.core.display.WindowResizeEvent;
import engine.map.TileMap;
import engine.map.ldtk.*;
import engine.state.State;
import engine.utils.FileUtils;
import game.objects.Player;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameState extends State {

    private Vector2f layerOffsetPosition;

    private Player player;
    private TileMap tileMap;

    private float playerStartingX;

    @Override
    public void OnCreate() {
        super.OnCreate();

        LDtkWorld worldFile = LDtkParser.Parse(FileUtils.ReadAssetFile("assets/maps/world.ldtk"));
        LDtkLevel level = worldFile.ldtkLevels.get("Test_Level");
        LDtkLayer firstLayer = level.ldtkLayers.get(0);

        // Generate Tile Map From Level
        tileMap = new TileMap(this, level);
        tileMap.GenerateGameObjects();

        LDtkEntity tilemapPlayerEntity = tileMap.GetEntity("Player");

        layerOffsetPosition = new Vector2f(firstLayer.layerStartPosition).negate();

        player = new Player(this);

        if(tilemapPlayerEntity != null) {
            player.transform.position = tilemapPlayerEntity.position;
            player.transform.position.y += 0.025f; // Offset for player's bigger size
        }

        this.playerStartingX = player.transform.position.x;

    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.camera.SetXPosition(layerOffsetPosition.x + this.player.transform.position.x - this.playerStartingX);

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
    }
}
