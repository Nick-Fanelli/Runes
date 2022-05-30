package engine.map;

import com.sun.source.doctree.StartElementTree;
import engine.asset.AssetManager;
import engine.map.ldtk.LDtkLayer;
import engine.map.ldtk.LDtkLevel;
import engine.map.ldtk.LDtkTile;
import engine.render.Texture;
import engine.render.sprite.SpriteSheet;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteAnimatorComponent;
import engine.state.component.SpriteRendererComponent;
import game.states.GameState;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class TileMap {

    private final State state;
    private final LDtkLevel level;

    private final ArrayList<GameObject> tiles = new ArrayList<>();

    public TileMap(State state, LDtkLevel level) {
        this.state = state;
        this.level = level;
    }

    public void GenerateGameObjects() {
        for(LDtkLayer layer : level.ldtkLayers) {
            int vTileCount = layer.__cHei;
            int gridSize = layer.__gridSize;

            float tileSize = 2.0f / (float) vTileCount;
            float yOffset = -1.0f + tileSize / 2.0f;

            String textureFilepath = layer.__tilesetRelPath.split("/textures/")[layer.__tilesetRelPath.split("/textures/").length - 1];

            Texture texture = AssetManager.LoadTexture(state, textureFilepath);
            SpriteSheet spriteSheet = new SpriteSheet(texture, gridSize, gridSize);

            for(LDtkTile tile : layer.ldtkTiles) {
                float x = (((float) tile.px[0] / gridSize) *  tileSize);
                float y = -(((float) tile.px[1] / gridSize) * tileSize) - yOffset;

                GameObject gameObject = new GameObject(state);
                gameObject.transform.position = new Vector2f(x, y);
                gameObject.transform.scale = new Vector2f(tileSize);
                gameObject.AddComponent(new SpriteRendererComponent(new Vector4f(1.0f), texture, spriteSheet.GetSprite(tile.t)));

                tiles.add(gameObject);
            }
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
