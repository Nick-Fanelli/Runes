package engine.map.ldtk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.joml.Vector2f;

import java.io.IOException;
import java.util.ArrayList;

public class LDtkLayer {

    public enum LayerType {
        INT_GRID,
        AUTO_LAYER,
        TILES,
        ENTITIES,
        UNKNOWN;

        public static LayerType GetLayerTypeFromIdentifier(String identifier) {
            return switch (identifier) {
                case "IntGrid_layer" -> INT_GRID;
                case "AutoLayer" -> AUTO_LAYER;
                case "Tiles" -> TILES;
                case "Entities" -> ENTITIES;
                default -> UNKNOWN;
            };
        }
    }

    public String __type;
    public String __tilesetRelPath;
    public ArrayNode autoLayerTiles;
    public ArrayNode entityInstances;
    public int __cWid;
    public int __cHei;
    public int __gridSize;

    public float glTileSize;
    public Vector2f glLayerStartPosition;

    private LayerType layerType;

    public ArrayList<LDtkTile> ldtkTiles = new ArrayList<>();
    public ArrayList<LDtkEntity> ldtkEntities = new ArrayList<>();

    public void ParseTiles() throws IOException {
        this.layerType = LayerType.GetLayerTypeFromIdentifier(__type);
        this.glTileSize = 2.0f / (float) this.__cHei;

        float yOffset = -1.0f + glTileSize / 2.0f;

        glLayerStartPosition = new Vector2f(0, yOffset);

        for(JsonNode node : autoLayerTiles) {
            LDtkTile tile = LDtkParser.objectMapper.treeToValue(node, LDtkTile.class);
            ldtkTiles.add(tile);
        }

        for(JsonNode node : entityInstances) {
            LDtkEntity entity = LDtkParser.objectMapper.treeToValue(node, LDtkEntity.class);
            ldtkEntities.add(entity);
        }
    }

    public LayerType GetLayerType() { return this.layerType; }

}
