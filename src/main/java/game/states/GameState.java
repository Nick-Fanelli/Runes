package game.states;

import engine.audio.AudioClip;
import engine.core.display.WindowResizeEvent;
import engine.map.TileMap;
import engine.map.ldtk.*;
import engine.physics2d.Physics2D;
import engine.state.State;
import engine.utils.IOUtils;
import engine.utils.Range;
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
    private float tilemapEndingPosition;

    private AudioClip audioClip;

    private void GenerateTilemap() {
        // Load the LDtk File
        LDtkWorld worldFile = LDtkParser.Parse(IOUtils.ReadAssetFileAsString("assets/maps/world.ldtk"));
        LDtkLevel level = worldFile.ldtkLevels.get("Test_Level");
        LDtkLayer firstLayer = level.ldtkLayers.get(0);

        layerOffsetPosition = new Vector2f(firstLayer.layerStartPosition).negate();
        tilemapEndingPosition = (firstLayer.__cWid * firstLayer.glTileSize) - layerOffsetPosition.x;

        // Generate Tile Map From Level
        tileMap = new TileMap(this, physics2D, level, 1);
        tileMap.GenerateGameObjects();
    }

    private void CreatePlayer() {
        player = new Player(this);

        // Get the player starting instance
        LDtkEntity tilemapPlayerEntity = tileMap.GetEntity("Player");

        if(tilemapPlayerEntity != null) {
            player.transform.position = tilemapPlayerEntity.position;
            player.transform.position.y += 0.025f; // Offset for player's bigger size
        }

        this.playerStartingX = player.transform.position.x;
        physics2D.AddGameObject(player);
    }

    @Override
    public void OnCreate() {
        super.OnCreate();

        GenerateTilemap();
        CreatePlayer();

        // Create UI Layer
        uiLayer = new GameUILayer(this);
        uiLayer.OnCreate();

        audioClip = new AudioClip("assets/audio/BabyElephantWalk.wav");
        audioClip.Create();
        audioClip.PlayContinuously();
    }

    @Override
    public void OnUpdate(float deltaTime) {
        // Update Physics
        physics2D.OnUpdate(deltaTime);

        super.camera.SetXPosition(
                Range.Clip(layerOffsetPosition.x, tilemapEndingPosition,
                        layerOffsetPosition.x + this.player.transform.position.x - this.playerStartingX));

        // Update Game Objects
        this.player.OnUpdate(deltaTime);
        this.tileMap.OnUpdate(deltaTime);

        this.uiLayer.OnUpdate();
    }

    @Override
    public void OnRender() {
        this.renderer.Begin();

        this.tileMap.OnRender();
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
