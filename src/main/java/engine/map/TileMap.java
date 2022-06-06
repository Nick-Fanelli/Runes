package engine.map;

import com.sun.source.doctree.StartElementTree;
import engine.asset.AssetManager;
import engine.map.ldtk.LDtkEntity;
import engine.map.ldtk.LDtkLayer;
import engine.map.ldtk.LDtkLevel;
import engine.map.ldtk.LDtkTile;
import engine.physics2d.Physics2D;
import engine.physics2d.Rigidbody2D;
import engine.physics2d.colliders.BoxCollider2D;
import engine.render.Texture;
import engine.render.sprite.SpriteSheet;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteAnimatorComponent;
import engine.state.component.SpriteRendererComponent;
import game.states.GameState;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;

public class TileMap {

    private final State state;
    private final Physics2D physics2D;
    private final LDtkLevel level;

    private final int solidTileID;

    private final ArrayList<GameObject> tiles = new ArrayList<>();
    private final HashMap<String, LDtkEntity> entities = new HashMap<>();

    public TileMap(State state, Physics2D physics2D, LDtkLevel level, int solidTileID) {
        this.state = state;
        this.physics2D = physics2D;
        this.level = level;
        this.solidTileID = solidTileID;
    }

    public void GenerateGameObjects() {
        for(LDtkLayer layer : level.ldtkLayers) {
            if(layer.GetLayerType() == LDtkLayer.LayerType.ENTITIES) {
                ParseEntityLayer(layer);
                continue;
            }

            int gridSize = layer.__gridSize;

            float yOffset = -1.0f + layer.glTileSize / 2.0f;

            String textureFilepath = layer.__tilesetRelPath.split("/textures/")[layer.__tilesetRelPath.split("/textures/").length - 1];

            Texture texture = AssetManager.LoadTexture(state, textureFilepath);
            SpriteSheet spriteSheet = new SpriteSheet(texture, gridSize, gridSize);

            for(LDtkTile tile : layer.ldtkTiles) {
                float x = (((float) tile.px[0] / gridSize) *  layer.glTileSize);
                float y = -(((float) tile.px[1] / gridSize) * layer.glTileSize) - yOffset;

                GameObject gameObject = new GameObject(state);
                gameObject.transform.position = new Vector2f(x, y);
                gameObject.transform.scale = new Vector2f(layer.glTileSize);

                gameObject.AddComponent(new SpriteRendererComponent(new Vector4f(1.0f), texture,
                        spriteSheet.GetSprite(tile.t, SpriteSheet.SpriteFlip.GetFlip(tile.f))));

                if(layer.GetLayerType() == LDtkLayer.LayerType.INT_GRID) {

                    int colPos = tile.px[0] / gridSize;
                    int rowPos = tile.px[1] / gridSize;

                    int index = rowPos * layer.__cWid + colPos;

                    if(layer.intGridCsv[index] == solidTileID) {
                        Rigidbody2D rigidbody2D = new Rigidbody2D();
                        rigidbody2D.bodyType = BodyType.STATIC;

                        gameObject.AddComponent(rigidbody2D);
                        gameObject.AddComponent(new BoxCollider2D());

                        physics2D.AddGameObject(gameObject);
                    }
                }

                tiles.add(gameObject);
            }
        }
    }

    private void ParseEntityLayer(LDtkLayer layer) {
        for(LDtkEntity entity : layer.ldtkEntities) {
            entities.put(entity.__identifier, entity);
        }
    }

    public LDtkEntity GetEntity(String identifier) {
        return entities.get(identifier);
    }

    public Vector2f ConvertPixelsToPosition(Vector2f pixelPosition, float tileSize) {
        float yOffset = -1.0f + tileSize / 2.0f;

        float x = pixelPosition.x / tileSize;
        float y = pixelPosition.y / tileSize - yOffset;

        return new Vector2f(x, y);
    }

    public void OnUpdate(float deltaTime) {
        for(GameObject gameObject : tiles) {
            gameObject.OnUpdate(deltaTime);
        }
    }

    public void OnRender() {
        for(GameObject gameObject : tiles) {
            gameObject.OnRender();
        }
    }

    public void OnDestroy() {

    }

}
