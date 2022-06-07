package game.states;

import engine.core.display.WindowResizeEvent;
import engine.map.TileMap;
import engine.map.ldtk.*;
import engine.physics2d.Physics2D;
import engine.state.State;
import engine.utils.FileUtils;
import game.objects.Player;
import game.ui.GameUILayer;
import org.joml.Vector2f;

public class GameState extends State {

    private final Physics2D physics2D = new Physics2D();

    private GameUILayer uiLayer;

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
        tileMap = new TileMap(this, physics2D, level, 1);
        tileMap.GenerateGameObjects();

        LDtkEntity tilemapPlayerEntity = tileMap.GetEntity("Player");

        layerOffsetPosition = new Vector2f(firstLayer.layerStartPosition).negate();

        player = new Player(this);

        if(tilemapPlayerEntity != null) {
            player.transform.position = tilemapPlayerEntity.position;
            player.transform.position.y += 0.025f; // Offset for player's bigger size
        }

        this.playerStartingX = player.transform.position.x;

        physics2D.AddGameObject(player);

        uiLayer = new GameUILayer(this);
        uiLayer.OnCreate();
    }

    @Override
    public void OnUpdate(float deltaTime) {
        // Update Physics
        physics2D.OnUpdate(deltaTime);

        super.camera.SetXPosition(layerOffsetPosition.x + this.player.transform.position.x - this.playerStartingX);

        // Update Game Objects
        this.player.OnUpdate(deltaTime);
        this.tileMap.OnUpdate(deltaTime);

        this.uiLayer.OnUpdate();
    }

    @Override
    public void OnRender() {
        this.renderer.Begin();

        this.tileMap.OnRender();

//        renderer.GetLightRenderer().DrawLight(new Vector2f(1.861f, 0.89f), new Vector4f(1.0f, 0.35f, 0.35f, 1.0f));
        this.player.OnRender();

        this.uiLayer.OnRender();

        this.renderer.End();
    }

    @Override
    public void OnWindowResize(WindowResizeEvent event) {
        super.OnWindowResize(event);

        this.player.OnDestroy();
        this.tileMap.OnDestroy();
        this.uiLayer.OnDestroy();
    }
}
